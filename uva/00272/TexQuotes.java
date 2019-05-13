import java.util.Scanner;

public class TexQuotes {
  public static void main(String[] args) throws java.lang.Exception {
    Scanner scan = new Scanner(System.in);
    
    boolean backtick = true;
    while (scan.hasNextLine()) {
      String line = scan.nextLine();
      for (int i = 0; i < line.length(); i++) {
        String currLetter = line.substring(i, i+1);
        if (currLetter.equals("\"")) {
          System.out.print(backtick ? "``" : "''");
          backtick = !backtick;
        } else {
          System.out.print(currLetter);
        }
      }
      System.out.println();
    }
  }
}