import java.util.*;
import java.math.BigInteger;

public class FibonacciHuge {
    private static ArrayList<Integer> generatePisano(int m) {
        ArrayList<Integer> pisano = new ArrayList<Integer>();
        int pSize = 2;
        pisano.add(0);
        pisano.add(1);
        while (true) {
            int lastPisano = pisano.get(pSize-1);
            int currPisano = (pisano.get(pSize-2) + lastPisano) % m;
            if (lastPisano == 0 && currPisano == 1) {
                pisano.remove(pSize-1);
                break;
            }
            pisano.add(currPisano);
            pSize++;
        }
        return pisano;
    }
    
    private static long getFibonacciHuge(long n, long m) {
        ArrayList<Integer> pisano = generatePisano((int) m);
        return pisano.get((int)(n % pisano.size()));
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        
        System.out.println(getFibonacciHuge(n, m));
    }
}
