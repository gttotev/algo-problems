import java.io.*;
import java.util.StringTokenizer;
import java.util.PriorityQueue;

public class JobQueue {
    private int numWorkers;
    PriorityQueue<TWorker> workerQueue;
    
    private int[] jobs;
    private int numJobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        workerQueue = new PriorityQueue<TWorker>(numWorkers);
        for (int w = 0; w < numWorkers; w++) {
            workerQueue.add(new TWorker(w));
        }
        numJobs = in.nextInt();
        assignedWorker = new int[numJobs];
        startTime = new long[numJobs];
        jobs = new int[numJobs];
        for (int i = 0; i < numJobs; i++) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        for (int i = 0; i < numJobs; i++) {
            TWorker worker = workerQueue.poll();
            assignedWorker[i] = worker.id;
            startTime[i] = worker.addJob(jobs[i]);
            workerQueue.add(worker);
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }
    
    static class TWorker implements Comparable<TWorker> {
        int id;
        long startTime;
        long endTime;
        public TWorker(int id) {
            this.id = id;
            startTime = 0;
            endTime = 0;
        }
        public long addJob(int jobTime) {
            startTime = endTime;
            endTime = startTime + (long) jobTime;
            return startTime;
        }
        public int compareTo(TWorker worker) {
            int timeComp = Long.compare(this.endTime, worker.endTime);
            return (timeComp == 0 ? Integer.compare(this.id, worker.id) : timeComp);
        }
        public String toString() {
            return "<(" + id + "): " + startTime + "~" + endTime + ">";
        }
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
