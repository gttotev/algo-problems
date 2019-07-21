import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixArrayLong {
    private int[] sortChars(String text) {
        int[] order = new int[text.length()];
        int[] count = new int[5];
        for (int i = 0; i < order.length; i++) {
            int c = num(text.charAt(i));
            count[c] = count[c] + 1;
        }
        for (int i = 1; i < 5; i++) {
            count[i] = count[i] + count[i - 1];
        }
        for (int i = order.length - 1; i >= 0; i--) {
            int c = num(text.charAt(i));
            int pos = count[c] - 1;
            count[c] = pos;
            order[pos] = i;
        }
        return order;
    }
    
    private int[] computeClasses(String text, int[] order) {
        int[] eclass = new int[order.length];
        for (int i = 1; i < order.length; i++) {
            int cs = order[i];
            int ps = order[i - 1];
            if (text.charAt(cs) == text.charAt(ps)) {
                eclass[cs] = eclass[ps];
            } else {
                eclass[cs] = eclass[ps] + 1;
            }
        }
        return eclass;
    }
    
    private int[] sortDoubled(int[] order, int[] eclass, int l) {
        int[] newOrder = new int[order.length];
        int[] count = new int[order.length];
        for (int i = 0; i < eclass.length; i++) {
            int ec = eclass[i];
            count[ec] = count[ec] + 1;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] = count[i] + count[i - 1];
        }
        for (int i = order.length - 1; i >= 0; i--) {
            int start = (order[i] - l + order.length) % order.length;
            int cl = eclass[start];
            int pos = count[cl] - 1;
            count[cl] = pos;
            newOrder[pos] = start;
        }
        return newOrder;
    }
    
    private int[] updateClasses(int[] order, int[] eclass, int l) {
        int[] newClass = new int[eclass.length];
        for (int i = 1; i < order.length; i++) {
            int cur = order[i];
            int curMid = (cur + l) % order.length;
            int prev = order[i - 1];
            int prevMid = (prev + l) % order.length;
            if (eclass[cur] == eclass[prev] && eclass[curMid] == eclass[prevMid]) {
                newClass[cur] = newClass[prev];
            } else {
                newClass[cur] = newClass[prev] + 1;
            }
        }
        return newClass;
    }
    
    private int[] computeSuffixArray(String text) {
        int[] order = sortChars(text);
        int[] eclass = computeClasses(text, order);
        for (int l = 1; l < order.length; l *= 2) {
            order = sortDoubled(order, eclass, l);
            eclass = updateClasses(order, eclass, l);
        }
        return order;
    }
    
    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        print(computeSuffixArray(text));
    }
    
    private void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }
    
    private int num(char b) {
        switch (b) {
            case '$':
                return 0;
            case 'A':
                return 1;
            case 'C':
                return 2;
            case 'G':
                return 3;
            case 'T':
                return 4;
        }
        return -1;
    }
    
    public static void main(String[] args) throws IOException {
        new SuffixArrayLong().run();
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
