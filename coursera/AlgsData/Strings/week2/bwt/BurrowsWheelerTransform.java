import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BurrowsWheelerTransform {
    String BWT(String text) {
        StringBuilder transform = new StringBuilder();
        int textLen = text.length();
        String[] rotations = new String[textLen];
        for (int i = 0; i < textLen; i++) {
            rotations[i] = text.substring(i) + text.substring(0, i);
        }
        Arrays.sort(rotations);
        for (String str : rotations) {
            transform.append(str.charAt(textLen-1));
        }
        return transform.toString();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
    
    public static void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
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
