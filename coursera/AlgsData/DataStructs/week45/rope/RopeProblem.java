import java.io.*;
import java.util.*;

class RopeProblem {
    static class Vertex {
        char key;
        Vertex left, right, parent;
        int size;
        Vertex(char key, Vertex left, Vertex right, Vertex parent, int size) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.size = size;
        }
        public String toString() {
            return "{" + key + "}";
        }
        public void print() {
            ArrayDeque<Vertex> queue = new ArrayDeque<Vertex>();
            queue.add(this);
            while (!queue.isEmpty()) {
                Vertex node = queue.poll();
                if (node.parent != null)
                    System.out.println("('" + node.parent.key + (node.parent.left == node ? "'>l" : "'>r") 
                                           + ")" + node.key + "{" + node.size + "}");
                else
                    System.out.println("(root)" + node.key + "{" + node.size + "}");
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
        }
    }
    
    static class VertexPair {
        Vertex left;
        Vertex right;
        VertexPair() {
        }
        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }
        public void print() {
            left.print();
            System.out.println();
            right.print();
        }
    }
    
    void update(Vertex v) {
        if (v == null) return;
        int newSize = 1;
        if (v.left != null) {
            v.left.parent = v;
            newSize += v.left.size;
        }
        if (v.right != null) {
            v.right.parent = v;
            newSize += v.right.size;
        }
        v.size = newSize;
    }
    
    void smallRotation(Vertex v) {
        Vertex parent = v.parent;
        if (parent == null) {
            return;
        }
        Vertex grandparent = v.parent.parent;
        if (parent.left == v) {
            Vertex m = v.right;
            v.right = parent;
            parent.left = m;
        } else {
            Vertex m = v.left;
            v.left = parent;
            parent.right = m;
        }
        update(parent);
        update(v);
        v.parent = grandparent;
        if (grandparent != null) {
            if (grandparent.left == parent) {
                grandparent.left = v;
            } else {
                grandparent.right = v;
            }
        }
    }
    
    void bigRotation(Vertex v) {
        if (v.parent.left == v && v.parent.parent.left == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else {
            // Zig-zag
            smallRotation(v);
            smallRotation(v);
        }
    }
    
    // Makes splay of the given vertex and returns the new root.
    Vertex splay(Vertex v) {
        if (v == null) return null;
        while (v.parent != null) {
            if (v.parent.parent == null) {
                smallRotation(v);
                break;
            }
            bigRotation(v);
        }
        return v;
    }
    
    VertexPair split(Vertex root, int index) {
        VertexPair result = new VertexPair();
        root = findSplay(root, index);
        if (root == null)
            return null;
        result.right = root;
        if (result.right == null) {
            result.left = root;
            return result;
        }
        result.right = splay(result.right);
        result.left = result.right.left;
        result.right.left = null;
        if (result.left != null) {
            result.left.parent = null;
        }
        update(result.left);
        update(result.right);
        return result;
    }
    
    Vertex merge(Vertex left, Vertex right) {
        if (left == null) return right;
        if (right == null) return left;
        while (right.left != null) {
            right = right.left;
        }
        right = splay(right);
        right.left = left;
        update(right);
        return right;
    }
    
    Vertex findSplay(Vertex tree, int index) {
        Vertex root = tree;
        boolean found = false;
        while (root != null) {
            int leftSize = 0;
            if (root.left != null) {
                leftSize = root.left.size;
            }
            if (index > leftSize) {
                index -= (root.size - (root.right != null ? root.right.size : 0));
                root = root.right;
            } else if (index < leftSize) {
                root = root.left;
            } else {
                found = true;
                break;
            }
        }
        if (found) {
            tree = splay(root);
            return tree;
        }
        return null;
    }
    
    class Rope {
        int strLength;
        Vertex ropeRoot;
        
        public Rope(String str) {
            strLength = str.length();
            ropeRoot = new Vertex(str.charAt(0), null, null, null, 1);
            for (int i = 1; i < strLength; i++) {
                Vertex add = new Vertex(str.charAt(i), ropeRoot, null, null, i+1);
                ropeRoot.parent = add;
                ropeRoot = add;
            }
        }
        
        public void process(int i, int j, int k) {
            VertexPair leftExtract = split(ropeRoot, i);
            VertexPair rightExtract = split(leftExtract.right, j-i+1);
            if (rightExtract == null) {
                rightExtract = new VertexPair(leftExtract.right, null);
            }
            Vertex insert = rightExtract.left;
            Vertex extracted = merge(leftExtract.left, rightExtract.right);
            VertexPair insertSplit = split(extracted, k);
            if (insertSplit == null) {
                insertSplit = new VertexPair(extracted, null);
            }
            insert = merge(insertSplit.left, insert);
            ropeRoot = merge(insert, insertSplit.right);
        }
        
        public char[] result() {
            char[] str = new char[strLength];
            int strI = 0;
            Stack<Vertex> stack = new Stack<Vertex>();
            Vertex node = ropeRoot;
            //first node to be visited will be the left one
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            // traverse the tree
            while (stack.size() > 0) {
                // visit the top node
                node = stack.pop();
                //
                str[strI] = node.key;
                strI++;
                if (node.right != null) {
                    node = node.right;
                    // the next node to be visited is the leftmost
                    while (node != null) {
                        stack.push(node);
                        node = node.left;
                    }
                }
            }
            return str;
        }
    }
    
    public void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        String str = in.next();
        int strLength = str.length();
        Rope rope = new Rope(str);
        if (strLength == 1) {
            out.println(str);
        } else {
            for (int q = in.nextInt(); q > 0; q--) {
                int i = in.nextInt();
                int j = in.nextInt();
                int k = in.nextInt();
                if (strLength > 1)
                    rope.process(i, j, k);
            }
            char[] strArr = rope.result();
            for (int i = 0; i < strLength; i++) {
                out.print(strArr[i]);
            }
            out.println();
        }
        out.close();
    }
    
    public static void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new RopeProblem().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
        //new RopeProblem().run();
    }
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
}
