import java.util.*;

public class DifferentSummands {
    private static List<Integer> optimalSummands(int num) {
        List<Integer> summands = new ArrayList<Integer>();
        int sub = (num == 2 ? 2 : 1);
        while (num > 0) {
            num -= sub;
            if (num <= sub && num > 0) {
                summands.add(num + sub);
                break;
            }
            summands.add(sub);
            sub++;
        }
        return summands;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
    }
}
