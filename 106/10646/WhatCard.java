import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] deck = new String[52];
        int i, y, top;
        for (int t = scan.nextInt(); t > 0; t--) {
            top = 26;
            for (int i = 0; i < 52; i++) {
                deck[i] = scan.next();
            }
            for (int i = 0; i < 3; i++) {
                String card = deck[top];
                int x = card.charAt(0) - '0';
                if (x > 9 || x < 2)
                    x = 10;
                y += x;
                top -= 10 - x;
            }
        }
    }
}
