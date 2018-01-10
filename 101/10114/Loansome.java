import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int months = scan.nextInt();
        while (months >= 0) {
            double down = scan.nextDouble();
            double loan = scan.nextDouble();
            int records = scan.nextInt();
            int ndmo = scan.nextInt();
            double deprec = 0;
            double value = down + loan;
            double mopay = loan / months;
            for (int i = 0; i <= months; i++) {
                double owed = loan - i * mopay;
                if (i == ndmo) {
                    records--;
                    deprec = scan.nextDouble();
                    if (records > 0)
                        ndmo = scan.nextInt();
                }
                value *= 1 - deprec;
                //System.out.println(owed + " " + value);
                if (months == 0 || owed < value) {
                    System.out.println(i + " month" + (i == 1 ? "" : "s"));
                    break;
                }
            }
            if (records > 0) {
                scan.nextDouble();
                for (int i = 1; i < records; i++) {
                    scan.nextLine();
                }
            }
            months = scan.nextInt();
        }
    }
}
