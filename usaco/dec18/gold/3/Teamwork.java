import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class Teamwork {
    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner("teamwork.in");
        PrintWriter out = new PrintWriter(new java.io.File("teamwork.out"));
        int i, j, next;
        int skillSum = 0;
        int n = in.nextInt();
        CowSkill skill[] = new CowSkill[n];
        int k = in.nextInt();
        for (i = 0; i < n; i++) skill[i] = new CowSkill(i, in.nextInt());
        for (j = n - 1; j >= 0; j -= k) {
        		CowSkill max = new CowSkill(-1, 0);
        		for (i = j; i > Math.max(j - k, -1); i--) {
        			if (skill[i].s > max.s) {
        				max.s = skill[i].s;
        				max.i = i;
        			}
        			skill[i].leader = max;
        		}
        		max.size = j - i;
        		skillSum += max.s * max.size;
        }
        System.out.println(skillSum);
        //for (i = 0; i < n; i++) System.out.println(skill[i]);
        for (j = ((n % k) - 1 + k) % k; j < n; j = i - 1) {
        		System.out.println(j);
        		CowSkill leader = skill[j].leader;
        		//for (int e = 0; e < n; e++) System.out.println(skill[e]);
        		for (i = j + 1; i < n; i++) {
        			System.out.println(">" + i);
        			CowSkill otherLeader = skill[i].leader;
        			for (int e = 0; e < n; e++) System.out.println(skill[e]);
        			//if (i == otherLeader.i) break; // TODO: Switch leaders?
        			if (leader.size < k) {
        				if (leader.s > otherLeader.s) {
        					leader.size++;
        					otherLeader.size--;
        					skill[i].leader = leader;
        					skillSum += leader.s - otherLeader.s;
        				}
        			} else {
        				int posFirst = i - k;
        				if (posFirst == leader.i) break;
        				if (skill[posFirst].s > otherLeader.s) {
        					skill[posFirst].leader = null;
        					otherLeader.size--;
        					skill[i].leader = leader;
        					skillSum += skill[posFirst].s - otherLeader.s;
        				}
        			}
        		}
        		if (i == n) break;
        		leader = skill[i].leader;
        		while (++i < n && skill[i].leader.i == leader.i); 
        }
        System.out.println(skillSum);
        in.close();
        out.close();
    }
    
    private static class CowSkill {
    		int i, s, size;
    		CowSkill leader;
    		public CowSkill(int i, int s) {
    			this.i = i;
    			this.s = s;
    		}
    		public String toString() {
    			return "(" + i + "," + s + ")[" + leader + "]";
    		}
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
