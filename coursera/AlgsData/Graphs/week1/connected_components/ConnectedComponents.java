import java.util.ArrayList;
import java.util.Scanner;

public class ConnectedComponents {
    private static void explore(ArrayList<Integer>[] adj, int node) {
        adj[node].add(0, -1);
        for (int i = 1; i < adj[node].size(); i++) {
            int nnode = adj[node].get(i);
            if (adj[nnode].get(0) != -1) {
                explore(adj, nnode);
            }
        }
    }
    
    private static int numberOfComponents(ArrayList<Integer>[] adj) {
        int components = 0;
        for (int i = 0; i < adj.length; i++) {
            if (adj[i].size() == 0 || adj[i].get(0) != -1) {
                explore(adj, i);
                components++;
            }
        }
        return components;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
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
        System.out.println(numberOfComponents(adj));
    }
}

