import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class MultiMoo {
    static int n;
    static int[][] grid;
    static boolean[][] added;
    static boolean[][] visited;
    static ArrayList<Integer> tovisit;
    static HashMap<Integer, ArrayList<Integer>> regions = new HashMap<>();
    static HashMap<Integer, boolean[][]> regvisits = new HashMap<>();
    //static HashMap<Integer, HashSet<Integer>> regcontacts = new HashMap<>();
    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner("multimoo.in");
        PrintWriter out = new PrintWriter(new java.io.File("multimoo.out"));
        Object[] idSet;
        ArrayList<Integer> rlist;
        int i, j, e, id, regsize, rlen;
        int maxreg = 0;
        n = in.nextInt();
        grid = new int[n][n];
        boolean[][] firstvisits = new boolean[n][n];
        added = new boolean[n][n];
        tovisit = new ArrayList<>();
        //regions.put(-1, 0);
        for (i = 0; i < n; i++) for (j = 0; j < n; j++) grid[i][j] = in.nextInt();
        id = grid[0][0];
        rlist = new ArrayList<>();
        rlist.add(0);
        rlist.add(0);
        regsize = visit(id, 0, 0, firstvisits);
        rlist.add(regsize);
        regions.put(id, rlist);
        maxreg = regsize;
        while (!tovisit.isEmpty()) {
            j = tovisit.remove(tovisit.size()-1);
            i = tovisit.remove(tovisit.size()-1);
            //System.out.println(i + " " + j);
            id = grid[i][j];
            if (!regions.containsKey(id)) {
                regions.put(id, new ArrayList<>());
                //regvisits.put(id, new boolean[n][n]);
                //regcontacts.put(id, new HashSet<Integer>());
            }
            rlist = regions.get(id);
            rlist.add(i);
            rlist.add(j);
            regsize = visit(id, i, j, firstvisits);
            rlist.add(regsize);
            maxreg = Math.max(maxreg, regsize);
        }
        /*for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                id = grid[i][j];
                if (!regions.containsKey(id)) {
                    regions.put(id, new ArrayList<>());
                    regvisits.put(id, new boolean[n][n]);
                    //regcontacts.put(id, new HashSet<Integer>());
                }
                if (!regvisits.get(id)[i][j]) {
                    rlist = regions.get(id);
                    rlist.add(i);
                    rlist.add(j);
                    regsize = visit(id, id, i, j, regvisits.get(id));
                    rlist.add(regsize);
                    maxreg = Math.max(maxreg, regsize);
                }
            }
        }*/
        //System.out.println(maxreg);
        //System.out.println(regions);
        out.println(maxreg);
        idSet = regions.keySet().toArray();
        for (i = 0; i < idSet.length - 1; i++) {
            id = (Integer) idSet[i];
            rlist = regions.get(id);
            rlen = rlist.size();
            for (j = i + 1; j < idSet.length; j++) {
                /*for (e = 0; e < rlen; e += 3) {
                    regsize = visit(id, (Integer) idSet[j], rlist.get(e),
                                    rlist.get(e + 1), new boolean[n][n]);
                    maxreg = Math.max(maxreg, regsize);
                }*/
                regsize = visit(id, (Integer) idSet[j], rlist.get(0),
                                rlist.get(1), new boolean[n][n]);
                maxreg = Math.max(maxreg, regsize);
            }
        }
        out.println(maxreg);
        in.close();
        out.close();
    }
    
    private static int visit(int id1, int id2, int i, int j, boolean[][] visited) {
        if (i < 0 || i >= n || j < 0 || j >= n || visited[i][j])
            return 0;
        visited[i][j] = true;
        int id = grid[i][j];
        if (id1 != id && id2 != id) {
            //regcontacts.get(id).add(grid[i][j]);
            //tovisit.add(i);
            //tovisit.add(j);
            return 0;
        }
        return 1 + visit(id1, id2, i, j + 1, visited) + visit(id1, id2, i, j - 1, visited) + 
            visit(id1, id2, i + 1, j, visited) + visit(id1, id2, i - 1, j, visited);
    }
    
    private static int visit(int id1, int i, int j, boolean[][] visited) {
        if (i < 0 || i >= n || j < 0 || j >= n || visited[i][j])
            return 0;
        int id = grid[i][j];
        if (id1 != id) {
            //regcontacts.get(id).add(grid[i][j]);'
            if (!added[i][j]) {
                added[i][j] = true;
                tovisit.add(i);
                tovisit.add(j);
            }
            return 0;
        }
        visited[i][j] = true;
        return 1 + visit(id1, i, j + 1, visited) + visit(id1, i, j - 1, visited) + 
            visit(id1, i + 1, j, visited) + visit(id1, i - 1, j, visited);
    }
    
    private static class FastScanner {
        BufferedReader br;
        String line;
        StringTokenizer st;
        
        public FastScanner(String file) throws IOException {
            br = new BufferedReader(new java.io.FileReader(file));
        }
        
        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                line = br.readLine();
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
        
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        
        void close() throws IOException {
            br.close();
        }
    }
}
