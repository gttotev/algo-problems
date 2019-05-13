import java.util.Scanner;
import java.util.PriorityQueue;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int fields, numGreen, numBlue, e;
        Integer g, b;
        PriorityQueue<Integer> gArmy = new PriorityQueue<Integer>();
        PriorityQueue<Integer> bArmy = new PriorityQueue<Integer>();
        boolean ge, be;
        int[] gLem, bLem;
        for (int t = scan.nextInt(); t > 0; t--) {
            fields = scan.nextInt();
            gLem = new int[fields];
            bLem = new int[fields];
            numGreen = scan.nextInt();
            numBlue = scan.nextInt();
            for (int i = 0; i < numGreen; i++) gArmy.add(-scan.nextInt());
            for (int i = 0; i < numBlue; i++) bArmy.add(-scan.nextInt());
            while (!(ge = gArmy.isEmpty()) && !bArmy.isEmpty()) {
                for (e = 0; e < fields; e++) {
                    g = gArmy.poll();
                    if (g == null) break;
                    b = bArmy.poll();
                    if (b == null) {
                        gArmy.add(g);
                        break;
                    }
                    g = -g;
                    g += b;
                    if (g > 0) {
                        gLem[e] = g;
                        bLem[e] = 0;
                    } else {
                        gLem[e] = 0;
                        bLem[e] = -g;
                    }
                }
                for (int i = 0; i < e; i++) {
                    if (gLem[i] > 0) gArmy.add(-gLem[i]);
                    if (bLem[i] > 0) bArmy.add(-bLem[i]);
                }
            }
            if (ge) {
                if (bArmy.isEmpty()) {
                    System.out.println("green and blue died");
                } else {
                    System.out.println("blue wins");
                    while (!bArmy.isEmpty()) System.out.println(-bArmy.poll());
                }
            } else {
                System.out.println("green wins");
                while (!gArmy.isEmpty()) System.out.println(-gArmy.poll());
            }
            if (t > 1) System.out.println();
        }
    }
}
