/*
ID: pileto1
LANG: JAVA
TASK: milk2
*/
import java.io.*;
import java.util.*;

class milk2 {
  private static class MilkFarmer {
    private int startTime;
    private int endTime;
    
    public MilkFarmer(int start, int end) {
      startTime = start;
      endTime = end;
    }
    
    public int getStartTime() {
      return startTime;
    }
    
    public int getEndTime() {
      return endTime;
    }
    
    @Override
    public String toString() {
      return "Farmer " + startTime + "-" + endTime;
    }
  }
  
  private static class MilkSequence {
    private int startTime;
    private int endTime;
    
    public MilkSequence(MilkFarmer farmer) {
      startTime = farmer.getStartTime();
      endTime = farmer.getEndTime();
    }
    
    public boolean insertMilkTime(MilkFarmer farmer) {
      int farmerStart = farmer.getStartTime();
      int farmerEnd = farmer.getEndTime();
      if (farmerEnd < farmerStart || farmerStart > endTime) {
        return false;
      } else {
        if (farmerEnd > endTime)
          endTime = farmerEnd;
        else if (farmerStart < startTime)
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
    MilkFarmer[] farmers = new MilkFarmer[numFarmers];
    MilkSequence[] milkSeqs = new MilkSequence[numFarmers];
    int milkSeqsI = -1;
    
    for (int i = 0; i < numFarmers; i++) {
      String[] in = f.readLine().split(" ");
      farmers[i] = new MilkFarmer(Integer.parseInt(in[0]), Integer.parseInt(in[1]));
    }
    
    // TODO:
    // ------------------
    // SORT farmers' timeSums descending
    // ------------------
    
    for (int i = 0; i < numFarmers; i++) {
      MilkFarmer currFarmer = farmers[i];
      boolean partOfSeq = false;
      for (int e = 0; e <= milkSeqsI; e++) {
        if (milkSeqs[e].insertMilkTime(currFarmer)) {
          partOfSeq = true;
          break;
        }
      }
      if (!partOfSeq) {
        milkSeqsI++;
        milkSeqs[milkSeqsI] = new MilkSequence(currFarmer);
      }
    }
    
    printSeqs(milkSeqs);
    
    //out.println();
    out.close();
    //System.exit(0);
  }
  
  public static void printSeqs(MilkSequence[] seqs) {
    for (MilkSequence seq : seqs) {
      System.out.println(seq);
    }
  }
}