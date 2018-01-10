import java.util.Scanner;
import java.util.Calendar;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Calendar cal;
        int days = scan.nextInt();
        int date = scan.nextInt();
        int mon, year;
        while (date > 0) {
            mon = scan.nextInt();
            year = scan.nextInt();
            cal = new java.util.GregorianCalendar(year, mon - 1, date);
            cal.add(Calendar.DATE, days);
            System.out.format("%d %d %d\n", cal.get(Calendar.DATE),
                          cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
            days = scan.nextInt();
            date = scan.nextInt();
        }
    }
}
