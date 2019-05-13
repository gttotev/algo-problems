import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int probs = scan.nextInt();
        for (int p = 0; p < probs; p++) {
            char piece = scan.next().charAt(0);
            int rows = scan.nextInt();
            int cols = scan.nextInt();
            System.out.println(maxPieces(piece, rows, cols));
        }
    }
    private static int maxPieces(char p, int r, int c) {
        switch (p) {
            case 'r':
            case 'Q':
                return Math.min(r, c);
            case 'k':
                return (int) Math.round((double) (r * c) / 2);
            case 'K':
                return ((r + 1) / 2) * ((c + 1) / 2);
        }
        return 0;
    }
}
