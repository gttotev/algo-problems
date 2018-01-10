import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] game = scan.nextLine().split(" ");
        while (game[0].charAt(0) != 'G') {
            int score = 0;
            int frame = 1;
            int f = 1;
            int bonusAdd = 0;
            int prev = 0;
            for (int i = 0; i < game.length; i++, f++) {
                int roll = game[i].charAt(0) - '0';
                boolean spare = roll == -1;
                //System.out.format("[%d](%d) %d +%d\n", frame, score, roll, bonusAdd);
                if (spare)
                    roll = 10 - prev;
                if (bonusAdd > 0) {
                    int mag = Math.max(1, bonusAdd - 1);
                    score += mag * Math.min(10, roll);
                    bonusAdd -= mag;
                }
                if (frame < 11) {
                    if (roll == 40) {
                        roll = 10;
                        bonusAdd += 2;
                        f = 2;
                    }
                    if (spare)
                        bonusAdd++;
                    score += roll;
                }
                if (f == 2) {
                    frame++;
                    f = 0;
                }
                prev = roll;
            }
            System.out.println(score);
            game = scan.nextLine().split(" ");
        }
    }
}
