import java.util.*;
import java.io.*;

public class tree_orders {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;
        
        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }
        
        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }
        
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
    
    public class TreeOrders {
        int n;
        int[] key, left, right;
        
        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            key = new int[n];
            left = new int[n];
            right = new int[n];
            for (int i = 0; i < n; i++) { 
                key[i] = in.nextInt();
                left[i] = in.nextInt();
                right[i] = in.nextInt();
            }
        }
        
        List<Integer> inOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>(n);
            inOrderTraversal(0, result);
            return result;
        }
        
        private void inOrderTraversal(int tree, List<Integer> nodes) {
            if (tree == -1)
                return;
            inOrderTraversal(left[tree], nodes);
            nodes.add(key[tree]);
            inOrderTraversal(right[tree], nodes);
        }
        
        List<Integer> preOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>(n);
            preOrderTraversal(0, result);
            return result;
        }
        
        private void preOrderTraversal(int tree, List<Integer> nodes) {
            if (tree == -1)
                return;
            nodes.add(key[tree]);
            preOrderTraversal(left[tree], nodes);
            preOrderTraversal(right[tree], nodes);
        }
        
        List<Integer> postOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>(n);
            postOrderTraversal(0, result);
            return result;
        }
        
        private void postOrderTraversal(int tree, List<Integer> nodes) {
            if (tree == -1)
                return;
            postOrderTraversal(left[tree], nodes);
            postOrderTraversal(right[tree], nodes);
            nodes.add(key[tree]);
        }
    }
    
    public static void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new tree_orders().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }
    
    public void print(List<Integer> x) {
        for (Integer a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }
    
    public void run() throws IOException {
        TreeOrders tree = new TreeOrders();
        tree.read();
        print(tree.inOrder());
        print(tree.preOrder());
        print(tree.postOrder());
    }
}
