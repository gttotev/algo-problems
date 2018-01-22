import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int tests = scan.nextInt();
        String[] deck = new String[52];
        int i, x, y, top;
        for (int t = 1; t <= tests; t++) {
            top = 26;
            y = -1;
            for (i = 0; i < 52; i++) {
                deck[i] = scan.next();
            }
            for (i = 0; i < 3; i++) {
                String card = deck[top];
                x = Math.min(10, card.charAt(0) - '0');
                y += x;
                top -= 11 - x;
            }
            y -= top;
            y += (y > 0 ? 26 : top);
            System.out.format("Case %d: %s\n", t, deck[y]);
        }
    }
}
