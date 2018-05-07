/*
ID: pileto1
LANG: JAVA
TASK: skidesign
*/
import java.io.*;
import java.util.*;

class skidesign {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("skidesign.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("skidesign.out")));
    
    int numHills = Integer.parseInt(f.readLine());
    ArrayList<Integer> hills = new ArrayList<Integer>();
    int totalPrice = 0;
    
    for (int i = 0; i < numHills; i++) {
      int currHill = Integer.parseInt(f.readLine());
      int insertI = Collections.binarySearch(hills, currHill);
      hills.add((insertI > -1 ? insertI : Math.abs(insertI) - 1), currHill);
    }
    
    for (int i = 0; i < numHills/2; i++) {
      int hill1 = hills.get(i);
      int hill2 = hills.get(hills.size() - 1 - i);
      int hillDiff = hill2 - hill1 - 17;
      System.out.println(hill1 + ", " + hill2 + " " + hillDiff);
      if (hillDiff > 0) {
        if (hillDiff % 2 == 0) {
          totalPrice += ((int) Math.pow(hillDiff / 2, 2)) * 2;
        } else {
          totalPrice += ((int) Math.pow(hillDiff / 2, 2)) + ((int) Math.pow((hillDiff / 2)+1, 2));
        }
      }
    }
    System.out.println(totalPrice);
    
    out.println(totalPrice); // For 50 test: 22946
    out.close();
    //System.exit(0);
  }
}