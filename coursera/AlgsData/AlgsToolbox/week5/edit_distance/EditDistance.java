import java.util.*;

class EditDistance {
    public static int editDistance(String s, String t) {
        int[][] edits = new int[s.length()+1][t.length()+1];
        int i = 0;
        while (i <= s.length() || i <= t.length()) {
            if (i <= s.length())
                edits[i][0] = i;
            if (i <= t.length())
                edits[0][i] = i;
            i++;
        }
        for (int si = 1; si <= s.length(); si++) {
            for (int ti = 1; ti <= t.length(); ti++) {
                int minEdit = Math.min(edits[si-1][ti] + 1, edits[si][ti-1] + 1);
                minEdit = Math.min(minEdit, edits[si-1][ti-1] + (s.charAt(si-1) == t.charAt(ti-1) ? 0 : 1));
                edits[si][ti] = minEdit;
            }
        }
        return edits[s.length()][t.length()];
    }
    
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        String t = scan.next();        
        System.out.println(editDistance(s, t));
    }
}
