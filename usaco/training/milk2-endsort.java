/*
ID: pileto1
LANG: JAVA
TASK: milk2
*/
import java.io.*;
import java.util.*;

class milk2 {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("milk2.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk2.out")));
    
    int numFarmers = Integer.parseInt(f.readLine());
    int[] startTimes = new int[numFarmers];
    int[] endTimes = new int[numFarmers];
    
    for (int i = 0; i < numFarmers; i++) {
      String[] in = f.readLine().split(" ");
      startTimes[i] = Integer.parseInt(in[0]);
      endTimes[i] = Integer.parseInt(in[1]);
    }
    
    // TODO:
    // ---------------------------------
    // SORT endTimes PARALLEL startTimes 
    // ---------------------------------
    
    int maxMilk = 0;
    int[] maxIdle = new int[numFarmers*2];
    int maxIdleI = 0;
    
    int seqStartTime = startTimes[0];
    int seqEndTime = endTimes[0];
    int currMilkTime = seqEndTime - seqStartTime;
    
    for (int i = 1; i < numFarmers; i++) {
      int currStartTime = startTimes[i];
      int currEndTime = endTimes[i];
      if (currStartTime >= seqStartTime && currStartTime <= seqEndTime) {
        currMilkTime += currEndTime - seqEndTime;
      } else if (currStartTime < seqStartTime) {
        //Reaches into prev seq FIX -- create seq store
        currMilkTime = currEndTime - currStartTime;
        maxIdle[maxIdleI] = (currStartTime - maxIdle[maxIdleI+1] < 1 ? 0 : currStartTime - maxIdle[maxIdleI+1]);
        maxIdle[maxIdleI+1] = currStartTime;
        seqStartTime = currStartTime;
      } else if (currStartTime > seqEndTime) {
        maxMilk = (currMilkTime > maxMilk ? currMilkTime : maxMilk);
        currMilkTime = currEndTime - currStartTime;
        //Add into first FIX
        maxIdleI = addIdleTime(maxIdle, maxIdleI, currStartTime - seqEndTime, seqEndTime);
        seqStartTime = currStartTime;
      }
      seqEndTime = currEndTime;
    }
    
    int max = maxIdle[0];
    for (int i = 2; i <= maxIdleI; i += 2) {
      if (maxIdle[i] > max)
        max = maxIdle[i];
    }
    
    System.out.println((currMilkTime > maxMilk ? currMilkTime : maxMilk) + " " + max);
    //out.println();
    out.close();
    //System.exit(0);
  }
  
  private static int addIdleTime(int[] idleTimes, int idleI, int idle, int seqEndTime) {
    idleI += 2;
    idleTimes[idleI] = idle;
    idleTimes[idleI+1] = seqEndTime;
    return idleI;
  }
}