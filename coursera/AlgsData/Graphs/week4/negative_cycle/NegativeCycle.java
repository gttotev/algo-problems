import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NegativeCycle {
    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] rate) {
        int edgeRelaxed = 0;
        Integer[] dist;
        for (int s = 0; s < adj.length; s++) {
            dist = new Integer[adj.length];
            dist[s] = 0;
            for (int v = 0; v < adj.length; v++) {
                edgeRelaxed = 0;
                for (int node = 0; node < adj.length; node++) {
                    for (int i = 0; i < adj[node].size(); i++) {
                        int nnode = adj[node].get(i);
                        int nrate = rate[node].get(i);
                        if (dist[node] != null && (dist[nnode] == null || dist[nnode] > dist[node] + nrate)) {
                            edgeRelaxed = 1;
                            dist[nnode] = dist[node] + nrate;
                        }
                    }
                }
                if (edgeRelaxed == 0) break;
            }
            if (edgeRelaxed == 1) return 1;
        }
        return 0;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        System.out.println(negativeCycle(adj, cost));
    }
}
