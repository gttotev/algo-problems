/*
ID: pileto1
LANG: JAVA
TASK: wormhole
*/
import java.io.*;
import java.util.*;

class wormhole {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("wormhole.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wormhole.out")));
    
    int numHoles = Integer.parseInt(f.readLine());
    int numHoleConnections = 0;
    int numOutHoles = numHoles;
    
    int[][] holes = new int[numHoles][2];
    HashMap<Integer, Integer> holesYMap = new HashMap<Integer, Integer>();
    for (int i = 0; i < numHoles; i++) {
      String[] in = f.readLine().split(" ");
      int holeY = Integer.parseInt(in[1]);
      holes[i][0] = Integer.parseInt(in[0]);
      holes[i][1] = holeY;
      holesYMap.put(holeY, (holesYMap.get(holeY) == null ? 1 : holesYMap.get(holeY)+1));
    }
    Iterator<Integer> iterator = holesYMap.keySet().iterator();
    while (iterator.hasNext()) {
      int currHoles = holesYMap.get(iterator.next());
      numHoleConnections += currHoles-1;
      numOutHoles -= (currHoles > 1 ? currHoles : 0);
    }
    
    /*int loopHoleCombos = findTotalCombos(numHoleConnections, numHoleConnections + numOutHoles) - findDuplicates(numHoles);
    System.out.println("loopHoleCombos: " + loopHoleCombos + " numHoleConnections: " + numHoleConnections + " numOutHoles: " + numOutHoles);
    out.println(loopHoleCombos);*/
    System.out.println(findTotalCombos(numHoleConnections+numOutHoles-1) * numHoleConnections);
    //System.out.println(findTotalCombos(numHoleConnections+numOutHoles-1) - findTotalCombos(numHoleConnections+numOutHoles-3));
    out.close();
    //System.exit(0);
  }
  
  public static int findTotalCombos(int connections) {
    if (connections < 2) {
      return 1;
    }
    return connections * findTotalCombos(connections-2);
  }
  
  public static int findDuplicates(int connectedHoles) {
    if (connectedHoles > 3) {
      return ((connectedHoles / 2) - 1) + findDuplicates(connectedHoles-2);
    }
    return 0;
  }
  
  /*public static List<Integer[]> pairs(Integer[] a) {
    List<Integer[]> ret = new ArrayList<>();
    if (a.length == 2) {
      ret.add(a);
      return ret;
    }
    for (int i = 1; i < a.length; i++) {
      Integer[] na = new Integer[a.length-2];
      for (int j = 0; j < na.length; j++) {
        na[j] = a[1 + j + (i-1 <= j ? 1 : 0)];
      }
      List<Integer[]> p = pairs(na);
      for(Integer[] k : p) {
        Integer[] ia = new Integer[a.length];
        ia[0] = a[0];
        ia[1] = a[i];
        System.arraycopy(k, 0, ia, 2, k.length);
        ret.add(ia);
      }
    }
    return ret;
 }*/
}