import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Cowpatibility {
    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner("cowpatibility.in");
        PrintWriter out = new PrintWriter(new java.io.File("cowpatibility.out"));
        int notCompat = 0;
        HashMap<Integer, ArrayList<Integer>> ic = new HashMap<>();
        int n = in.nextInt();
        int[][] cows = new int[n][5];
        for (int i = 0; i < n; i++) {
        		for (int j = 0; j < 5; j++) {
        			int f = in.nextInt();
        			cows[i][j] = f;
        			ArrayList<Integer> icList = ic.get(f);
        			if (icList == null) {
        				icList = new ArrayList<>();
        				ic.put(f, icList);
        			}
        			icList.add(i);
                }
                System.out.println();
        }
        //System.out.println(ic);
        for (int i = 0; i < n; i++) {
        		int nof = 0;
        		for (int j = 0; j < 5; j++) {
        			int f = cows[i][j];
        			ArrayList<Integer> icList = ic.get(f);
        			if (icList.size() > nof) {
        				nof = icList.size();
        			}
        		}
			notCompat += n - nof - 1;
			//System.out.println(i);
        }
        out.println(notCompat);
        in.close();
        out.close();
    }
    
    private static class FastScanner {
        static final char DELIMITER = ' ';
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
        
        String nextLine() throws IOException {
            if (st == null || !st.hasMoreTokens()) {
                return br.readLine();
            } else {
                StringBuilder sb = new StringBuilder();
                while (st.hasMoreTokens()) {
                    sb.append(st.nextToken());
                    sb.append(DELIMITER);
                }
                st = null;
                sb.deleteCharAt(sb.length()-1);
                return sb.toString();
            }
        }
        
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        
        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
        
        void close() throws IOException {
            br.close();
        }
    }
}
