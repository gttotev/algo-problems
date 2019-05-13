import java.io.BufferedReader;

class Main {
    public static void main(String[] args) throws java.io.IOException {
        BufferedReader scan = new BufferedReader(new java.io.InputStreamReader(System.in));
        int i, clen;
        boolean found;
        char c, b;
        StringBuilder sb;
        String code = scan.readLine();
        while (code.charAt(0) != '#') {
            clen = code.length();
            sb = new StringBuilder(clen);
            found = false;
            c = code.charAt(clen - 1);
            for (i = clen - 2; i >= 0; i--) {
                b = code.charAt(i);
                if (b < c)
                    break;
                c = b;
            }
            //System.out.println(i);
            if (i == -1) {
                System.out.println("No Successor");
            } else {
                System.out.append(code, 0, i);
                b = code.charAt(i);
                for (int e = clen - 1; e > i; e--) {
                    c = code.charAt(e);
                    if (!found && c > b) {
                        System.out.print(c);
                        sb.append(b);
                        found = true;
                    } else {
                        sb.append(c);
                    }
                }
                System.out.println(sb);
            }
            code = scan.readLine();
        }
    }
}
