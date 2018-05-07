/*
ID: pileto1
LANG: JAVA
TASK: namenum
*/
import java.io.*;
import java.util.*;

class namenum {  
  private static class KeypadName {
    public static final ArrayList<String> dict = new ArrayList<String>();
    public static boolean checkName(String name) {
      return (Collections.binarySearch(dict, name) >= 0);
    }
    
    public static final char[][] keypad = {
      {}, {},
      {'A', 'B', 'C'},
      {'D', 'E', 'F'},
      {'G', 'H', 'I'},
      {'J', 'K', 'L'},
      {'M', 'N', 'O'},
      {'P', 'R', 'S'},
      {'T', 'U', 'V'},
      {'W', 'X', 'Y'}
    };
    
    private int[] numCode;
    private int[] numMags;
    
    public KeypadName(String num) {
      numCode = new int[num.length()];
      for (int i = 0; i < num.length(); i++) {
        numCode[i] = Integer.parseInt(num.substring(i, i+1));
      }
      numMags = new int[numCode.length];
    }
    
    public String nextName() {
      String name = "";
      int carry = 1;
      for (int i = numCode.length-1; i >= 0; i--) {
        if (numMags[i] + carry > 2) {
          numMags[i] = 0;
          carry = 1;
        } else {
          numMags[i] = numMags[i] + carry;
          carry = 0;
        }
        name = keypad[numCode[i]][numMags[i]] + name;
      }
      if (carry == 1) {
        numMags = new int[numMags.length];
        return "END";
      }
      return name;
    }
    
    public String getName() {
      String name = "";
      for (int i = 0; i < numCode.length; i++) {
        name += keypad[numCode[i]][numMags[i]];
      }
      return name;
    }
  }
  
  public static void main(String[] args) throws IOException {
    long start = System.currentTimeMillis();
    BufferedReader dictIn = new BufferedReader(new FileReader("dict.txt"));
    String name = null;
    while ((name = dictIn.readLine()) != null) {
      KeypadName.dict.add(name);
    }
    BufferedReader f = new BufferedReader(new FileReader("namenum.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("namenum.out")));
    
    KeypadName cow = new KeypadName(f.readLine());
    
    String cowName = cow.getName();
    /*if (cowName.length() == 12) {
      for (String thing : KeypadName.dict) {
        if (thing.length() == 12)
          out.println(thing);
      }
    } else {*/
    boolean found = false;
    do {
      if (KeypadName.checkName(cowName)) {
        out.println(cowName);
        found = true;
      }
    } while (!((cowName = cow.nextName()).equals("END")));
    
    if (!found) {
      out.println("NONE");
    }
    out.close();
    System.out.println(System.currentTimeMillis() - start);
    System.exit(0);
  }
}