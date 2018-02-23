import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class Rental {
    public static void main(String[] args) throws java.io.IOException {
        BufferedReader in = new BufferedReader(new java.io.FileReader("rental.in"));
        PrintWriter out = new PrintWriter(new java.io.File("rental.out"));
        String[] line = in.readLine().split(" ");
        int i, milk, buy, sold;
        long cmoney;
        long money = 0;
        int n = Integer.parseInt(line[0]);
        int[] cows = new int[n];
        int m = Integer.parseInt(line[1]);
        Store[] stores = new Store[m];
        int r = Integer.parseInt(line[2]);
        int[] rentals = new int[r];
        int fp = Math.min(n, r);
        int st = m - 1;
        for (i = 0; i < n; i++) {
            cows[i] = Integer.parseInt(in.readLine());
        }
        Arrays.sort(cows);
        for (i = 0; i < m; i++) {
            line = in.readLine().split(" ");
            stores[i] = new Store(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
        }
        Arrays.sort(stores);
        for (i = 0; i < r; i++) {
            rentals[i] = Integer.parseInt(in.readLine());
        }
        Arrays.sort(rentals);
        for (i = 0; i < fp; i++) {
            money += rentals[r - i - 1];
        }
        cmoney = money;
        for (i = n - 1; i >= 0 && st >= 0; i--) {
            milk = cows[i];
            cmoney -= (i < r ? rentals[r - i - 1] : 0);
            while (st >= 0) {
                buy = stores[st].milk;
                sold = Math.min(milk, buy);
                cmoney += sold * stores[st].price;
                stores[st].milk = buy - sold;
                milk -= sold;
                if (milk == 0) {
                    break;
                }
                st--;
            }
            if (cmoney < money)
                break;
            money = cmoney;
        }
        out.println(money);
        in.close();
        out.close();
    }
    
    private static class Store implements Comparable<Store> {
        int milk, price;
        Store(int m, int p) {
            milk = m;
            price = p;
        }
        public int compareTo(Store s) {
            return price - s.price;
        }
    }
}
