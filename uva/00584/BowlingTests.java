import java.util.Random;

public class BowlingTests {
    public static void main(String[] args) {
        Random rand = new Random();
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            int roll1, roll2;
            for (int f = 1; f < 10; f++) {
                roll1 = rand.nextInt(11);
                if (roll1 == 10) {
                    System.out.print("X ");
                } else {
                    roll2 = rand.nextInt(11 - roll1);
                    System.out.format("%d %s ", roll1, (roll1 + roll2 == 10 ? "/" : roll2));
                }
            }
            roll1 = rand.nextInt(11);
            roll2 = rand.nextInt(11 - roll1);
            if (roll1 == 10) {
                roll1 = rand.nextInt(11);
                roll2 = rand.nextInt(11 - (roll1 == 10 ? 0 : roll1));
                String r2s = (roll2 == 10 ? "X" : Integer.toString(roll2));
                System.out.format("X %s %s", (roll1 == 10 ? "X" : roll1),
                                  (roll1 + roll2 == 10 ? "/" : r2s));
            } else if (roll1 + roll2 == 10) {
                roll2 = rand.nextInt(11);
                System.out.format("%d / %s", roll1, (roll2 == 10 ? "X" : roll2));
            } else {
                System.out.format("%d %d", roll1, roll2);
            }
            System.out.println();
        }
        System.out.println("Game Over");
    }
}
