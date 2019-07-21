import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BWMatching {
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
    
    private void preprocessBWT(String transform, int[] charStart, int[][] count) {
        int transformLen = transform.length();
        for (int i = 0; i < transformLen; i++) {
            int ci = charIndex(transform.charAt(i));
            for (int e = 0; e < 5; e++) {
                count[e][i + 1] = count[e][i] + (e == ci ? 1 : 0);
            }
        }
        charStart[1] = 1;
        charStart[2] = 1 + count[1][transformLen];
        charStart[3] = charStart[2] + count[2][transformLen];
        charStart[4] = charStart[3] + count[3][transformLen];
    }
    
    int countOccurrences(String pattern, String bwt, int[] charStart, int[][] count) {
        int top = 0;
        int bottom = bwt.length() - 1;
        for (int i = pattern.length() - 1; i >= 0; i--) {
            int ci = charIndex(pattern.charAt(i));
            if (count[ci][bottom + 1] - count[ci][top] > 0) {
                top = charStart[ci] + count[ci][top];
                bottom = charStart[ci] + count[ci][bottom + 1] - 1;
            } else {
                return 0;
            }
        }
        return bottom - top + 1;
    }
    
    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        int[] charStart = new int[5];
        int[][] count = new int[5][bwt.length() + 1];
        preprocessBWT(bwt, charStart, count);
        int patternCount = scanner.nextInt();
        String[] patterns = new String[patternCount];
        int[] result = new int[patternCount];
        for (int i = 0; i < patternCount; ++i) {
            patterns[i] = scanner.next();
            result[i] = countOccurrences(patterns[i], bwt, charStart, count);
        }
        print(result);
    }
    
    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }
    
    static public void main(String[] args) throws IOException {
        new BWMatching().run();
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
