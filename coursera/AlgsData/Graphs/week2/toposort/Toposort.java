import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Toposort {
    private static void exploreOrder(ArrayList<Integer>[] adj, ArrayList<Integer> order, int node) {
        adj[node].add(0, -1);
        for (int i = 1; i < adj[node].size(); i++) {
            int nnode = adj[node].get(i);
            if (adj[nnode].size() == 0 || adj[nnode].get(0) != -1) {
                exploreOrder(adj, order, nnode);
            }
        }
        order.add(0, node);
    }
    
    private static ArrayList<Integer> toposort(ArrayList<Integer>[] adj) {
        ArrayList<Integer> order = new ArrayList<Integer>();
        for (int i = 0; i < adj.length; i++) {
            if (adj[i].size() == 0 || adj[i].get(0) != -1) {
                exploreOrder(adj, order, i);
            }
        }
        return order;
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
        }
        ArrayList<Integer> order = toposort(adj);
        for (int x : order) {
            System.out.print((x + 1) + " ");
        }
    }
}
