import java.io.BufferedReader;
import java.io.PrintWriter;

public class SnowBoots {
    static boolean[][] visited;
    static int n, b, minBoots = 999;
    static int[] tiles, bootDepth, bootStep;
    
    public static void main(String[] args) throws java.io.IOException {
        BufferedReader in = new BufferedReader(new java.io.FileReader("snowboots.in"));
        PrintWriter out = new PrintWriter(new java.io.File("snowboots.out"));
        int i;
        String[] input = in.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        tiles = new int[n];
        b = Integer.parseInt(input[1]);
        bootDepth = new int[b];
        bootStep = new int[b];
        visited = new boolean[n][b];
        //int discards = 0;
        input = in.readLine().split(" ");
        for (i = 0; i < n; i++) {
            tiles[i] = Integer.parseInt(input[i]);
        }
        for (i = 0; i < b; i++) {
            input = in.readLine().split(" ");
            bootDepth[i] = Integer.parseInt(input[0]);
            bootStep[i] = Integer.parseInt(input[1]);
        }
        visit(0, 0);
        out.println(minBoots);
        in.close();
        out.close();
    }
    
    static void visit(int tl, int bt) {
        if (visited[tl][bt])
            return;
        visited[tl][bt] = true;
        if (tl == n - 1) {
            minBoots = Math.min(bt, minBoots);
            return;
        }
        for (int t = tl + 1; t < n && t <= tl + bootStep[bt]; t++) {
            if (bootDepth[bt] >= tiles[t]) visit(t, bt);
        }
        for (int i = bt + 1; i < b; i++) {
            if (bootDepth[i] >= tiles[tl]) visit(tl, i);
        }
    }
}
