import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] ab = new int[2];
        int n, i, s, r, c;
        while ((n = scan.nextInt()) > 0) {
            ab[0] = 0;
            ab[1] = 0;
            c = 0;
            for (i = 0, s = 1; i < 31; i++, s <<= 1) {
                if ((r = n & s) > 0) {
                    ab[c] = ab[c] + r;
                    c = (c + 1) % 2;
                }
            }
            System.out.format("%d %d\n", ab[0], ab[1]);
        }
    }
}
