import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n, cur, prev, diff;
        boolean[] diffs;
        String result;
        while (scan.hasNext()) {
            n = scan.nextInt();
            diffs = new boolean[n-1];
            result = "J";
            prev = scan.nextInt();
            for (int i = 1; i < n; i++) {
                cur = scan.nextInt();
                diff = Math.abs(cur - prev) - 1;
                if (diff < 0 || diff > n - 2 || diffs[diff]) {
                    result = "Not j";
                    scan.nextLine();
                    break;
                }
                diffs[diff] = true;
                prev = cur;
            }
            System.out.format("%solly\n", result);
        }
    }
}
