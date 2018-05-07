/*
ID: pileto1
LANG: JAVA
TASK: palsquare
*/
import java.io.*;
import java.util.*;

class palsquare {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("palsquare.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("palsquare.out")));
    
    int base = Integer.parseInt(f.readLine());
    
    for (int i = 1; i <= 300; i++) {
      String sqBase = Integer.toString((int) Math.pow(i, 2), base);
      if (isPal(sqBase))
        out.println(Integer.toString(i, base).toUpperCase() + " " + sqBase.toUpperCase());
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