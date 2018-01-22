import java.io.BufferedReader;

class Main {
    public static void main(String[] args) throws java.io.IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        int round = Integer.parseInt(br.readLine());
        boolean[] wordChars, guessChars;
        int hangman, wclen, wlen, glen, wci, gci, i;
        String result, word, guess;
        while (round != -1) {
            hangman = 0;
            wordChars = new boolean[26];
            guessChars = new boolean[26];
            wclen = 0;
            result = "chickened out";
            word = br.readLine();
            wlen = word.length();
            guess = br.readLine();
            glen = guess.length();
            for (i = 0; i < wlen; i++) {
                wci = word.charAt(i) - 'a';
                if (!wordChars[wci]) {
                    wclen++;
                    wordChars[wci] = true;
                }
            }
            if (wclen <= glen) {
                for (i = 0; i < glen; i++) {
                    gci = guess.charAt(i) - 'a';
                    if (!guessChars[gci]) {
                        guessChars[gci] = true;
                        if (wordChars[gci]) {
                            wclen--;
                        } else {
                            hangman++;
                        }
                        if (hangman == 7) {
                            result = "lose";
                            break;
                        }
                        if (wclen == 0) {
                            result = "win";
                            break;
                        }
                    }
                }
            }
            System.out.format("Round %d\nYou %s.\n", round, result);
            round = Integer.parseInt(br.readLine());
        }
    }
}
