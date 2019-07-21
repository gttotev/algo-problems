import java.util.Scanner;

public class Change {
    private static int getChange(int money) {
        int coins = money / 10;
        money = money % 10;
        coins += money / 5;
        money = money % 5;
        return coins + money;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int money = scanner.nextInt();
        System.out.println(getChange(money));
    }
}
