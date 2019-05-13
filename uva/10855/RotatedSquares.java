import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        char[][] big, small;
        boolean fit;
        int[] rfits;
        int[] cbi = new int[2];
        int b2, s, s2, i, e, rot, rb, cb;
        int b = scan.nextInt();
        while (b > 0) {
            s = scan.nextInt();
            rfits = new int[4];
            s2 = s * s;
            big = new char[b][b];
            small = new char[s][s];
            for (i = 0; i < b; i++) {
                big[i] = scan.next().toCharArray();
            }
            b -= s - 1;
            b2 = b * b;
            for (i = 0; i < s; i++) {
                small[i] = scan.next().toCharArray();
            }
            for (i = 0; i < b2; i++) {
                rb = i / b;
                cb = i % b;
                for (rot = 0; rot < 4; rot++) {
                    fit = true;
                    for (e = 0; e < s2; e++) {
                        cbi[0] = e / s;
                        cbi[1] = e % s;
                        if (rot > 1)
                            cbi[0] = s - cbi[0] - 1;
                        if (rot > 0 && rot < 3)
                            cbi[1] = s - cbi[1] - 1;
                        if (big[e / s + rb][e % s + cb] != small[cbi[rot % 2]][cbi[(rot % 2)^1]]) {
                            fit = false;
                            break;
                        }
                    }
                    if (fit)
                        rfits[rot] = rfits[rot] + 1;
                }
            }
            System.out.format("%d %d %d %d\n", rfits[0], rfits[1], rfits[2], rfits[3]);
            b = scan.nextInt();
        }
    }
}
