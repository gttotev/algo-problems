import java.io.*;
import java.math.*;
import java.util.*;

public class NonSharedSubstring implements Runnable {
    String text;
    int textOneLen;
    List<Map<int[], Integer>> suffixTree;

    void createSuffixTree() {
        suffixTree = new ArrayList<Map<int[], Integer>>();
        suffixTree.add(new HashMap<int[], Integer>());
        for (int ti = 0; ti < text.length()-1; ti++) {
            int node = 0;
            int[] matchEdge = null;
            int edgeOffset = 0;
            if (ti == textOneLen) {
                continue;
            }
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
    }
    
    String shortUniqueStr(String currStr, String upCurrStr, String upStr, int node) {
        boolean invalidNode = false;
        List<String> saveStrings = new ArrayList<String>();
        for (Map.Entry<int[], Integer> edgeNode : suffixTree.get(node).entrySet()) {
            int[] edge = edgeNode.getKey();
            char edgeStart = text.charAt(edge[0]);
            if (edgeStart == '#') {
                // Do nothing
            } else if (edgeStart == '$') {
                invalidNode = true;
            } else if (suffixTree.get(edgeNode.getValue()).isEmpty()) {
                if (edge[0] < textOneLen) { // endMark == '#'
                    saveStrings.add(currStr + text.charAt(edge[0]));
                } else {
                    invalidNode = true;
                }
            } else {
                String edgeStr = text.substring(edge[0], edge[0] + edge[1]);
                String shortStr = shortUniqueStr(currStr + edgeStr, currStr, edgeStr, edgeNode.getValue());
                if (shortStr.charAt(0) == '$') {
                    invalidNode = true;
                }
                saveStrings.add(shortStr.length() == 1 ? null : shortStr.substring(1));
            }
        }
        if (invalidNode) {
            String shortest = "";
            int minLen = textOneLen + 1;
            for (String str : saveStrings) {
                if (str != null && str.length() < minLen) {
                    shortest = str;
                    minLen = str.length();
                }
            }
            return "$" + shortest;
        }
        return "#" + upCurrStr + upStr.charAt(0);
    }
    
    String solve(String text1, String text2) {
        text = text1 + "#" + text2 + "$";
        textOneLen = text1.length();
        createSuffixTree();
        //printst();
        return shortUniqueStr("", "", "", 0).substring(1);
    }
    
    public void printst() {
        for (int n = 0; n < suffixTree.size(); n++) {
            System.out.print("Node " + n + ":");
            for (Map.Entry<int[], Integer> edgeNode : suffixTree.get(n).entrySet()) {
                int[] edge = edgeNode.getKey();
                System.out.print("[" + edge[0] + "," + edge[1] + "](" + text.substring(edge[0], edge[0] + edge[1]) +
                                 ")->" + edgeNode.getValue() + " ");
            }
            System.out.println();
        }
    }
    
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String p = in.readLine();
            String q = in.readLine();
            
            String ans = solve(p, q);
            
            System.out.println(ans);
        }
        catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public static void main(String[] args) {
        new Thread(new NonSharedSubstring()).start();
    }
}
