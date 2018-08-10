import java.util.Scanner;
import java.util.HashMap;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        HashMap<Integer, Integer> flakes = new HashMap<>();
        int n, i, maxLen, curLen, s, j;
        for (int t = scan.nextInt(); t > 0; t--) {
            flakes.clear();
            n = scan.nextInt();
            for (i = curLen = maxLen = 0; i < n; i++, curLen++) {
                s = scan.nextInt();
                //System.out.println(flakes);
                if (flakes.containsKey(s) && (j = flakes.get(s)) >= i - curLen) {
                    // bad
                    //System.out.format("%d: %d, %d\n", i, s, curLen);
                    if (curLen > maxLen) maxLen = curLen;
                    curLen = i - j - 1;
                }
                flakes.put(s, i);
            }
            System.out.println(curLen > maxLen ? curLen : maxLen);
        }
    }
}
