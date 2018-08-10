import java.util.Scanner;
import java.util.PriorityQueue;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        PriorityQueue<Query> queries = new PriorityQueue<Query>();
        char in = scan.next().charAt(0);
        while (in != '#') {
            queries.add(new Query(scan.nextInt(), scan.nextInt()));
            in = scan.next().charAt(0);
        }
        for (int i = scan.nextInt(); i > 0; i--) {
            Query q = queries.poll();
            System.out.println(q.id);
            q.update();
            queries.add(q);
        }
    }
    
    private static class Query implements Comparable<Query> {
        int id, period, time;
        public Query(int i, int p) {
            id = i;
            period = p;
            time = p;
        }
        public void update() {
            time += period;
        }
        public int compareTo(Query q) {
            int p = time - q.time;
            return (p == 0 ? id - q.id : p);
        }
    }
}
