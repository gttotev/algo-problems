import java.util.*;
import java.io.*;

public class MajorityElement {
    private static boolean hasMajorityElement(int[] arr, int arrLength/*,int left, int right*/) {
        int maxVal = 0;
        HashMap<Integer,Integer> elements = new HashMap<Integer,Integer>();
        for (int i = 0; i < arrLength; i++) {
            int key = arr[i];
            Integer value = elements.get(key);
            value = (value == null ? 1 : value+1);
            elements.put(key, value);
            if (value > maxVal)
                maxVal = value;
        }
        return maxVal > (arrLength / 2);
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        if (hasMajorityElement(a, n/*, 0, n*/)) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }
    
    
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

