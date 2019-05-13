import java.io.BufferedReader;
import java.util.HashMap;

class Main {
    private static HashMap<Character, Character> mirror;
    
    public static void main(String[] args) throws java.io.IOException {
        mirror = new HashMap<Character, Character>();
        mirror.put('E', '3');
        mirror.put('3', 'E');
        mirror.put('J', 'L');
        mirror.put('L', 'J');
        mirror.put('S', '2');
        mirror.put('2', 'S');
        mirror.put('Z', '5');
        mirror.put('5', 'Z');
        mirror.put('A', 'A');
        mirror.put('M', 'M');
        mirror.put('O', 'O');
        mirror.put('1', '1');
        mirror.put('8', '8');
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        String str = br.readLine();
        while (str != null) {
            int len = str.length();
            int halfLen = (int) Math.round((double) len / 2.0);
            boolean pal = true;
            boolean mir = true;
            for (int i = 0; i < halfLen; i++) {
                char a = str.charAt(i);
                char b = str.charAt(len - i - 1);
                if (a != b)
                    pal = false;
                if (getMirror(b) != a || getMirror(a) != b)
                    mir = false;
            }
            System.out.print(str + " -- is ");
            if (pal) {
                if (mir)
                    System.out.print("a mirrored palindrome");
                else
                    System.out.print("a regular palindrome");
            } else if (mir) {
                System.out.print("a mirrored string");
            } else {
                System.out.print("not a palindrome");
            }
            System.out.println(".\n");
            str = br.readLine();
        }
    }
    
    private static char getMirror(char c) {
        if ((c > 'S' && c < 'Z') || (c > 'G' && c < 'J'))
            return c;
        Character m = mirror.get(c);
        if (m == null)
            return '$';
        return m.charValue();
    }
}
