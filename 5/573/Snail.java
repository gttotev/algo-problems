import java.util.Scanner;
import java.util.Random;

class Main {
    public static void main(String[] args) {
        if (args.length > 1 && args[0].equals("gen-tests")) {
            genTests(Integer.parseInt(args[1]));
            System.exit(0);
        }
        Scanner scan = new Scanner(System.in);
        int well = scan.nextInt();
        while (well > 0) {
            System.out.println(snailClimb(well, scan.nextInt(), scan.nextInt(), scan.nextInt()));
            well = scan.nextInt();
        }
    }
    
    private static void genTests(int n) {
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int h = rand.nextInt(100) + 1;
            int u = rand.nextInt(rand.nextInt(8) > 0 ? h : 100) + 1;
            int d = rand.nextInt(rand.nextInt(6) > 0 ? u : 100) + 1;
            int f = rand.nextInt(rand.nextInt(5) > 0 ? d : 100) + 1;
            System.err.format("%d %d %d %d\n", h, u, d, f);
            System.out.println(snailClimb(h, u, d, f));
        }
        System.err.println("0 0 0 0");
    }
    
    private static String snailClimb(int well, double up, int down, double ff) {
        int day = 1;
        double climb = up;
        ff = up * ff / 100.0;
        while (climb >= 0 && climb <= well) {
            climb -= down;
            if (climb < 0) break;
            day++;
            up -= ff;
            if (up < 0) {
                up = 0;
                ff = 0;
            }
            climb += up;
        }
        return (climb < 0 ? "failure" : "success") + " on day " + day;
    }
}
