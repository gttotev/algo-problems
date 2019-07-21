import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BFS {
    private static int distance(ArrayList<Integer>[] adj, int origin, int dest) {
        int[] dist = new int[adj.length];
        dist[origin] = 1;
        Queue<Integer> nodeQueue = new LinkedList<Integer>();
        Integer node = origin;
        while (node != null) {
            for (int nnode : adj[node]) {
                if (nnode == dest) {
                    return dist[node];
                }
                if (dist[nnode] == 0) {
                    nodeQueue.add(nnode);
                    dist[nnode] = dist[node] + 1;
                }
            }
            node = nodeQueue.poll();
        }
        return -1;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, x, y));
    }
}
