import java.io.BufferedReader;
import java.io.PrintWriter;

public class Template {
    public static void main(String[] args) throws java.io.IOException {
        BufferedReader in = new BufferedReader(new java.io.FileReader("template.in"));
        PrintWriter out = new PrintWriter(new java.io.File("template.out"));
        
        in.close();
        out.close();
    }
}
