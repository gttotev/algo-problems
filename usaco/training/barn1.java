/*
ID: pileto1
LANG: JAVA
TASK: barn1
*/
import java.io.*;
import java.util.*;

class barn1 {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("barn1.in"));
    StringTokenizer in = new StringTokenizer(f.readLine());
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barn1.out")));
    
    int numBoards = Integer.parseInt(in.nextToken());
    int numStalls = Integer.parseInt(in.nextToken());
    int numCows = Integer.parseInt(in.nextToken());
    
    Object[] stallsSorted = sortStallsInput(f, numCows);
    ArrayList<Integer> stallSeq = new ArrayList<Integer>();
    ArrayList<Integer> emptySorted = new ArrayList<Integer>();
    int stallsCovered = 0;
    
    int currStart = (Integer) stallsSorted[0];
    int lastStall = currStart;
    for (int i = 1; i < numCows; i++) {
      int currStall = (Integer) stallsSorted[i];
      if (currStall - lastStall > 1) {
        int seqStalls = lastStall - currStart + 1;
        int seqEmpty = currStall - lastStall - 1;
        stallSeq.add(seqStalls);
        stallsCovered += seqStalls;
        stallSeq.add(seqEmpty);
        int insertI = Collections.binarySearch(emptySorted, seqEmpty);
        emptySorted.add(
                        (insertI < 0 ? (Math.abs(insertI + 1) > emptySorted.size() ? emptySorted.size() : Math.abs(insertI + 1)) : 
                                           insertI), seqEmpty);
        currStart = currStall;
      }
      lastStall = currStall;
    }
    stallSeq.add(lastStall - currStart + 1);
    stallsCovered += lastStall - currStart + 1;
    
    for (int used = (stallSeq.size() / 2) + 1; used > numBoards; used--) {
      stallsCovered += emptySorted.remove(0);
    }
    
    System.out.println(stallsCovered);
    out.println(stallsCovered);
    
    out.close();
    System.exit(0);
  }
  
  private static Object[] sortStallsInput(BufferedReader f, int numCows) throws IOException {
    ArrayList<Integer> stallsSorted = new ArrayList<Integer>();
    for (int i = 0; i < numCows; i++) {
      int currStall = Integer.parseInt(f.readLine());
      int insertI = Math.abs(Collections.binarySearch(stallsSorted, currStall));
      stallsSorted.add(insertI - 1, currStall);
    }
    return stallsSorted.toArray();
  }
}