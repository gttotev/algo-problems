/*
ID: pileto1
LANG: JAVA
TASK: dualpal
*/
import java.io.*;
import java.util.*;

class dualpal {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("dualpal.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dualpal.out")));
    
    String[] in = f.readLine().split(" ");
    int numPals = Integer.parseInt(in[0]);
    int numFound = 0;
    int from = Integer.parseInt(in[1]);
    
    for (int i = from + 1; numFound < numPals; i++) {
      int currFound = 0;
      for (int base = 2; base < 11; base++) {
        if (isPal(Integer.toString(i, base))) {
          currFound++;
          if (currFound == 2) {
            numFound++;
            out.println(i);
            break;
          }
        }
      }
    }
    
    out.close();
    System.exit(0);
  }
  
  private static boolean isPal(String numStr) {
    for (int i = 0; i <= numStr.length() / 2; i++) {
      if (numStr.charAt(i) != numStr.charAt(numStr.length() - 1 - i))
        return false;
    }
    return true;
  }
}