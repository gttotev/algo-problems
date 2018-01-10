import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        char[] out = {'<', '=', '>'};
        for (int i = 0; i < t; i++) {
            long nd = scan.nextLong() - scan.nextLong();
            System.out.println(out[nd == 0 ? 1 : (int) (nd / Math.abs(nd)) + 1]);
        }
    }
}
