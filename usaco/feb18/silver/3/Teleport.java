import java.io.BufferedReader;
import java.io.PrintWriter;

public class Teleport {
    public static void main(String[] args) throws java.io.IOException {
        BufferedReader in = new BufferedReader(new java.io.FileReader("teleport.in"));
        PrintWriter out = new PrintWriter(new java.io.File("teleport.out"));
        int i, a, b, dist;
        String[] input;
        int n = Integer.parseInt(in.readLine());
        int[] from = new int[n];
        int[] to = new int[n];
        int dsum = 0;
        for (i = 0; i < n; i++) {
            input = in.readLine().split(" ");
            a = Integer.parseInt(input[0]);
            b = Integer.parseInt(input[1]);
            dist = Math.abs(a - b);
            if (dist <= Math.abs(a)) {
                dsum += dist;
            } else {
                
            }
            from[i] = a;
            to[i] = b;
        }
        out.println(dsum);
        in.close();
        out.close();
    }
}
