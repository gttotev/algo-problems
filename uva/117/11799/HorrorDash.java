import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int tests = scan.nextInt();
        for (int t = 1; t <= tests; t++) {
            int n = scan.nextInt();
            int maxSpeed = 0;
            for (int i = 0; i < n; i++) {
                int speed = scan.nextInt();
                if (speed > maxSpeed)
                    maxSpeed = speed;
            }
            System.out.format("Case %d: %d\n", t, maxSpeed);
        }
    }
}
