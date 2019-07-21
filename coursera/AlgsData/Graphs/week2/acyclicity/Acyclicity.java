import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Acyclicity {
    private static boolean hasCycle(List<Integer>[] adj, int node, List<Integer> path) {
        adj[node].add(0, -1);
        path.add(node);
        for (int i = 1; i < adj[node].size(); i++) {
            int nnode = adj[node].get(i);
            if (path.contains(nnode) || ((adj[nnode].size() == 0 || adj[nnode].get(0) != -1) 
                                             && hasCycle(adj, nnode, path))) {
                return true;
            }
        }
        path.remove(path.size()-1);
        return false;
    }
    
    private static int cyclic(List<Integer>[] adj, int n) {
        for (int i = 0; i < n; i++) {
            if ((adj[i].size() == 0 || adj[i].get(0) != -1) && hasCycle(adj, i, new ArrayList<Integer>())) {
                return 1;
            }
        }
        return 0;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        @SuppressWarnings("unchecked")
        List<Integer>[] adj = (List<Integer>[]) new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
        }
        System.out.println(cyclic(adj, n));
    }
}
