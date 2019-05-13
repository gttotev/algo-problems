import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] wireDirs = {"+x", "-x", "+y", "-y", "+z", "-z"};
        Scanner scan = new Scanner(System.in);
        int len;
        while ((len = scan.nextInt()) > 0) {
            int dir = 0;
            for (int i = 1; i < len; i++) {
                int action = toDir(scan.next());
                if (action < 0) continue;
                if (dir < 2) {
                    dir = (dir + action) % 2 + (action / 2) * 2;
                } else if (Math.abs(dir - action) < 2 && dir + action != 7) {
                    int d = dir % 2;
                    int a = action % 2;
                    dir = (d^a)^1;
                }
                //System.out.println(wireDirs[action] + " -> " + wireDirs[dir]);
            }
            System.out.println(wireDirs[dir]);
        }
    }
    
    private static int toDir(String m) {
        return 2 * (m.charAt(1) - 'x') + (m.charAt(0) - '+') / 2;
    }
}
