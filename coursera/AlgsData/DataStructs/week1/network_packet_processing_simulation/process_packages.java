import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Request {
    public int arrival_time;
    public int process_time;
    
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }
}

class Response {
    public boolean dropped;
    public int start_time;
    
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }
}

class Buffer {
    private int size;
    private ArrayList<Integer> queue;
    
    public Buffer(int size) {
        this.size = size;
        this.queue = new ArrayList<Integer>();
    }

    public Response Process(Request request) {
        while (queue.size() > 0 && queue.get(0) <= request.arrival_time)
            queue.remove(0);
        int queueSize = queue.size();
        if (queueSize == size)
            return new Response(true, -1);
        int startTime = (queueSize > 0 ? queue.get(queueSize-1) : request.arrival_time);
        int finishTime = startTime + request.process_time;
        queue.add(finishTime);
        return new Response(false, startTime);
        //int lastFinish = (queueSize > 0 ? queue.get(queueSize-1) : 0);
        //queue.add(lastFinish + (size == 1 ? request.arrival_time : 0) + request.process_time);
        //return new Response(false, (queueSize > 0 ? lastFinish : request.arrival_time));
    }
}

class process_packages {
    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
    }
}
