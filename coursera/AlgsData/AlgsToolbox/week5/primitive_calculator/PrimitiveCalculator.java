import java.util.*;

public class PrimitiveCalculator {
    private static int[] optimalSequence(int n) {
        int[] minSeqs = new int[n];
        minSeqs[0] = 0;
        for (int i = 1; i < n; i++) {
            int currN = i + 1;
            int minOps = Integer.MAX_VALUE;
            if (currN % 3 == 0) {
                minOps = minSeqs[(currN / 3)-1] + 1;
            }
            if (currN % 2 == 0) {
                minOps = Math.min(minOps, minSeqs[(currN / 2)-1] + 1);
            }
            minSeqs[i] = Math.min(minOps, minSeqs[currN - 2] + 1);
        }
        int[] sequence = new int[minSeqs[n-1]+1];
        sequence[minSeqs[n-1]] = n;
        for (int i = minSeqs[n-1]-1; i >= 0; i--) {
            int currN = sequence[i + 1];
            if (currN % 3 == 0 && minSeqs[(currN/3)-1] == i) {
                sequence[i] = currN / 3;
            } else if (currN % 2 == 0 && minSeqs[(currN/2)-1] == i) {
                sequence[i] = currN / 2;
            } else {
                sequence[i] = currN-1;
            }
        }
        //System.out.println(Arrays.toString(minSeqs));
        return sequence;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] sequence = optimalSequence(n);
        System.out.println(sequence.length - 1);
        for (int x : sequence) {
            System.out.print(x + " ");
        }
    }
}
