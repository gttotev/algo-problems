import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int rfp = 0;
        int requires, proposals;
        while ((requires = scan.nextInt()) > 0) {
            rfp++;
            proposals = scan.nextInt();
            int maxr = -1;
            double minp = Double.MAX_VALUE;
            String best = "";
            //System.out.format("RFP #%d\n%d %d\n", rfp, requires, proposals);
            for (int b = 0; b <= requires; b++) scan.nextLine();
            for (int i = 0; i < proposals; i++) {
                String name = scan.nextLine();
                double price = scan.nextDouble();
                int rmet = scan.nextInt();
                for (int b = 0; b <= rmet; b++) scan.nextLine();
                if (rmet > maxr || (rmet == maxr && price < minp)) {
                    maxr = rmet;
                    minp = price;
                    best = name;
                }
            }
            System.out.format((rfp > 1 ? "\n" : "") + "RFP #%d\n%s\n", rfp, best);
        }
    }
}
