import java.util.*;

public class DotProduct {
    private static long minDotProduct(int seqLength, ArrayList<Integer> a, ArrayList<Integer> b) {
        long result = 0;
        for (int i = 0; i < seqLength; i++) {
            result += (long) a.get(i) * b.get(seqLength-1-i);
        }
        return result;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        ArrayList<Integer> a = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            sortInsert(a, scanner.nextInt());
        }
        ArrayList<Integer> b = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            sortInsert(b, scanner.nextInt());
        }
        
        System.out.println(minDotProduct(n, a, b));
    }
    
    private static void sortInsert(ArrayList<Integer> arr, int item) {
        boolean inserted = false;
        for (int e = 0; e < arr.size(); e++) {
            if (item >= arr.get(e)) {
                arr.add(e, item);
                inserted = true;
                break;
            }
        }
        if (!inserted)
            arr.add(item);
    }
}

