import java.io.*;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class HashChains {
    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private ArrayList<LinkedList<String>> hashSet;
    // for hash function
    private int numBuckets;
    private int prime = 1000000007;
    private int multiplier = 263;

    private int hash(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int)hash % numBuckets;
    }

    private void processQuery(Query query) {
        LinkedList<String> setList = hashSet.get(query.hashVal);
        switch (query.type) {
            case "add":
                if (!setList.contains(query.str))
                    setList.addFirst(query.str);
                break;
            case "del":
                setList.remove(query.str);
                break;
            case "find":
                out.println(setList.contains(query.str) ? "yes" : "no");
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            case "check":
                for (String str : setList) {
                    out.print(str + " ");
                }
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        numBuckets = in.nextInt();
        hashSet = new ArrayList<LinkedList<String>>(numBuckets);
        for (int i = 0; i < numBuckets; i++) {
            hashSet.add(new LinkedList<String>());
        }
        int numQuery = in.nextInt();
        for (int i = 0; i < numQuery; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }
    
    private Query readQuery() throws IOException {
        String type = in.next();
        int ind = -1;
        String str = "";
        if (!type.equals("check")) {
            str = in.next();
            ind = hash(str);
        } else {
            ind = in.nextInt();
        }
        return new Query(type, ind, str);
    }

    static class Query {
        String type;
        int hashVal;
        String str;

        public Query(String type, int ind, String str) {
            this.type = type;
            this.hashVal = ind;
            this.str = str;
        }
    }

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }
    
    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
