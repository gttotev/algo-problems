import java.io.*;
import java.util.*;

public class TrieMatchingExtended implements Runnable {
    String text;
    List<Map<Character, Integer>> trie;
    
    void buildTrie(List<String> patterns) {
        trie = new ArrayList<Map<Character, Integer>>();
        trie.add(new HashMap<Character, Integer>());
        for (String pattern : patterns) {
            int trieNode = 0;
            for (int i = 0; i < pattern.length(); i++) {
                char pc = pattern.charAt(i);
                if (trie.get(trieNode).containsKey(pc)) {
                    trieNode = trie.get(trieNode).get(pc);
                } else {
                    trie.get(trieNode).put(pc, trie.size());
                    trieNode = trie.size();
                    trie.add(new HashMap<Character, Integer>());
                }
            }
        }
    }
    
    boolean trieMatch(int ti) {
        int trieNode = 0;
        for (int i = ti; i < text.length(); i++) {
            char tc = text.charAt(i);
            if (trie.get(trieNode).containsKey(tc)) {
                trieNode = trie.get(trieNode).get(tc);
            } else {
                return false;
            }
            if (trie.get(trieNode).containsKey('$')) {
                return true;
            }
        }
        return false;
    }
    
    List<Integer> solve(String text, int n, List<String> patterns) {
        this.text = text;
        buildTrie(patterns);
        List<Integer> matches = new ArrayList<Integer>();
        for (int ti = 0; ti < text.length(); ti++) {
            if (trieMatch(ti)) {
                matches.add(ti);
            }
        }
        return matches;
    }
    
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String text = in.readLine();
            int n = Integer.parseInt(in.readLine());
            List<String> patterns = new ArrayList<String>();
            for (int i = 0; i < n; i++) {
                patterns.add(in.readLine() + "$");
            }
            
            List<Integer> ans = solve(text, n, patterns);
            
            for (int j = 0; j < ans.size(); j++) {
                System.out.print("" + ans.get(j));
                System.out.print(j + 1 < ans.size() ? " " : "\n");
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public static void main(String[] args) {
        new Thread(new TrieMatchingExtended()).start();
    }
}
