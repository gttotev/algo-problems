import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixTree {
    public void printSuffixTreeEdges(String text) {
        List<Map<int[], Integer>> suffixTree = new ArrayList<Map<int[], Integer>>();
        suffixTree.add(new HashMap<int[], Integer>());
        for (int ti = 0; ti < text.length(); ti++) {
            int node = 0;
            int[] matchEdge = null;
            int edgeOffset = 0;
            for (int i = ti; i < text.length(); i++) {
                char tc = text.charAt(i);
                if (matchEdge != null && edgeOffset >= matchEdge[1]) {
                    node = suffixTree.get(node).get(matchEdge);
                    matchEdge = null;
                }
                if (matchEdge == null) {
                    for (int[] edge : suffixTree.get(node).keySet()) {
                        if (tc == text.charAt(edge[0])) {
                            matchEdge = edge;
                            edgeOffset = 1;
                            break;
                        }
                    }
                    if (matchEdge == null) {
                        suffixTree.get(node).put(new int[] {i, text.length() - i}, suffixTree.size());
                        suffixTree.add(new HashMap<int[], Integer>());
                        break;
                    }
                } else {
                    if (tc == text.charAt(matchEdge[0] + edgeOffset)) {
                        edgeOffset++;
                    } else {
                        int midNode = suffixTree.size();
                        suffixTree.get(node).put(new int[] {matchEdge[0], edgeOffset}, midNode);
                        suffixTree.add(new HashMap<int[], Integer>());
                        suffixTree.get(midNode).put(new int[] {matchEdge[0] + edgeOffset, matchEdge[1] - edgeOffset},
                                                    suffixTree.get(node).get(matchEdge));
                        suffixTree.get(midNode).put(new int[] {i, text.length() - i}, midNode + 1);
                        suffixTree.add(new HashMap<int[], Integer>());
                        suffixTree.get(node).remove(matchEdge);
                        break;
                    }
                }
            }
        }
        printste(suffixTree, text);
    }

    public static void main(String[] args) throws IOException {
        new SuffixTree().run();
    }
    
    public void printst(List<Map<int[], Integer>> stree, String text) {
        for (int n = 0; n < stree.size(); n++) {
            System.out.print("Node " + n + ":");
            for (Map.Entry<int[], Integer> edgeNode : stree.get(n).entrySet()) {
                int[] edge = edgeNode.getKey();
                System.out.print("[" + edge[0] + "," + edge[1] + "](" + text.substring(edge[0], edge[0] + edge[1]) +
                                 ")->" + edgeNode.getValue() + " ");
            }
            System.out.println();
        }
    }
    
    public void printste(List<Map<int[], Integer>> stree, String text) {
        for (int n = 0; n < stree.size(); n++) {
            for (Map.Entry<int[], Integer> edgeNode : stree.get(n).entrySet()) {
                int[] edge = edgeNode.getKey();
                System.out.println(text.substring(edge[0], edge[0] + edge[1]));
            }
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        printSuffixTreeEdges(text);
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
