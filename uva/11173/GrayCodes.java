import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int k;
        for (int t = scan.nextInt(); t > 0; t--) {
            scan.nextInt();
            k = scan.nextInt();
            System.out.println(k ^ (k >> 1));
        }
    }
}
