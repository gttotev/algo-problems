import java.util.Scanner;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Stack<Integer> carrier = new Stack<>();
        Queue<Integer>[] platforms;
        Queue<Integer> plt;
        int stations, capacity, queueLen;
        int i, j, stationsFull, s, minutes, cargo;
        for (int set = scan.nextInt(); set > 0; set--) {
            stations = scan.nextInt();
            capacity = scan.nextInt();
            queueLen = scan.nextInt();
            platforms = new LinkedList[stations];
            stationsFull = stations;
            for (i = 0; i < stations; i++) {
                platforms[i] = new LinkedList<Integer>();
                s = scan.nextInt();
                if (s == 0) {
                    stationsFull--;
                    continue;
                }
                for (j = s; j > 0; j--) {
                    platforms[i].add(scan.nextInt() - 1);
                }
            }
            s = 0;
            minutes = 0;
            while (true) {
                plt = platforms[s];
                while (!carrier.isEmpty()) {
                    cargo = carrier.pop();
                    if (cargo != s) {
                        j = plt.size();
                        if (j == queueLen) {
                            carrier.add(cargo);
                            break;
                        }
                        if (j == 0) stationsFull++;
                        plt.add(cargo);
                    }
                    minutes++;
                }
                while (carrier.size() < capacity && !plt.isEmpty()) {
                    cargo = plt.poll();
                    carrier.push(cargo);
                    minutes++;
                    if (plt.isEmpty()) {
                        stationsFull--;
                        break;
                    }
                }
                if (stationsFull == 0 && carrier.isEmpty()) break;
                s = (s + 1) % stations;
                minutes += 2;
            }
            System.out.println(minutes);
        }
    }
}
