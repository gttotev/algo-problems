/*
ID: pileto1
LANG: JAVA
TASK: prog
*/
import java.io.*;
import java.util.*;

class BoilerPlateProg {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("prog.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("prog.out")));
    
    String input = f.readLine();
    
    out.println();
    out.close();
    System.exit(0);
  }
}
