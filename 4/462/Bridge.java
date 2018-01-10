import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        char[] suits = {'S', 'H', 'D', 'C'};
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            int points = 0;
            int tpoints = 0;
            int maxSuit = 0;
            int stopped = 0;
            boolean[][] suitCards = new boolean[4][13];
            int[] suitCounts = new int[4];
            for (int i = 0; i < 13; i++) {
                String card = scan.next();
                int r = getRank(card.charAt(0));
                int s = getSuit(card.charAt(1));
                if (r > 10)
                    points += r - 10;
                suitCards[s][r - 2] = true;
                suitCounts[s] = suitCounts[s] + 1;
            }
            tpoints = points;
            for (int s = 0; s < 4; s++) {
                boolean king = suitCards[s][11];
                boolean queen = suitCards[s][10];
                int count = suitCounts[s];
                int sub = (king && count == 1 ? 1 : 0) + (queen && count < 3 ? 1 : 0) +
                    (suitCards[s][9] && count < 4 ? 1 : 0);
                if (count > suitCounts[maxSuit])
                    maxSuit = s;
                points -= sub;
                tpoints -= sub;
                if (suitCards[s][12] || (king && count > 1) || (queen && count > 2))
                    stopped++;
                if (count < 3)
                    points += (count == 2 ? 1 : 2);
            }
            if (points < 14) {
                System.out.println("PASS");
            } else if (tpoints >= 16 && stopped == 4) {
                System.out.println("BID NO-TRUMP");
            } else {
                System.out.println("BID " + suits[maxSuit]);
            }
        }
    }
    
    private static int getRank(char c) {
        if (c < 'A') return c - '0';
        switch (c) {
            case 'A':
                return 14;
            case 'T':
                return 10;
            case 'J':
                return 11;
            case 'Q':
                return 12;
            case 'K':
                return 13;
        }
        return 0;
    }
    
    private static int getSuit(char c) {
        switch (c) {
            case 'S':
                return 0;
            case 'H':
                return 1;
            case 'D':
                return 2;
            case 'C':
                return 3;
        }
        return 0;
    }
}
