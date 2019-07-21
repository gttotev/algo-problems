import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<Bracket> brStack = new Stack<Bracket>();
        int brErrI = -1;
        for (int i = 0; i < text.length(); i++) {
            char next = text.charAt(i);
            if (next == '(' || next == '[' || next == '{') {
                brStack.push(new Bracket(next, i));
            } else if (next == ')' || next == ']' || next == '}') {
                if (brStack.empty()) {
                    // Unmatched closing bracket
                    brErrI = i;
                    break;
                }
                Bracket open = brStack.pop();
                if (!open.match(next)) {
                    // Wrong closing bracket
                    brErrI = i;
                    break;
                }
            }
        }
        // Printing answer, write your code here
        // Success or unmatched opening
        if (brErrI > -1)
            System.out.println(brErrI+1);
        else if (!brStack.empty()) {
            int openI = -1;
            while (!brStack.empty()) {
                openI = brStack.pop().position;
            }
            System.out.println(openI+1);
        } else {
            System.out.println("Success");
        }
    }
}
