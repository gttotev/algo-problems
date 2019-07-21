import java.util.*;
import java.io.*;

public class tree_height {
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
    
    class TreeNode {
        public int num;
        public TreeNode parent;
        public ArrayList<TreeNode> children;
        
        public TreeNode(int num) {
            this.num = num;
            children = new ArrayList<TreeNode>();
        }
        public void setParent(TreeNode parent) {
            this.parent = parent;
        }
        public void addChild(TreeNode child) {
            children.add(child);
        }
        public boolean empty() {
            return children.size() == 0;
        }
        public String getStr() {
            String childStr = "";
            for (int i = 0; i < children.size(); i++) {
                childStr += children.get(i).num + ",";
            }
            return "Node " + num + ", parent " + (parent == null ? "<root>" : parent.num) + "\nChildren: " + childStr;
        }
    }
    
    class TreeHeight {
        int n;
        TreeNode[] nodes;
        TreeNode root;
        
        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            nodes = new TreeNode[n];
            for (int i = 0; i < n; i++) {
                int parent = in.nextInt();
                if (nodes[i] == null) {
                    nodes[i] = new TreeNode(i);
                }
                TreeNode thisNode = nodes[i];
                if (parent == -1) {
                    root = thisNode;
                } else {
                    if (nodes[parent] == null) {
                        nodes[parent] = new TreeNode(parent);
                    }
                    thisNode.setParent(nodes[parent]);
                    nodes[parent].addChild(thisNode);
                }
            }
        }
        
        int findHeight(TreeNode tree) {
            if (tree.empty())
                return 0;
            int max = 0;
            ArrayList<TreeNode> children = tree.children;
            for (int i = 0; i < children.size(); i++) {
                int childHeight = findHeight(children.get(i));
                if (childHeight > max)
                    max = childHeight;
            }
            return 1 + max;
        }
        
        int computeHeight() {
            // Replace this code with a faster implementation
            /*int maxHeight = 0;
            for (int vertex = 0; vertex < n; vertex++) {
                int height = 0;
                for (int i = vertex; i != -1; i = parent[i])
                    height++;
                maxHeight = Math.max(maxHeight, height);
            }
            return maxHeight;*/
            return findHeight(root) + 1;
        }
    }
    
    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new tree_height().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }
    public void run() throws IOException {
        TreeHeight tree = new TreeHeight();
        tree.read();
        System.out.println(tree.computeHeight());
    }
}
