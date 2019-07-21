import java.util.*;

public class Knapsack {
    static int optimalWeight(int capacity, int[] bars) {
        int[][] knapsack = new int[bars.length+1][capacity+1];
        for (int i = 1; i <= bars.length; i++) {
            for (int e = 1; e <= capacity; e++) {
                knapsack[i][e] = knapsack[i-1][e];
                if (bars[i-1] <= e) {
                    knapsack[i][e] = Math.max(knapsack[i][e], knapsack[i-1][e - bars[i-1]] + bars[i-1]);
                }
            }
        }
        return knapsack[bars.length][capacity];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int capacity = scanner.nextInt();
        int n = scanner.nextInt();
        int[] bars = new int[n];
        for (int i = 0; i < n; i++) {
            bars[i] = scanner.nextInt();
        }
        System.out.println(optimalWeight(capacity, bars));
    }
}
