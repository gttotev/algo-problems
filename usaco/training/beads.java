/*
ID: pileto1
LANG: JAVA
TASK: beads
*/
import java.io.*;
import java.util.*;

class beads {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("beads.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));
    
    int numBeads = Integer.parseInt(f.readLine());
    String beads = f.readLine();
    
    int absStartI = 0;
    char firstBead = beads.charAt(0);
    
    for (int i = 1; i < numBeads; i++) {
      char currBead = beads.charAt(i);
      if (currBead != firstBead) {
        absStartI = i;
        break;
      }
    }
    
    if (absStartI == 0) {
      out.println(numBeads);
    } else {
      int startI = absStartI;
      int maxLenSum = 0;
      boolean restart = false;
      do {
        int currLenSum = 2;
        char hBead = beads.charAt(startI);
        int nextDivI = startI;
        int chainDivI = startI;
        char tBead = hBead;
        
        for (int i = (startI + 1 == numBeads ? 0 : startI + 1); currLenSum < numBeads; i = (i + 1 == numBeads ? 0 : i + 1)) {
          char currBead = beads.charAt(i);
          if (hBead == 'w') {
            if (currBead != 'w')
              hBead = currBead;
            currLenSum++;
          } else {
            if (currBead == 'w') {
              if (nextDivI == startI)
                nextDivI = i;
              currLenSum++;
            } else if (currBead == hBead) {
              currLenSum++;
            } else {
              chainDivI = i;
              tBead = currBead;
              break;
            }
          }
        }
        
        for (int i = (chainDivI + 1 == numBeads ? 0 : chainDivI + 1); currLenSum < numBeads; i = (i + 1 == numBeads ? 0 : i + 1)) {
          char currBead = beads.charAt(i);
          if (tBead == 'w') {
            if (currBead != 'w')
              tBead = currBead;
            currLenSum++;
          } else {
            if (currBead == 'w') {
              currLenSum++;
            } else if (currBead == tBead) {
              currLenSum++;
            } else {
              break;
            }
          }
        }
        
        if (chainDivI < startI && nextDivI == startI)
          restart = true;
        else if (chainDivI < startI && !(nextDivI > startI))
          restart = true;
        
        if (nextDivI == startI) {
          startI = chainDivI;
        } else {
          startI = nextDivI;
        }
        
        if (currLenSum > maxLenSum)
          maxLenSum = currLenSum;
      } while (!(restart && startI >= absStartI) && maxLenSum < numBeads);
      
      out.println(maxLenSum);
    }
    
    out.close();
    System.exit(0);
  }
}