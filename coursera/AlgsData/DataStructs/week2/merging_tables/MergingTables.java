import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class MergingTables {
    private final InputReader reader;
    private final OutputWriter writer;
    
    Table[] tables;
    int[] tablePars;
    int maxRows;

    public MergingTables(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
        this.maxRows = -1;
    }
    
    int getParent(int table1) {
        int i = table1;
        int parentI = tablePars[i];
        if (parentI == i)
            return i;
        int rootI = getParent(parentI);
        tablePars[i] = rootI;
        return rootI;
    }

    void merge(int destination, int source) {
        Table dest = tables[getParent(destination)];
        Table src = tables[getParent(source)];
        if (dest == src)
            return;
        int totalRows = dest.numRows + src.numRows;
        if (src.rank < dest.rank) {
            tablePars[src.id] = dest.id;
            dest.numRows = totalRows;
        } else {
            tablePars[dest.id] = src.id;
            src.numRows = totalRows;
            if (src.rank == dest.rank)
                src.incRank();
        }
        if (totalRows > maxRows)
            maxRows = totalRows;
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();
        tablePars = new int[n];
        tables = new Table[n];
        for (int i = 0; i < n; i++) {
            int numRows = reader.nextInt();
            tables[i] = new Table(i, numRows);
            tablePars[i] = i;
            maxRows = Math.max(maxRows, numRows);
        }
        for (int i = 0; i < m; i++) {
            int destination = reader.nextInt() - 1;
            int source = reader.nextInt() - 1;
            merge(destination, source);
            writer.printf("%d\n", maxRows);
        }
    }
    
    static class Table {
        int id;
        int rank;
        int numRows;
        Table(int id, int numberOfRows) {
            this.id = id;
            numRows = numberOfRows;
            rank = 0;
        }
        public void incRank() {
            rank++;
        }
    }
    
    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new MergingTables(reader, writer).run();
        writer.writer.flush();
    }
    
    static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}
