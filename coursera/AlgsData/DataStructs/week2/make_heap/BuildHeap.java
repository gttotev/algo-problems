import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
    private int dataLength;
    private int[] data;
    private List<Swap> swaps;
    
    private FastScanner in;
    private PrintWriter out;
    
    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
    }
    
    private void readData() throws IOException {
        int n = in.nextInt();
        dataLength = n;
        data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = in.nextInt();
        }
    }
    
    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
            out.println(swap.index1 + " " + swap.index2);
        }
    }
    
    private void printData(String msg) {
        System.out.print(msg + ": ");
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }
    
    private void generateSwaps() {
        swaps = new ArrayList<Swap>();
        // TODO: fix start i start
        for (int i = /*dataLength/2 + 1*/dataLength-1; i >= 0; i--) {
            siftDown(i);
            //printData(Integer.toString(i));
        }
    }
    
    private void siftDown(int node) {
        int maxIndex = node;
        int childLeft = (node * 2) + 1;
        if (childLeft < dataLength && data[childLeft] < data[node])
            maxIndex = childLeft;
        int childRight = childLeft + 1;
        if (childRight < dataLength && data[childRight] < data[node])
            maxIndex = (data[childRight] < data[childLeft] ? childRight : childLeft);
        if (node != maxIndex) {
            swapNodes(node, maxIndex);
            siftDown(maxIndex);
        }
    }
    
    private void swapNodes(int i1, int i2) {
        int tmp = data[i1];
        data[i1] = data[i2];
        data[i2] = tmp;
        swaps.add(new Swap(i1, i2));
    }
    
    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwaps();
        writeResponse();
        out.close();
    }
    
    static class Swap {
        int index1;
        int index2;
        
        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }
    }
    
    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;
        
        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }
        
        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }
        
        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
