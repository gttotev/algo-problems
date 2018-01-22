import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class Template {
    public static void main(String[] args) throws java.io.FileNotFoundException {
        Scanner in = new Scanner(new File("template.in"));
        PrintWriter out = new PrintWriter(new File("template.out"));
        
        out.close();
    }
}
