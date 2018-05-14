import java.io.BufferedReader;
import java.io.PrintWriter;

public class RestStops {
    public static void main(String[] args) throws java.io.IOException {
        BufferedReader in = new BufferedReader(new java.io.FileReader("reststops.in"));
        PrintWriter out = new PrintWriter(new java.io.File("reststops.out"));
        String[] input = in.readLine().split(" ");
        int i;
        int s = 0;
        int pos = 0;
        int trailLen = Integer.parseInt(input[0]);
        int n = Integer.parseInt(input[1]);
        Stop next;
        Stop[] stops = new Stop[n];
        int rfarmer = Integer.parseInt(input[2]);
        int rbessie = Integer.parseInt(input[3]);
        long tdiff;
        long rdiff = rfarmer - rbessie;
        long tasty = 0;
        for (i = 0; i < n; i++) {
            input = in.readLine().split(" ");
            stops[i] = new Stop(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
        }
        java.util.Arrays.sort(stops);
        //System.out.println(java.util.Arrays.toString(stops));
        while (pos < trailLen) {
            for (i = s; i < n; i++) {
                if (stops[i].x > pos) break;
            }
            if (i == n) break;
            s = i + 1;
            next = stops[i];
            tdiff = (next.x - pos) * rdiff; // TODO: Careful with overflow...
            tasty += next.c * tdiff;
            pos = next.x;
        }
        out.println(tasty);
        in.close();
        out.close();
    }
    
    private static class Stop implements Comparable<Stop> {
        int x, c;
        public Stop(int x, int c) {
            this.x = x;
            this.c = c;
        }
        public int compareTo(Stop o) {
            return o.c - c;
        }
        public String toString() {
            return "[" + x + "]" + c;
        }
    }
}
