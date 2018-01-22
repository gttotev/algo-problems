import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] yout = {"S", "", "N"};
        String[] xout = {"O", "", "E"};
        Scanner scan = new Scanner(System.in);
        int k = scan.nextInt();
        while (k > 0) {
            int n = scan.nextInt();
            int m = scan.nextInt();
            for (int i = 0; i < k; i++) {
                int x = scan.nextInt();
                int y = scan.nextInt();
                if (x == n || y == m) {
                    System.out.println("divisa");
                } else {
                    int ym = y - m;
                    int xn = x - n;
                    System.out.println(yout[ym / Math.abs(ym) + 1] + xout[xn / Math.abs(xn) + 1]);
                }
            }
            k = scan.nextInt();
        }
    }
}
