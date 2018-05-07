import java.io.*;

public class Measurement {
    static StreamTokenizer in;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("measurement.in"));
        in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("measurement.out")));
        int n = nextInt();
        int[] cowsOut = {7, 7, 7};
        int[][] log = new int[100][2];
        int[] prevDelta = {3, 0};
        int wallChanges = 0;
        for (int i = 0; i < n; i++) {
            String[] input = br.readLine().split(" ");
            int day = Integer.parseInt(input[0]) - 1;
            log[day][0] = cowNum(input[1].charAt(0));
            log[day][1] = Integer.parseInt(input[2]);
        }
        
        for (int i = 0; i < 100; i++) {
            if (log[i][1] == 0) continue;
            int cow = log[i][0];
            cowsOut[cow] = cowsOut[cow] + log[i][1];
            int[] delta = cowsDelta(cowsOut);
            if (delta[0] != prevDelta[0] || delta[1] != prevDelta[1])
                wallChanges++;
            prevDelta = delta;
        }
        
        out.println(wallChanges);
        out.close();
        //System.exit(0);
    }
    
    static int[] cowsDelta(int[] cowsOut) {
        int[] d = {1, 0};
        for (int i = 1; i < 3; i++) {
            if (cowsOut[i] > cowsOut[d[1]]) {
                d[0] = 1;
                d[1] = i;
            } else if (cowsOut[i] == cowsOut[d[1]]) {
                d[0] = d[0] + 1;
            }
        }
        return d;
    }
    
    static int cowNum(char l) {
        switch (l) {
            case 'B':
                return 0;
            case 'E':
                return 1;
            case 'M':
                return 2;
        }
        return -1;
    }
    
    static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }
}
