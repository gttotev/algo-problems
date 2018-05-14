import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class Sort {
    public static void main(String[] args) throws java.io.IOException {
        BufferedReader in = new BufferedReader(new java.io.FileReader("sort.in"));
        PrintWriter out = new PrintWriter(new java.io.File("sort.out"));
        int i, j, num, si, dj;
        int moo = 1;
        int n = Integer.parseInt(in.readLine());
        int[] arr = new int[n];
        int[] sorted = new int[n];
        for (i = 0; i < n; i++) {
            j = Integer.parseInt(in.readLine());
            arr[i] = j;
            sorted[i] = j;
        }
        Arrays.sort(sorted);
        for (i = 0; i < n; i++) {
            num = arr[i];
            j = Arrays.binarySearch(sorted, num);
            //System.out.println(i + " " + j);
            //dj = (int) Math.signum(i - j);
            if (i > j) {
                for (; j != i && sorted[j] == num; j++);
                if (sorted[j] != num) {
                    //j -= 2;
                    moo = Math.max(moo, Math.abs(j - 2 - i));
                }
            }
        }
        out.println(moo);
        in.close();
        out.close();
    }
}
