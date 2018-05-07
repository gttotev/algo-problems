/*
ID: pileto1
LANG: JAVA
TASK: transform
*/
import java.io.*;
import java.util.*;

class transform {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("transform.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("transform.out")));
    
    int sqSize = Integer.parseInt(f.readLine());
    char[][] startSq = new char[sqSize][sqSize];
    char[][] resultSq = new char[sqSize][sqSize];
    
    for (int i = 0; i < sqSize; i++) {
      String in = f.readLine();
      for (int e = 0; e < sqSize; e++) {
        startSq[i][e] = in.charAt(e);
      }
    }
    for (int i = 0; i < sqSize; i++) {
      String in = f.readLine();
      for (int e = 0; e < sqSize; e++) {
        resultSq[i][e] = in.charAt(e);
      }
    }
    
    char[][] tempSq = new char[sqSize][sqSize]; //TEST IT!!!!!!
    if (sqEquals(tempSq = rotate90(startSq), resultSq)) {
      out.println(1);
    } else if (sqEquals(tempSq = rotate90(tempSq), resultSq)) {
      out.println(2);
    } else if (sqEquals(tempSq = rotate90(tempSq), resultSq)) {
      out.println(3);
    } else if (sqEquals(tempSq = reflect(startSq), resultSq)) {
      out.println(4);
    } else if (sqEquals(tempSq = rotate90(tempSq), resultSq) || 
               sqEquals(tempSq = rotate90(tempSq), resultSq) || 
               sqEquals(rotate90(tempSq), resultSq)) {
      out.println(5);
    } else if (sqEquals(startSq, resultSq)) {
      out.println(6);
    } else {
      out.println(7);
    }
    //out.println();
    
    out.close();
    System.exit(0);
  }
  
  private static boolean sqEquals(char[][] sq1, char[][] sq2) {
    for (int i = 0; i < sq1.length; i++) {
      for (int e = 0; e < sq1[i].length; e++) {
        if (sq1[i][e] != sq2[i][e])
          return false;
      }
    }
    return true;
  }
  
  private static char[][] rotate90(char[][] sq) {
    int sqSize = sq.length;
    char[][] rotated = new char[sqSize][sqSize];
    for (int startI = 0; startI < sqSize / 2; startI++) {
      int endI = sqSize - 1 - startI;
      for (int side = 0; side < 4; side++) {
        for (int i = startI; i <= endI; i++) {
          switch (side) {
            case 0:
              rotated[i][endI] = sq[startI][i];
              break;
            case 1:
              rotated[endI][/*endI - i*/sqSize - 1 - i] = sq[i][endI];
              break;
            case 2:
              rotated[i][startI] = sq[endI][i];
              break;
            case 3:
              rotated[startI][/*endI - i*/sqSize - 1 - i] = sq[i][startI];
          }
        }
      }
    }
    rotated[sqSize/2][sqSize/2] = (sqSize % 2 == 0 ? rotated[sqSize/2][sqSize/2] : sq[sqSize/2][sqSize/2]);
    return rotated;
  }
  
  private static char[][] reflect(char[][] sq) {
    int sqSize = sq.length;
    char[][] reflected = new char[sqSize][sqSize];
    for (int i = 0; i < sqSize; i++) {
      for (int e = 0; e < sqSize/2; e++) {
        reflected[i][e] = sq[i][sqSize - 1 - e];
        reflected[i][sqSize - 1 - e] = sq[i][e];
      }
    }
    if (sqSize % 2 != 0) {
      for (int i = 0; i < sqSize; i++) {
        reflected[i][sqSize/2] = sq[i][sqSize/2];
      }
    }
    return reflected;
  }
}