/*
ID: pileto1
LANG: JAVA
PROG: gift1
*/
import java.io.*;
import java.util.*;

class gift1 {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("gift1.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gift1.out")));
    //StringTokenizer st = new StringTokenizer(f.readLine());
    
    int numPeople = Integer.parseInt(f.readLine());
    String[] people = new String[numPeople];
    int[] giveDiff = new int[numPeople];
    
    for (int i = 0; i < numPeople; i++) {
      people[i] = f.readLine();
    }
    
    for (int i = 0; i < numPeople; i++) {
      String giver = f.readLine();
      int giverI = findLinearArr(people, giver);
      int[] givePeopleArr = givePeopleArr(f.readLine());
      int giveGift = givePeopleArr[0];
      int numRecs = givePeopleArr[1];
      int divGift = 0;
      if (numRecs > 0) {
        giveDiff[giverI] -= giveGift - (giveGift % numRecs);
        divGift = giveGift / numRecs;
      }
      for (int e = 0; e < numRecs; e++) {
        int recI = findLinearArr(people, f.readLine());
        giveDiff[recI] += divGift;
      }
    }
    
    for (int i = 0; i < numPeople; i++) {
      out.println(people[i] + " " + giveDiff[i]);
    }
    out.close();
    System.exit(0);
  }
  
  private static int[] givePeopleArr(String giveStr) {
    String[] giveStrArr = giveStr.split(" ");
    int[] giveIntArr = new int[giveStrArr.length];
    for (int i = 0; i < giveStrArr.length; i++) {
      giveIntArr[i] = Integer.parseInt(giveStrArr[i]);
    }
    return giveIntArr;
  }
  
  public static int findLinearArr(String[] arr, String value) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i].equals(value))
        return i;
    }
    return -1;
  }
}