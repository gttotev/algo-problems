/*
ID: pileto1
LANG: JAVA
TASK: milk
*/
import java.io.*;
import java.util.*;

class milk {
  private static class MilkFarmer {
    private int price;
    private int units;
    
    public MilkFarmer(String dataStr) {
      String[] data = dataStr.split(" ");
      price = Integer.parseInt(data[0]);
      units = Integer.parseInt(data[1]);
    }
    
    public int getPrice() { return price; }
    public int getUnits() { return units; }
    
    @Override
    public String toString() {
      return "<Price: " + price + " | Units: " + units + ">";
    }
  }
  
  public static void main(String[] args) throws IOException {
    long start = System.currentTimeMillis();
    BufferedReader f = new BufferedReader(new FileReader("milk.in"));
    StringTokenizer in = new StringTokenizer(f.readLine());
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk.out")));
    
    ArrayList<MilkFarmer> farmers = new ArrayList<MilkFarmer>();
    
    int unitsReqd = Integer.parseInt(in.nextToken());
    int numFarmers = Integer.parseInt(in.nextToken());
    int totalPrice = 0;
    
    for (int i = 0; i < numFarmers; i++) {
      MilkFarmer currFarmer = new MilkFarmer(f.readLine());
      int e = 0;
      while (e < farmers.size() && farmers.get(e).getPrice() < currFarmer.getPrice())
        e++;
      farmers.add(e, currFarmer);
    }
    
    for (MilkFarmer currFarmer : farmers) {
      unitsReqd -= currFarmer.getUnits();
      if (unitsReqd <= 0) {
        totalPrice += (currFarmer.getUnits() + unitsReqd) * currFarmer.getPrice();
        break;
      } else {
        totalPrice += currFarmer.getUnits() * currFarmer.getPrice();
      }
    }
    
    out.println(totalPrice);
    out.close();
    System.out.println(System.currentTimeMillis() - start);
    System.exit(0);
  }
}