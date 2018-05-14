import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class TemplateFS {
    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner("template.in");
        PrintWriter out = new PrintWriter(new java.io.File("template.out"));
        
        in.close();
        out.close();
    }
    
    private static class FastScanner {
        static final char DELIMITER = ' ';
        BufferedReader br;
        String line;
        StringTokenizer st;
        
        public FastScanner(String file) throws IOException {
            br = new BufferedReader(new java.io.FileReader(file));
        }
        
        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                line = br.readLine();
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
        
        String nextLine() throws IOException {
            if (st == null || !st.hasMoreTokens()) {
                return br.readLine();
            } else {
                StringBuilder sb = new StringBuilder();
                while (st.hasMoreTokens()) {
                    sb.append(st.nextToken());
                    sb.append(DELIMITER);
                }
                st = null;
                sb.deleteCharAt(sb.length()-1);
                return sb.toString();
            }
        }
        
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        
        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
        
        void close() throws IOException {
            br.close();
        }
    }
}
