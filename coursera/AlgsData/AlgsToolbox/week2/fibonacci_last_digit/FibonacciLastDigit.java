import java.util.*;

public class FibonacciLastDigit {
    private static int getFibonacciLastDigit(int n) {
        int llDigit = 0;
        int lDigit = (n == 0 ? 0 : 1);
        for (int i = 2; i <= n; i++) {
          int digitSum = (llDigit + lDigit) % 10;
          llDigit = lDigit;
          lDigit = digitSum;
        }
        return lDigit;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        
        int c = getFibonacciLastDigit(n);
        System.out.println(c);
    }
}

