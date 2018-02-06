import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Lifeguards {
    public static void main(String[] args) throws java.io.IOException {
        BufferedReader in = new BufferedReader(new java.io.FileReader("lifeguards.in"));
        PrintWriter out = new PrintWriter("lifeguards.out");
        String[] read;
        int i, st, depth, tspan;
        int minCov = Integer.MAX_VALUE;
        int time = 0;
        int n = Integer.parseInt(in.readLine());
        LinkedList<Integer> timeline = new LinkedList<>();
        java.util.Iterator<Integer> it;
        ShiftEdge se;
        ShiftEdge[] she = new ShiftEdge[2 * n];
        for (i = 0; i < n; i++) {
            read = in.readLine().split(" ");
            she[i * 2] = new ShiftEdge(i, Integer.parseInt(read[0]), true);
            she[i * 2 + 1] = new ShiftEdge(i, Integer.parseInt(read[1]), false);
        }
        java.util.Arrays.sort(she);
        st = she[0].t;
        timeline.add(she[0].id);
        depth = 1;
        for (i = 1; i < she.length; i++) {
            se = she[i];
            tspan = se.t - st;
            if (depth > 0)
                time += tspan;
            if (depth == 1 && tspan < minCov)
                minCov = tspan;
            if (se.start) {
                depth++;
                timeline.add(se.id);
            } else {
                depth--;
                if (minCov > 0) {
                    it = timeline.descendingIterator();
                    while (it.next() != se.id);
                    if (it.hasNext())
                        minCov = 0;
                    it.remove();
                }
            }
            st = se.t;
        }
        out.println(time - minCov);
        in.close();
        out.close();
    }
    
    private static class ShiftEdge implements Comparable<ShiftEdge> {
        int id, t;
        boolean start;
        ShiftEdge(int id, int t, boolean s) {
            this.id = id;
            this.t = t;
            this.start = s;
        }
        public int compareTo(ShiftEdge o) {
            return t - o.t;
        }
    }
}
