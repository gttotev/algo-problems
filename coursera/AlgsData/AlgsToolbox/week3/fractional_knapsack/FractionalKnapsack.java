import java.util.Scanner;
import java.util.ArrayList;

public class FractionalKnapsack {
    private static double getOptimalValue(int capacity, ArrayList<double[]> items) {
        double maxValue = 0;
        int i = 0;
        //while (i < items.size() && capacity > 0) {
        for (double[] item : items) {
            //double[] item = items.get(i);
            double weight = item[0];
            capacity -= weight;
            if (capacity > 0) {
                maxValue += weight * item[1];
            } else {
                maxValue += (weight + capacity) * item[1];
                break;
            }
            i++;
        }
        return maxValue;
    }
    
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int numItems = scanner.nextInt();
        int capacity = scanner.nextInt();
        ArrayList<double[]> items = new ArrayList<double[]>(); // {weight, unit}: w*u = value
        for (int i = 0; i < numItems; i++) {
            int value = scanner.nextInt();
            int weight = scanner.nextInt();
            double unit = (double) value / weight;
            boolean inserted = false;
            for (int e = 0; e < items.size(); e++) {
                if (unit > items.get(e)[1]) {
                    items.add(e, new double[] {weight, unit});
                    inserted = true;
                    break;
                }
            }
            if (!inserted)
                items.add(new double[] {weight, unit});
        }
        
        System.out.println(getOptimalValue(capacity, items));
    }
} 
