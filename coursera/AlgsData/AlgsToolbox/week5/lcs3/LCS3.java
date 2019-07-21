import java.util.*;

public class LCS3 {
    private static int lcs3(int[] a, int[] b, int[] c) {
        int[][][] seqTable = new int[a.length+1][b.length+1][c.length+1];
        for (int ai = 1; ai <= a.length; ai++) {
            for (int bi = 1; bi <= b.length; bi++) {
                for (int ci = 1; ci <= c.length; ci++) {
                    int maxSeq = Math.max(seqTable[ai-1][bi][ci], seqTable[ai][bi-1][ci]);
                    maxSeq = Math.max(maxSeq, seqTable[ai][bi][ci-1]);
                    maxSeq = Math.max(maxSeq, seqTable[ai-1][bi-1][ci-1] + (a[ai-1] == b[bi-1] && c[ci-1] == a[ai-1] ? 1 : 0));
                    seqTable[ai][bi][ci] = maxSeq;
                }
            }
        }
        return seqTable[a.length][b.length][c.length];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int an = scanner.nextInt();
        int[] a = new int[an];
        for (int i = 0; i < an; i++) {
            a[i] = scanner.nextInt();
        }
        int bn = scanner.nextInt();
        int[] b = new int[bn];
        for (int i = 0; i < bn; i++) {
            b[i] = scanner.nextInt();
        }
        int cn = scanner.nextInt();
        int[] c = new int[cn];
        for (int i = 0; i < cn; i++) {
            c[i] = scanner.nextInt();
        }
        System.out.println(lcs3(a, b, c));
    }
}

