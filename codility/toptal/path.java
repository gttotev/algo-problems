import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Solution {
    private int[][] board = null;
    private static int[] tf = {-1, 0, 1, 0, -1};

    public int solution(int[][] Board) {
        board = Board;
        HashMap<Integer, ArrayList<int[]>> alocations = findAll();
        int max = 0;

        for (int x = 9; x > 0 && max == 0; x--) {
            ArrayList<int[]> locations = alocations.get(x);
            if (locations == null) continue;
            int[] famax = new int[4];
            for (int[] loc : locations) {
                //System.out.println(loc[0] + "@" + loc[1]);
                int curmax = findMax(loc[0], loc[1], 0, famax, new boolean[Board.length][Board[0].length]);
                //System.err.println(curmax);
                if (curmax > max) max = curmax;
            }
        }

        return max;
    }

    private int findMax(int i, int j, int d, int[] max, boolean[][] visited) {
        //System.out.println(i + " " + j);
        if (visited[i][j] || board[i][j] < max[d]) return 0;
        visited[i][j] = true;
        int[] save = max.clone();
        if (board[i][j] > max[d]) for (int e = d + 1; e < 4; e++) max[e] = 0;
        max[d] = board[i][j];
        if (d == 3) return 1000 * max[0] + 100 * max[1] + 10 * max[2] + max[3];
        int fm = 0;
        for (int[] neighbor : findNeighbors(i, j, max[d+1], visited)) {
            boolean[][] nv = new boolean[visited.length][];
            for (int e = 0; e < visited.length; e++) nv[e] = visited[e].clone();
            int m = findMax(neighbor[0], neighbor[1], d+1, max, nv);
            if (m > fm) fm = m;
        }
        if (fm == 0) for (int e = 0; e < 4; e++) max[e] = save[e];
        return fm;
    }

    private ArrayList<int[]> findNeighbors(int i, int j, int mn, boolean[][] visited) {
        ArrayList<int[]> res = new ArrayList<>();
        for (int e = 0; e < tf.length - 1; e++) {
            int ni = i + tf[e];
            int nj = j + tf[e+1];
            if (ni >= 0 && nj >= 0 && ni < board.length && nj < board[0].length
                    && !visited[ni][nj] && board[ni][nj] >= mn) {
                if (board[ni][nj] > mn) {
                    res.clear();
                    mn = board[ni][nj];
                }
                res.add(new int[] {ni, nj});
            }
        }
        return res;
    }

    private HashMap<Integer, ArrayList<int[]>> findAll() {
        HashMap<Integer, ArrayList<int[]>> locations = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int k = board[i][j];
                if (k == 0) continue;
                if (!locations.containsKey(k)) locations.put(k, new ArrayList<int[]>());
                locations.get(k).add(new int[] {i, j});
            }
        }
        return locations;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        Scanner scan = new Scanner(System.in);
        //System.out.println(scan.hasNextInt());
        while (scan.hasNextInt()) {
	        int n = scan.nextInt();
	        int m = scan.nextInt();
	        int[][] board = new int[n][m];
	        for (int i = 0; i < n; i++) {
	        	for (int j = 0; j < m; j++) {
	        		board[i][j] = scan.nextInt();
	        	}
	        }
	        System.out.println(s.solution(board));
        }
    }
}
