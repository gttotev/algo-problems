import java.util.*;

public class ShortestPaths {
    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] rate, int s, Long[] dist, byte[] special) {
        List<Integer> lastRelaxNodes = new ArrayList<Integer>();
        boolean noRelax;
        dist[s] = 0L;
        special[s] = 1;
        for (int v = 0; v < adj.length; v++) {
            noRelax = true;
            for (int node = 0; node < adj.length; node++) {
                if (dist[node] == null) continue;
                for (int i = 0; i < adj[node].size(); i++) {
                    int nnode = adj[node].get(i);
                    int nrate = rate[node].get(i);
                    if (dist[nnode] == null || dist[nnode] > dist[node] + nrate) {
                        noRelax = false;
                        special[nnode] = 1;
                        dist[nnode] = dist[node] + nrate;
                        if (v == adj.length - 1) {
                            lastRelaxNodes.add(nnode);
                        }
                    }
                }
            }
            if (noRelax) return;
        }
        for (int node : lastRelaxNodes) {
            if (special[node] != -1) {
                dfsExplore(adj, special, node);
            }
        }
    }
    
    private static void dfsExplore(ArrayList<Integer>[] adj, byte[] special, int node) {
        special[node] = -1;
        for (int i = 0; i < adj[node].size(); i++) {
            int nnode = adj[node].get(i);
            if (special[nnode] != -1) {
                dfsExplore(adj, special, nnode);
            }
        }
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
        int s = scanner.nextInt() - 1;
        Long[] distance = new Long[n];
        byte[] special = new byte[n];
        shortestPaths(adj, cost, s, distance, special);
        for (int i = 0; i < n; i++) {
            if (special[i] == 0) {
                System.out.println('*');
            } else if (special[i] == -1) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
    }
}
