import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class KnuthMorrisPratt {
    public List<Integer> findPattern(String pattern, String text) {
        int patternLen = pattern.length();
        int twicePatternLen = patternLen * 2;
        String kmpText = pattern + "$" + text;
        int[] prefixBorders = new int[kmpText.length()];
        int border = 0;
        ArrayList<Integer> matches = new ArrayList<Integer>();
        for (int i = 1; i < prefixBorders.length; i++) {
            char lastChar = kmpText.charAt(i);
            while (border > 0 && lastChar != kmpText.charAt(border)) {
                border = prefixBorders[border - 1];
            }
            if (lastChar == kmpText.charAt(border)) {
                border++;
            } else {
                border = 0;
            }
            prefixBorders[i] = border;
            if (i > patternLen && border == patternLen) {
                matches.add(i - twicePatternLen);
            }
        }
        return matches;
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String pattern = scanner.next();
        String text = scanner.next();
        List<Integer> positions = findPattern(pattern, text);
        print(positions);
    }
    
    public void print(List<Integer> x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }
    
    static public void main(String[] args) throws IOException {
        new KnuthMorrisPratt().run();
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
