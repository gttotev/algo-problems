/*
ID: pileto1
LANG: JAVA
TASK: barn1
*/
import java.io.*;
import java.util.*;

class barn1_test {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("barn1.in"));
    StringTokenizer in = new StringTokenizer(f.readLine());
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barn1.out")));
    
    ArrayList<Integer> stallSeq = new ArrayList<Integer>();
    
    int numBoards = Integer.parseInt(in.nextToken());
    int numStalls = Integer.parseInt(in.nextToken());
    int numCows = Integer.parseInt(in.nextToken());
    
    // Binary search insertion sort into stallSeq
    
    int currStart = Integer.parseInt(f.readLine());
    int lastStall = currStart;
    for (int i = 1; i < numCows; i++) {
      int currStall = Integer.parseInt(f.readLine());
      if (currStall - lastStall > 1) {
        stallSeq.add(lastStall - currStart + 1);
        stallSeq.add(currStall - lastStall - 1);
        currStart = currStall;
      }
      lastStall = currStall;
    }
    stallSeq.add(lastStall - currStart + 1);
    
    // Number of empty stalls = numBoard - 1 check all possible
    // locations for min stall coverage.
    
    if (numBoards == 2) {
      int to = 0;
      int minSeq = Integer.MAX_VALUE;
      while (to < stallSeq.size()) {
        int currSeq = 0;
        for (int e = 0; e <= to; e++) {
          currSeq += stallSeq.get(e);
        }
        for (int e = to + 2; e < stallSeq.size(); e++) {
          currSeq += stallSeq.get(e);
        }
        to++;
        if (currSeq < minSeq)
          minSeq = currSeq;
      }
      System.out.println(minSeq);
      out.println(minSeq);
    } else {
      int stallsBlocked = 0;
      int hybridReqd = (stallSeq.size() / 2 + 1) - numBoards;
      for (int connect = 0; connect < hybridReqd; connect++) {
        int minSeq = stallSeq.get(0) + stallSeq.get(1) + stallSeq.get(2);
        int minSeqI = 1;
        for (int i = 3; i < stallSeq.size() - 1; i += 2) {
          int currSeq = stallSeq.get(i - 1) + stallSeq.get(i) + stallSeq.get(i + 1);
          if (currSeq <= minSeq) {
            minSeq = currSeq;
            minSeqI = i;
          }
        }
        stallsBlocked += minSeq;
        stallSeq.set(minSeqI - 1, 0);
        stallSeq.remove(minSeqI);
        stallSeq.remove(minSeqI);
      }
      //if (stallsBlocked == 0) {
      for (int i = 0; i < stallSeq.size(); i += 2) {
        stallsBlocked += stallSeq.get(i);
      }
      //}
      
      System.out.println(stallsBlocked);
      out.println(stallsBlocked);
    }
    out.close();
    System.exit(0);
  }
}