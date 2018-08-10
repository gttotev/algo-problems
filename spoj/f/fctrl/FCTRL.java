import java.io.BufferedReader;
import java.io.PrintWriter;

class FCTRL {
    public static void main(String[] args) throws java.io.IOException {
        BufferedReader in = new BufferedReader(new java.io.InputStreamReader(System.in)); 
        int n, z, i;
        for (int t = Integer.parseInt(in.readLine()); t > 0; t--) {
            n = Integer.parseInt(in.readLine());
            z = 0;
            while (n >= 5) {
                z += n / 5;
                n /= 5;
            }
            System.out.println(z);
        }
        in.close();
    }
}
