import java.util.Scanner;
import java.util.Arrays;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        for (int i = 1; i <= t; i++) {
            int[] employees = {scan.nextInt(), scan.nextInt(), scan.nextInt()};
            Arrays.sort(employees);
            System.out.format("Case %d: %d\n", i, employees[1]);
        }
    }
}
