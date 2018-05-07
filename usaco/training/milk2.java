/*
ID: pileto1
LANG: JAVA
TASK: milk2
*/
import java.io.*;
import java.util.*;

class milk2 {
  private static class MilkSequence {
    private int startTime;
    private int endTime;
    
    public MilkSequence(int start, int end) {
      startTime = start;
      endTime = end;
    }
    
    public int getStartTime() {
      return startTime;
    }
    
    public int getEndTime() {
      return endTime;
    }
    
    public int getTotalMilkTime() {
      return endTime - startTime;
    }
    
    public boolean insertMilkTime(int farmerStart, int farmerEnd) {
      if (farmerEnd < startTime || farmerStart > endTime) {
        return false;
      } else {
        if (farmerEnd > endTime)
          endTime = farmerEnd;
        if (farmerStart < startTime)
          startTime = farmerStart;
        return true;
      }
    }
    
    @Override
    public String toString() {
      return "Seq " + startTime + "-" + endTime;
    }
  }
  
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("milk2.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk2.out")));
    
    int numFarmers = Integer.parseInt(f.readLine());
    String[] in0 = f.readLine().split(" ");
    ArrayList <MilkSequence> milkSeqs = new ArrayList<MilkSequence>();
    milkSeqs.add(new MilkSequence(Integer.parseInt(in0[0]), Integer.parseInt(in0[1])));
    
    for (int i = 1; i < numFarmers; i++) {
      String[] in = f.readLine().split(" ");
      int farmerStart = Integer.parseInt(in[0]);
      int farmerEnd = Integer.parseInt(in[1]);
      boolean inserted = false;
      for (int e = 0; e < milkSeqs.size(); e++) {
        MilkSequence currSeq = milkSeqs.get(e);
        if (farmerStart <= currSeq.getEndTime()) {
          if (farmerEnd <= currSeq.getEndTime()) {
            if (farmerEnd >= currSeq.getStartTime()) {
              currSeq.insertMilkTime(farmerStart, farmerEnd);
            } else {
              milkSeqs.add(e, new MilkSequence(farmerStart, farmerEnd));
            }
            inserted = true;
            break;
          } else {
            int removeI = e+1;
            while (removeI < milkSeqs.size()) {
              if (farmerEnd >= milkSeqs.get(removeI).getEndTime()) {
                milkSeqs.remove(removeI);
              } else {
                currSeq.insertMilkTime(farmerStart, farmerEnd);
                inserted = true;
                break;
              }
            }
            break;
          }
        }
      }
      if (!inserted && !milkSeqs.get(milkSeqs.size()-1).insertMilkTime(farmerStart, farmerEnd))
        milkSeqs.add(new MilkSequence(farmerStart, farmerEnd));
    }
    
    System.out.println(milkSeqs);
    
    int mi = 1;
    MilkSequence currMSeq = milkSeqs.get(0);
    while (mi < milkSeqs.size()-1) {
      if (milkSeqs.get(mi).getStartTime() - currMSeq.getEndTime() < 0) {
        currMSeq.insertMilkTime(milkSeqs.get(mi).getStartTime(), milkSeqs.get(mi).getEndTime());
        milkSeqs.remove(mi);
      } else {
        currMSeq = milkSeqs.get(mi);
        mi++;
      }
    }
    
    System.out.println(milkSeqs);
    
    int maxMilk = milkSeqs.get(0).getEndTime() - milkSeqs.get(0).getStartTime();
    int maxIdle = 0;
    for (int i = 0; i < milkSeqs.size()-1; i++) {
      MilkSequence currSeq = milkSeqs.get(i);
      MilkSequence nextSeq = milkSeqs.get(i+1);
      if (nextSeq.getTotalMilkTime() > maxMilk)
        maxMilk = nextSeq.getTotalMilkTime();
      if (nextSeq.getStartTime() - currSeq.getEndTime() > maxIdle)
        maxIdle = nextSeq.getStartTime() - currSeq.getEndTime();
    }
    
    System.out.println(maxMilk + " " + maxIdle);
    
    out.println(maxMilk + " " + maxIdle);
    out.close();
    //System.exit(0);
  }
  
}