import java.util.Scanner;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Integer> signals;
        int sg = scan.nextInt();
        while (sg > 0) {
            int sgsLen = -1;
            int minSg = sg;
            int minSgi = 0;
            int g, c;
            int syncTime = 18002;
            signals = new ArrayList<Integer>();
            while (sg > 0) {
                signals.add(sg);
                sgsLen++;
                if (sg < minSg) {
                    minSg = sg;
                    minSgi = sgsLen;
                }
                sg = scan.nextInt();
            }
            signals.remove(minSgi);
            g = minSg - 5;
            c = minSg * 2;
            for (int n = 1; syncTime > 18001; n++) {
                for (int i = 0; i < g; i++) {
                    int t = i + n * c;
                    boolean green = true;
                    //System.out.println(t);
                    if (t > 18000) {
                        syncTime = 18001;
                        break;
                    }
                    for (int s = 0; s < sgsLen; s++) {
                        if (isNotGreen(signals.get(s), t)) {
                            green = false;
                            break;
                        }
                    }
                    if (green) {
                        syncTime = t;
                        break;
                    }
                }
            }
            if (syncTime > 18000) {
                System.out.println("Signals fail to synchronise in 5 hours");
            } else {
                System.out.format("%02d:%02d:%02d\n", syncTime / 3600,
                                  syncTime % 3600 / 60, syncTime % 60);
            }
            sg = scan.nextInt();
        }
    }
    
    private static boolean isNotGreen(int s, int t) {
        return t % (2 * s) >= s - 5;
    }
}
