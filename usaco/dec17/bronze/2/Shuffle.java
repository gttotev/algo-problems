import java.io.*;

public class Shuffle {
    static StreamTokenizer in;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("shuffle.in"));
        in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shuffle.out")));
        int n = nextInt();
        int[] shuffle = new int[n];
        for (int i = 0; i < n; i++) {
            shuffle[i] = nextInt() - 1;
        }
        String[] ids = br.readLine().split(" ");
        
        for (int s = 0; s < 3; s++) {
            String[] newIds = new String[n];
            for (int i = 0; i < n; i++) {
                newIds[i] = ids[shuffle[i]];
            }
            ids = newIds;
        }
        for (int i = 0; i < n; i++) {
            out.println(ids[i]);
        }
        out.close();
        //System.exit(0);
    }
    
    static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }
}
