/*
ID: pileto1
LANG: JAVA
TASK: combo
*/
import java.io.*;
import java.util.*;

class combo {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("combo.in"));
    StringTokenizer in = null;
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("combo.out")));
    
    int numLockDigits = Integer.parseInt(f.readLine());
    in = new StringTokenizer(f.readLine());
    int[] johnCombo = {Integer.parseInt(in.nextToken()), Integer.parseInt(in.nextToken()), Integer.parseInt(in.nextToken())};
    resetCombo(johnCombo, numLockDigits);
    in = new StringTokenizer(f.readLine());
    int[] masterCombo = {Integer.parseInt(in.nextToken()), Integer.parseInt(in.nextToken()), Integer.parseInt(in.nextToken())};
    resetCombo(masterCombo, numLockDigits);
    int[] masterComboOrig = {masterCombo[0], masterCombo[1], masterCombo[2]};
    int[] masterComboLimit = {nextLockDigit(masterCombo[0], 4, numLockDigits), nextLockDigit(masterCombo[1], 4, numLockDigits), 
      nextLockDigit(masterCombo[2], 4, numLockDigits)};
    int numCombos = numLockDigits < 5 ? numLockDigits*numLockDigits*numLockDigits : 125;
    
    for (int i = 0; i < 125; i++) {
      if (!intersects(masterCombo, johnCombo, numLockDigits)) {
        numCombos++;
      }
      nextCombo(masterCombo, masterComboLimit, masterComboOrig, numLockDigits);
    }
    
    System.out.println(numCombos);
    out.println(numCombos);
    out.close();
    System.exit(0);
  }
  
  private static int nextLockDigit(int digit, int amount, int numDigits) {
    return (digit + amount > numDigits ? (digit + amount) - numDigits : digit + amount);
  }
  
  private static void resetCombo(int[] combo, int numDigits) {
    combo[0] = (combo[0] - 2 < 1 ? numDigits + (combo[0] - 2) : combo[0] - 2);
    combo[1] = (combo[1] - 2 < 1 ? numDigits + (combo[1] - 2) : combo[1] - 2);
    combo[2] = (combo[2] - 2 < 1 ? numDigits + (combo[2] - 2) : combo[2] - 2);
  }
  
  private static void nextCombo(int[] combo, int[] comboLimit, int[] comboOrig, int numDigits) {
    for (int i = 0; i < combo.length; i++) {
      int nextDigit = nextLockDigit(combo[i], 1, numDigits);
      if (nextDigit == nextLockDigit(comboLimit[i], 1, numDigits)) {
        combo[i] = comboOrig[i];
      } else {
        combo[i] = nextDigit;
        break;
      }
    }
  }
  
  public static boolean intersects(int[] combo, int[] compareCombo, int numDigits) {
    for (int i = 0; i < combo.length; i++) {
      if (combo[i] != compareCombo[i] && combo[i] != nextLockDigit(compareCombo[i], 1, numDigits) && 
          combo[i] != nextLockDigit(compareCombo[i], 2, numDigits) && combo[i] != nextLockDigit(compareCombo[i], 3, numDigits) && 
          combo[i] != nextLockDigit(compareCombo[i], 4, numDigits))
        return false;
    }
    return true;
  }
}