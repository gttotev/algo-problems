import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextInt()) {
            int people = scan.nextInt();
            int budget = scan.nextInt();
            int hotels = scan.nextInt();
            int weeks = scan.nextInt();
            int minCost = budget + 1;
            for (int h = 0; h < hotels; h++) {
                int price = people * scan.nextInt();
                for (int w = 0; w < weeks; w++) {
                    if (scan.nextInt() >= people && price < minCost)
                        minCost = price;
                }
            }
            System.out.println(minCost > budget ? "stay home" : minCost);
        }
    }
}
