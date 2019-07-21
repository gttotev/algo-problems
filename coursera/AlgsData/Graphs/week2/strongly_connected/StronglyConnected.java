import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class StronglyConnected {
    private static void exploreOrder(ArrayList<Integer>[] adj, ArrayList<Integer> order, int node) {
        adj[node].add(0, -1);
        for (int i = 1; i < adj[node].size(); i++) {
            int nnode = adj[node].get(i);
            if (adj[nnode].size() == 0 || adj[nnode].get(0) != -1) {
                exploreOrder(adj, order, nnode);
            }
        }
        if (order != null) {
            order.add(0, node);
        }
    }
    
    private static int numberOfSCCs(ArrayList<Integer>[] adj, ArrayList<Integer>[] radj) {
        int sccs = 0;
        ArrayList<Integer> order = new ArrayList<Integer>();
        for (int i = 0; i < radj.length; i++) {
            if (radj[i].size() == 0 || radj[i].get(0) != -1) {
                exploreOrder(radj, order, i);
            }
        }
        for (int node : order) {
            if (adj[node].size() == 0 || adj[node].get(0) != -1) {
                exploreOrder(adj, null, node);
                sccs++;
            }
        }
        return sccs;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] radj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            radj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            radj[y - 1].add(x - 1);
        }
        System.out.println(numberOfSCCs(adj, radj));
    }
}
