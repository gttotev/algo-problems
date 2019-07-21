import java.util.*;

public class LCM {
  private static long lcm(int a, int b) {
    int gcd = gcd(a, b);
    return (long) a * b / gcd;
  }
  
  private static int gcd(int a, int b) {
    if (b == 0)
      return a;
    int r = a % b;
    return gcd(b, r);
  }

  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    System.out.println(lcm(a, b));
    //System.out.println(lcm(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
  }
}
