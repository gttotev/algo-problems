import java.util.Scanner;
import java.util.Arrays;

public class PlacingParentheses {
    private static long getMaximValue(int[] digits, char[] ops) {
      long[][] minTable = new long[digits.length][digits.length];
      long[][] maxTable = new long[digits.length][digits.length];
      for (int i = 0; i < digits.length; i++) {
          minTable[i][i] = digits[i];
          maxTable[i][i] = digits[i];
      }
      for (int m = 1; m < digits.length; m++) {
          for (int i = 0; i < digits.length - m; i++) {
              int j = m + i;
              long min = Long.MAX_VALUE;
              long max = Long.MIN_VALUE;
              for (int o = i; o < j; o++) {
                  char op = ops[o];
                  long minLow = minTable[i][o];
                  long minHigh = minTable[o+1][j];
                  long maxLow = maxTable[i][o];
                  long maxHigh = maxTable[o+1][j];
                  long[] vals = {eval(minLow, minHigh, op), eval(minLow, maxHigh, op), 
                      eval(maxLow, minHigh, op), eval(maxLow, maxHigh, op)};
                  Arrays.sort(vals);
                  min = Math.min(min, vals[0]);
                  max = Math.max(max, vals[3]);
              }
              minTable[i][j] = min;
              maxTable[i][j] = max;
          }
      }
      return maxTable[0][digits.length-1];
    }

    private static long eval(long a, long b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            assert false;
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        int[] digits = new int[(exp.length()/2)+1];
        char[] ops = new char[exp.length()/2];
        for (int i = 0; i < exp.length(); i++) {
            if (i % 2 == 0) {
                digits[i/2] = (int)(exp.charAt(i) - '0');
            } else {
                ops[i/2] = exp.charAt(i);
            }
        }
        System.out.println(getMaximValue(digits, ops));
    }
}

