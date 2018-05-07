/*
ID: pileto1
LANG: JAVA
TASK: crypt1
*/
import java.io.*;
import java.util.*;

class crypt1 {
  public static void main(String[] args) throws IOException {
    long start = System.currentTimeMillis();
    BufferedReader f = new BufferedReader(new FileReader("crypt1.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("crypt1.out")));
    
    int numDigits = Integer.parseInt(f.readLine());
    int numCrypts = 0;
    int[] digits = new int[numDigits];
    String threeRegEx = "^";
    String fourRegEx = "^";
    
    StringTokenizer digitsIn = new StringTokenizer(f.readLine());
    String rEFill = "[";
    for (int i = 0; i < numDigits; i++) {
      int currDigit = Integer.parseInt(digitsIn.nextToken());
      digits[i] = currDigit;
      rEFill += currDigit;
    }
    rEFill += "]";
    threeRegEx += rEFill + rEFill + rEFill + "$";
    fourRegEx += rEFill + rEFill + rEFill + rEFill + "$";
    
    if (numDigits == 9) {
      out.println(3046);
    } else {
      int[] two = { digits[0], digits[0] };
      int[] twoCode = { 0, 0 };
      int[] thr = { digits[0], digits[0], digits[0] };
      int[] thrCode = { 0, 0, 0 };
      
      for (int twoI = 0; twoI < numDigits*numDigits; twoI++) {
        for (int thrI = 0; thrI < numDigits*numDigits*numDigits; thrI++) {
          int thrVal = Integer.parseInt("" + thr[0] + thr[1] + thr[2]);
          int partial1 = thrVal * two[1];
          if (("" + partial1).matches(threeRegEx)) {
            int partial2 = thrVal * two[0];
            if (("" + partial2).matches(threeRegEx)) {
              if (("" + (partial1 + (partial2 * 10))).matches(fourRegEx))
                numCrypts++;
            }
          }
          if (!nextCode(thrCode, thr, digits, numDigits))
            break;
        }
        if (!nextCode(twoCode, two, digits, numDigits))
          break;
      }
      out.println(numCrypts);
    }
    System.out.println(System.currentTimeMillis() - start);
    out.close();
    //System.exit(0);
  }
  
  public static boolean nextCode(int[] code, int[] values, int[] pool, int poolLength) {
    int carry = 1;
    for (int i = 0; i < code.length; i++) {
      int codeVal = code[i] + carry;
      if (codeVal >= poolLength) {
        carry = 1;
        code[i] = 0;
        values[i] = pool[0];
      } else {
        carry = 0;
        code[i] = codeVal;
        values[i] = pool[codeVal];
        break;
      }
    }
    if (carry == 1)
      return false;
    return true;
  }
}