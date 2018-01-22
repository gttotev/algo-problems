import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<AnagramString> dict = new ArrayList<AnagramString>();
        ArrayList<String> result = new ArrayList<String>();
        int dictLen = 0;
        int runLen = 1;
        AnagramString lastWord;
        String word = scan.next();
        while (word.charAt(0) != '#') {
            dict.add(new AnagramString(word));
            dictLen++;
            word = scan.next();
        }
        dict.sort(new Comparator<AnagramString>() {
            public int compare(AnagramString s1, AnagramString s2) {
                return s1.anagram.compareTo(s2.anagram);
            }
        });
        lastWord = dict.get(0);
        for (int i = 1; i < dictLen; i++, runLen++) {
            AnagramString curWord = dict.get(i);
            if (!curWord.anagram.equals(lastWord.anagram)) {
                if (runLen == 1)
                    result.add(lastWord.orig);
                runLen = 0;
                lastWord = curWord;
            }
        }
        if (runLen == 1)
            result.add(lastWord.orig);
        result.sort(new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
        dictLen = result.size();
        for (int i = 0; i < dictLen; i++) {
            System.out.println(result.get(i));
        }
    }
    
    private static class AnagramString {
        String orig;
        String anagram;
        AnagramString(String s) {
            char[] sa = s.toLowerCase().toCharArray();
            Arrays.sort(sa);
            anagram = new String(sa);
            orig = s;
        }
    }
}
