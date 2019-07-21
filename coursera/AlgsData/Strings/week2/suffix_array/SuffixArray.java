import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixArray {
    public class Suffix implements Comparable {
        String suffix;
        int start;
        
        Suffix(String suffix, int start) {
            this.suffix = suffix;
            this.start = start;
        }
        
        @Override
        public int compareTo(Object o) {
            Suffix other = (Suffix) o;
            return suffix.compareTo(other.suffix);
        }
    }
    
    public Suffix[] computeSuffixArray(String text) {
        Suffix[] suffixArray = new Suffix[text.length()];
        for (int i = 0; i < suffixArray.length; i++) {
            suffixArray[i] = new Suffix(text.substring(i), i);
        }
        Arrays.sort(suffixArray);
        return suffixArray;
    }
    
    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        Suffix[] suffixArray = computeSuffixArray(text);
        print(suffixArray);
    }
    
    public void print(Suffix[] x) {
        for (Suffix a : x) {
            System.out.print(a.start + " ");
        }
        System.out.println();
    }
    
    static public void main(String[] args) throws IOException {
        new SuffixArray().run();
    }
    
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;
        
        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }
        
        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }
        
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
