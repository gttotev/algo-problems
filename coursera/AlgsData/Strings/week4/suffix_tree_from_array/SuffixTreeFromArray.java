import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SuffixTreeFromArray {
    private class Node {
        int parentNode;
        int parentEdgePos;
        int edgeCount;
        Edge[] edges;
        
        public Node(int parentNode, int parentEdgePos) {
            this.parentNode = parentNode;
            this.parentEdgePos = parentEdgePos;
            this.edgeCount = 0;
            this.edges = new Edge[5];
        }
        
        public void addEdge(int pos, Edge e) {
            edges[pos] = e;
            edgeCount++;
        }
        
        public void compressEdges() {
            int npos = 0;
            for (int i = 0; i < 5; i++) {
                if (edges[i] != null) {
                    if (i != npos) {
                        edges[npos] = edges[i];
                        edges[i] = null;
                    }
                    npos++;
                }
            }
        }
    }
    
    private class Edge {
        int node;
        int start;
        int end;
        
        public Edge(int node, int start, int end) {
            this.node = node;
            this.start = start;
            this.end = end;
        }
        
        public int length() {
            return end - start;
        }
        
        @Override
        public String toString() {
            return start + " " + end;
        }
    }
    
    List<Node> suffixTreeFromSuffixArray(int[] suffixArray, int[] lcp, final String text) {
        List<Node> tree = new ArrayList<Node>();
        int nnid = 2;
        int curNode = 1;
        int oldNode = 1;
        int depth = 1;
        tree.add(new Node(-1, -1));
        tree.get(0).addEdge(0, new Edge(1, suffixArray[0], suffixArray[0] + 1));
        tree.add(new Node(0, 0));
        for (int i = 0; i < lcp.length; i++) {
            int curLcp = lcp[i];
            while (depth > curLcp) {
                Node curN = tree.get(curNode);
                depth -= tree.get(curN.parentNode).edges[curN.parentEdgePos].length();
                oldNode = curNode;
                curNode = curN.parentNode;
            }
            if (depth == curLcp) {
                int start = suffixArray[i + 1] + curLcp;
                int cPos = num(text.charAt(start));
                tree.get(curNode).addEdge(cPos, new Edge(nnid, start, text.length()));
                tree.add(new Node(curNode, cPos));
                depth += text.length() - start;
            } else {
                int comLen = curLcp - depth;
                int edgePos = tree.get(oldNode).parentEdgePos;
                Edge breakEdge = tree.get(curNode).edges[edgePos];
                int breakPos = breakEdge.start + comLen;
                int cPos = num(text.charAt(breakPos));
                tree.get(curNode).edges[edgePos] = new Edge(nnid, breakEdge.start, breakPos);
                tree.add(new Node(curNode, edgePos));
                curNode = nnid;
                nnid++;
                depth += breakPos - breakEdge.start;
                tree.get(curNode).addEdge(cPos, new Edge(oldNode, breakPos, breakEdge.end));
                tree.get(oldNode).parentNode = nnid;
                tree.get(oldNode).parentEdgePos = cPos;
                breakPos = suffixArray[i + 1] + curLcp;
                cPos = num(text.charAt(breakPos));
                tree.get(curNode).addEdge(cPos, new Edge(nnid, breakPos, text.length()));
                tree.add(new Node(curNode, cPos));
                depth += text.length() - breakPos;
            }
            curNode = nnid;
            nnid++;
        }
        return tree;
    }
    
    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffixArray = new int[text.length()];
        for (int i = 0; i < suffixArray.length; ++i) {
            suffixArray[i] = scanner.nextInt();
        }
        int[] lcpArray = new int[text.length() - 1];
        for (int i = 0; i + 1 < text.length(); ++i) {
            lcpArray[i] = scanner.nextInt();
        }
        List<Node> suffixTree = suffixTreeFromSuffixArray(suffixArray, lcpArray, text);
        ArrayList<String> result = new ArrayList<String>();
        int[] nodeStack = new int[text.length()];
        int[] edgeIndexStack = new int[text.length()];
        nodeStack[0] = 0;
        edgeIndexStack[0] = 0;
        int stackSize = 1;
        while (stackSize > 0) {
            int node = nodeStack[stackSize - 1];
            int edgeIndex = edgeIndexStack[stackSize - 1];
            Node nd = suffixTree.get(node);
            Edge edge;
            stackSize -= 1;
            if (nd.edgeCount == 0) {
                continue;
            }
            if (edgeIndex + 1 < nd.edgeCount) {
                nodeStack[stackSize] = node;
                edgeIndexStack[stackSize] = edgeIndex + 1;
                stackSize += 1;
            }
            nd.compressEdges();
            edge = nd.edges[edgeIndex];
            result.add(edge.toString());
            nodeStack[stackSize] = edge.node;
            edgeIndexStack[stackSize] = 0;
            stackSize += 1;
        }
        System.out.println(text);
        print(result);
    }
    
    private int num(char b) {
        switch (b) {
            case '$':
                return 0;
            case 'A':
                return 1;
            case 'C':
                return 2;
            case 'G':
                return 3;
            case 'T':
                return 4;
        }
        return -1;
    }
    
    public void print(ArrayList<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }
    
    static public void main(String[] args) throws IOException {
        new SuffixTreeFromArray().run();
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
