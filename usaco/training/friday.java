/*
ID: pileto1
LANG: JAVA
TASK: friday
*/
import java.io.*;
import java.util.*;

class friday {
  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("friday.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("friday.out")));
    
    int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    
    int numYears = Integer.parseInt(f.readLine());
    int day = 2;
    
    int[] fridayDays = new int[7];
    
    for (int year = 1900; year <= 1900 + numYears - 1; year++) {
      if ((year % 100 == 0 && year % 400 == 0) || (year % 100 != 0 && year % 4 == 0))
        months[1] = 29;
      else
        months[1] = 28;
      for (int mon = 0; mon < 12; mon++) {
        day = calcDays(day, 5);
        fridayDays[day]++;
        day = calcDays(day, months[mon] - 26);
      }
    }
    
    for (int i = 0; i < 7; i++) {
      out.print(fridayDays[i] + (i == 6 ? "" : " "));
    }
    out.println();
    out.close();
    System.exit(0);
  }
  
  public static int calcDays(int day, int add) {
    day += add;
    day = day % 7;
    return day;
  }
}