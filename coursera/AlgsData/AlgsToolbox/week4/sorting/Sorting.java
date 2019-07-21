import java.io.*;
import java.util.*;

public class Sorting {
    private static Random random = new Random();
    
    public static int[] partition3(int[] arr, int left, int right) {
        int pivot = arr[left];
        int lowEnd = left;
        int highStart = left + 1;
        for (int i = highStart; i <= right; i++) {
            int il = arr[i];
            if (il == pivot) {
                swap(arr, i, highStart);
                highStart++;
            } else if (il < pivot) {
                highStart++;
                lowEnd++;
                swap(arr, i, lowEnd);
                if (highStart - lowEnd != 1)
                    swap(arr, i, highStart-1);
            }
        }
        swap(arr, left, lowEnd);
        return new int[] {lowEnd-1, highStart};
    }

    private static void randomizedQuickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivotI = random.nextInt(right - left + 1) + left;
        swap(arr, left, pivotI);
        int[] mids = partition3(arr, left, right);
        randomizedQuickSort(arr, left, mids[0]);
        randomizedQuickSort(arr, mids[1], right);
    }
    
    private static void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        randomizedQuickSort(a, 0, n - 1);
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
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

