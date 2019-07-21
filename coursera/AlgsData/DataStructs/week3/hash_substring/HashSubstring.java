import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
//import java.util.Arrays;
import java.util.StringTokenizer;

public class HashSubstring {
    private static final long PRIME = 1000000007;
    private static final long MULT = 31;
    //private static final long MULT = 227;
    private static FastScanner in;
    private static PrintWriter out;
    
    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        String pattern = in.next();
        String text = in.next();
        printOccurrences(getOccurrences(text, pattern));
        //printOccurrences(naiveGetOccurrences(text, pattern));
        out.close();
    }
    
    private static List<Integer> getOccurrences(String text, String pattern) {
        List<Integer> pattList = new ArrayList<Integer>();
        long pHash = hash(pattern);
        long[] tHashes = hashAll(text, pattern.length());
        //System.out.println(pHash + " | " + Arrays.toString(tHashes));
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            if (pHash == tHashes[i] && pattern.equals(text.substring(i, i + pattern.length())))
                pattList.add(i);
        }
        return pattList;
    }
    
    private static long[] hashAll(String text, int hLength) {
        int lastTextI = text.length() - hLength;
        long[] hashes = new long[lastTextI + 1];
        hashes[lastTextI] = hash(text.substring(lastTextI, text.length()));
        long maxMult = 1;
        for (int i = 0; i < hLength; i++) {
            maxMult = (maxMult * MULT) % PRIME;
        }
        for (int i = lastTextI-1; i >= 0; i--) {
            long currHash = (MULT * hashes[i+1]) + text.charAt(i) - (maxMult * text.charAt(i+hLength));
            hashes[i] = ((currHash % PRIME) + PRIME) % PRIME;
        }
        return hashes;
    }
    
    private static long hash(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * MULT + s.charAt(i)) % PRIME;
        return hash;
    }
    
    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
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
