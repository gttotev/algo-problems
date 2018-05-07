import java.io.*;

public class Billboard {
    static StreamTokenizer in;
    public static void main(String[] args) throws IOException {
        in = new StreamTokenizer(new BufferedReader(new FileReader("billboard.in")));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("billboard.out")));
        int[][] rect = new int[3][4];
        int[][] lengths = new int[3][2];
        int area = 0;
        int[][] overlap = new int[2][2];
        for (int i = 0; i < 12; i++) {
            int t = i / 4;
            int e = i % 4;
            rect[t][e] = nextInt();
            if (e == 3) {
                lengths[t][0] = rect[t][2] - rect[t][0];
                lengths[t][1] = rect[t][3] - rect[t][1];
                if (t < 2)
                    area += lengths[t][0] * lengths[t][1];
            }
        }
        for (int i = 0; i < 4; i++) {
            int d = i / 2;
            int m = i % 2;
            overlap[d][m] = Math.min(rect[d][m], rect[2][m]) - Math.max(rect[d][m+2], rect[2][m+2]) +
                lengths[d][m] + lengths[2][m];
            if (overlap[d][m] < 0)
                overlap[d][m] = 0;
        }
        //System.out.println(area - overlap[0][0] * overlap[0][1] - overlap[1][0] * overlap[1][1]);
        
        out.println(area - overlap[0][0] * overlap[0][1] - overlap[1][0] * overlap[1][1]);
        out.close();
        //System.exit(0);
    }
    
    static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }
}
