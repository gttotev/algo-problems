/*
ID: pileto1
LANG: JAVA
TASK: beads
*/
import java.io.*;
import java.util.*;

class beads_tati {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("beads.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));
    
    int numBeads = Integer.parseInt(f.readLine());
    String beads = f.readLine();
    
    int[] seqLens = new int[350];
    int seqLensMax = 0;
    
    boolean first = true;
    int beadsEnd = numBeads - 1;
    int currSeqLen = 1;
    char seqBead = beads.charAt(0);
    for (int i = 1; i <= beadsEnd; i++) {
      char currBead = beads.charAt(i);
      if (seqBead == 'w' && currBead != 'w') {
        seqBead = currBead;
      }
      if (currBead == 'w' || currBead == seqBead) {
        currSeqLen++;
      } else {
        if (first) {
          for (int e = beadsEnd; e > currSeqLen; e--) {
            if (beads.charAt(e) == 'w' || beads.charAt(e) == seqBead) {
              currSeqLen++;
            } else {
              beadsEnd = e;
              break;
            }
          }
          first = false;
        }
        seqLens[seqLensMax] = currSeqLen;
        seqLensMax++;
        currSeqLen = 1;
        seqBead = currBead;
      }
    }
    //clean up trailing same beads
    seqLens[seqLensMax] = currSeqLen;
    
    int maxLenSum = seqLens[seqLensMax] + seqLens[0];
    for (int i = 0; i < seqLensMax; i++) {
      int currLenSum = seqLens[i] + seqLens[i+1];
      if (currLenSum > maxLenSum)
        maxLenSum = currLenSum;
    }
    
    System.out.println(maxLenSum);
    
    //out.println(maxLenSum);
    out.close();
    //System.exit(0);
  }
}