/*
ID: pileto1
LANG: JAVA
PROG: ride
*/
import java.io.*;
import java.util.*;

class ride {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("ride.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ride.out")));
    //StringTokenizer st = new StringTokenizer(f.readLine());
    
    String comet = f.readLine();
    String group = f.readLine();
    
    int cometProd = 1;
    for (int i = 0; i < comet.length(); i++) {
      int charCode = (int)comet.charAt(i);
      cometProd *= charCode - 64;
    }
    
    int groupProd = 1;
    for (int i = 0; i < group.length(); i++) {
      int charCode = (int)group.charAt(i);
      groupProd *= charCode - 64;
    }
    
    if (cometProd % 47 == groupProd % 47)
      out.println("GO");
    else
      out.println("STAY");
    out.close();
    System.exit(0);
  }
}