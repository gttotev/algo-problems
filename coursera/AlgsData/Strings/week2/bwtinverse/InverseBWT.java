import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InverseBWT {
    int charIndex(char c) {
        switch (c) {
            case '$': return 0;
            case 'A': return 1;
            case 'C': return 2;
            case 'G': return 3;
            case 'T': return 4;
        }
        return -1;
    }
    
    int[] lastToFirst(String transform) {
       int[] ltf = new int[transform.length()];
       int[] charCount = new int[5];
       int[] ncharCount = new int[5];
       int[] charStart = {0, 1, 0, 0, 0};
       for (int i = 0; i < ltf.length; i++) {
           int ci = charIndex(transform.charAt(i));
           charCount[ci] = charCount[ci] + 1;
       }
       charStart[2] = charCount[1] + 1;
       charStart[3] = charStart[2] + charCount[2];
       charStart[4] = charStart[3] + charCount[3];
       for (int i = 0; i < ltf.length; i++) {
           int ci = charIndex(transform.charAt(i));
           ltf[i] = charStart[ci] + ncharCount[ci];
           ncharCount[ci] = ncharCount[ci] + 1;
       }
       return ltf;
    }
    
    String inverseBWT(String transform) {
        StringBuilder text = new StringBuilder();
        int[] ltf = lastToFirst(transform);
        int colIndex = 0;
        for (int i = 0; i < ltf.length-1; i++) {
            text.append(transform.charAt(colIndex));
            colIndex = ltf[colIndex];
        }
        return text.reverse().append('$').toString();
    }
    
    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }
    
    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
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
