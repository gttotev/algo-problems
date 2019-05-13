import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean[] legit;
        int[] fake;
        String left, right;
        int len, weigh, fakeCoin, maxFake;
        for (int t = scan.nextInt(); t > 0; t--) {
            legit = new boolean[12];
            fake = new int[12];
            fakeCoin = -1;
            maxFake = 0;
            for (int w = 0; w < 3; w++) {
                left = scan.next();
                right = scan.next();
                len = left.length();
                weigh = scan.next().charAt(0) - 'd';
                for (int i = 0; i < len; i++) {
                    int l = left.charAt(i) - 'A';
                    int r = right.charAt(i) - 'A';
                    if (weigh == 1) {
                        legit[l] = true;
                        legit[r] = true;
                    } else {
                        int d = 2 * weigh / 17 - 1;
                        fake[l] = fake[l] + d;
                        fake[r] = fake[r] - d;
                    }
                }
            }
            for (int i = 0; i < 12; i++) {
                if (!legit[i] && Math.abs(fake[i]) > maxFake) {
                    fakeCoin = i;
                    maxFake = Math.abs(fake[i]);
                }
            }
            System.out.format("%c is the counterfeit coin and it is %s.\n", fakeCoin + 'A',
                              (fake[fakeCoin] < 0 ? "light" : "heavy"));
        }
    }
}
