import java.util.*;

public class Dijkstra {
    private static int extractMin(int[] dist, boolean[] nfinal) {
        int min = -1;
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < dist.length; i++) {
            if (dist[i] <= minDist && !nfinal[i]) {
                min = i;
                minDist = dist[i];
            }
        }
        return min;
    }
    
    private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        boolean[] nfinal = new boolean[adj.length];
        int[] dist = new int[adj.length];
        for (int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[s] = 0;
        for (int l = 0; l < adj.length; l++) {
            int node = extractMin(dist, nfinal);
            nfinal[node] = true;
            if (dist[node] == Integer.MAX_VALUE) {
                return -1;
            }
            if (node == t) {
                return dist[node];
            }
            for (int i = 0; i < adj[node].size(); i++) {
                int nnode = adj[node].get(i);
                int ncost = cost[node].get(i);
                if (dist[nnode] > dist[node] + ncost) {
                    dist[nnode] = dist[node] + ncost;
                }
            }
        }
        return -1;
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, cost, x, y));
    }
}
