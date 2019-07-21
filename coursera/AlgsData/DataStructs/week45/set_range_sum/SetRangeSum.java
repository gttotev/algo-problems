import java.io.*;
import java.util.*;

public class SetRangeSum {
    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;

    // Splay tree implementation

    // Vertex of a splay tree
    class Vertex {
        int key;
        // Sum of all the keys in the subtree - remember to update
        // it after each operation that changes the tree.
        long sum;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(int key, long sum, Vertex left, Vertex right, Vertex parent) {
            this.key = key;
            this.sum = sum;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
        public String toString() {
            return "----------------------\nPar: " + (parent == null ? null : parent.key) + "\nNode " + key + "\nLeft: " + 
                (left == null ? left : left.key) + ", Right: " + (right == null ? null : right.key) + "\n----------------------";
        }
    }

    void update(Vertex v) {
        if (v == null) return;
        v.sum = v.key + (v.left != null ? v.left.sum : 0) + (v.right != null ? v.right.sum : 0);
        if (v.left != null) {
            v.left.parent = v;
        }
        if (v.right != null) {
            v.right.parent = v;
        }
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

    class VertexPair {
        Vertex left;
        Vertex right;
        VertexPair() {
        }
        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }
    }

    // Searches for the given key in the tree with the given root
    // and calls splay for the deepest visited node after that.
    // Returns pair of the result and the new root.
    // If found, result is a pointer to the node with the given key.
    // Otherwise, result is a pointer to the node with the smallest
    // bigger key (next value in the order).
    // If the key is bigger than all keys in the tree,
    // then result is null.
    VertexPair find(Vertex root, int key) {
        Vertex v = root;
        Vertex last = root;
        Vertex next = null;
        //System.out.println(key);
        while (v != null) {
            if (v.key >= key && (next == null || v.key < next.key)) {
                next = v;
            }
            last = v;
            if (v.key == key) {
                break;
            }
            if (v.key < key) {
                v = v.right;
            } else {
                v = v.left;
            }
        }
        root = splay(last);
        return new VertexPair(next, root);
    }

    VertexPair split(Vertex root, int key) {
        VertexPair result = new VertexPair();
        VertexPair findAndRoot = find(root, key);
        root = findAndRoot.right;
        result.right = findAndRoot.left;
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

    // Code that uses splay tree to solve the problem

    Vertex root = null;

    void insert(int x) {
        Vertex left = null;
        Vertex right = null;
        Vertex new_vertex = null;
        VertexPair leftRight = split(root, x);
        left = leftRight.left;
        right = leftRight.right;
        if (right == null || right.key != x) {
            new_vertex = new Vertex(x, x, null, null, null);
        }
        root = merge(merge(left, new_vertex), right);
    }

    void erase(int x) {
        VertexPair findTarget = find(root, x);
        Vertex target = findTarget.left;
        root = findTarget.right;
        //System.out.println(root);
        //System.out.println(target);
        if (target == null || target.key != x)
            return;
        if (target.right == null) {
            root = target.left;
            if (root != null)
                root.parent = null;
        } else if (target.left == null) {
            root = target.right;
            root.parent = null;
        } else {
            root.left.parent = null;
            root.right.parent = null;
            root = merge(root.left, root.right); // Was right, left
        }
        //System.out.println(root);
    }

    boolean find(int x) {
        VertexPair findResult = find(root, x);
        root = findResult.right;
        //System.out.println(root);
        //System.out.println(findResult.left);
        //System.out.println(findResult.left != null && findResult.left.key == x);
        return (findResult.left != null && findResult.left.key == x);
    }

    long sum(int from, int to) {
        VertexPair leftMiddle = split(root, from);
        Vertex left = leftMiddle.left;
        Vertex middle = leftMiddle.right;
        VertexPair middleRight = split(middle, to + 1);
        middle = middleRight.left;
        Vertex right = middleRight.right;
        //long sum = sumTraversal(middle);
        long sum = (middle == null ? 0 : middle.sum);
        //System.out.println(sum);
        root = merge(merge(left, middle), right);
        //System.out.println(root);
        return sum;
    }
    
    private long sumTraversal(Vertex tree) {
        if (tree == null)
            return 0;
        long sum = tree.key;
        sum += sumTraversal(tree.left);
        sum += sumTraversal(tree.right);
        return sum;
    }

    public static final int MODULO = 1000000001;

    void solve() throws IOException {
        int n = nextInt();
        int lastSum = 0;
        for (int i = 0; i < n; i++) {
            char type = nextChar();
            switch (type) {
                case '+' : {
                    int x = nextInt();
                    insert((x + lastSum) % MODULO);
                } break;
                case '-' : {
                    int x = nextInt();
                    erase((x + lastSum) % MODULO);
                } break;
                case '?' : {
                    int x = nextInt();
                    out.println(find((x + lastSum) % MODULO) ? "Found" : "Not found");
                } break;
                case 's' : {
                    int l = nextInt();
                    int r = nextInt();
                    long res = sum((l + lastSum) % MODULO, (r + lastSum) % MODULO);
                    out.println(res);
                    lastSum = (int)(res % MODULO);
                } break;
                case 'j' : {
                    System.out.println(root);
                } break;
                case 'r' : {
                    printTree(root);
                }
            }
        }
    }
    
    void printTree(Vertex tree) {
        ArrayDeque<Vertex> queue = new ArrayDeque<Vertex>();
        queue.add(tree);
        while (!queue.isEmpty()) {
           Vertex node = queue.poll();
           if (node.parent != null)
               System.out.println("(" + node.parent.key + (node.parent.left == node ? "l" : "r") + ")" + node.key);
           else
               System.out.println("(root)" + node.key);
           if (node.left != null)
               queue.add(node.left);
           if (node.right != null)
               queue.add(node.right);
        }
    }

    SetRangeSum() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new SetRangeSum();
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
    char nextChar() throws IOException {
        return nextToken().charAt(0);
    }
}
