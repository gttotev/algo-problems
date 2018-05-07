import java.util.*;
public class SpellingBee {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    Random randGen = new Random();
    
    System.out.println("Enter number of words:");
    int numWords = scan.nextInt();
    scan.nextLine();
    
    int missedLimit = 5;
    int numMissed = 0;
    String[] words = new String[numWords];
    String[] wordDefs = new String[numWords];
    
    for (int i = 0; i < numWords; i++) {
      System.out.println("Enter word:");
      words[i] = scan.nextLine();
      System.out.println("Enter definiton:");
      wordDefs[i] = scan.nextLine();
    }
   
    int targetI = randGen.nextInt(numWords);
    String targetWord = words[targetI];
    System.out.println("What is " + wordDefs[targetI]);
    String line = "";
    while (!((line = scan.nextLine()).equals(targetWord))) {
      System.out.println("wRONG GT REKT SKRUB!!!1!");
      if (numMissed >= missedLimit) {
        System.out.println("Hint: first letter " + targetWord.charAt(0) + " last letter " + targetWord.charAt(targetWord.length()-1));
      } else {
        numMissed++;
      }
    }
    System.out.println("Correct!");
  }
}