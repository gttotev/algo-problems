import java.util.Scanner;

public class Fib {
  private static long calcFib(int n) {
    long llnum = 0;
    long lnum = (n == 0 ? 0 : 1);
    for (int i = 2; i <= n; i++) {
      long sum = llnum + lnum;
      llnum = lnum;
      lnum = sum;
    }
    return lnum;
  }

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    
    System.out.println(calcFib(n));
  }
}
