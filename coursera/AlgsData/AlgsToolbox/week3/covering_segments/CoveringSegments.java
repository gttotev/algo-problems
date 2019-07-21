import java.util.*;

public class CoveringSegments {
    private static ArrayList<Integer> optimalPoints(ArrayList<Segment> segments) {
        ArrayList<Integer> points = new ArrayList<Integer>();
        points.add(segments.get(0).end);
        for (int i = 1; i < segments.size(); i++) {
            Segment seg = segments.get(i);
            if (!seg.inRange(points.get(points.size()-1))) {
                points.add(seg.end);
            }
        }
        return points;
    }
    
    private static class Segment {
        int start, end;
        public Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }
        public boolean inRange(int point) {
            return (point >= start) && (point <= end);
        }
        public String toString() {
            return "{" + start + "-" + end + "}";
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        ArrayList<Segment> segments = new ArrayList<Segment>();
        for (int i = 0; i < n; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            sortInsert(segments, new Segment(start, end));
        }
        //System.out.println(segments);
        
        ArrayList<Integer> points = optimalPoints(segments);
        System.out.println(points.size());
        for (Integer point : points) {
            System.out.print(point + " ");
        }
    }
    
    private static void sortInsert(ArrayList<Segment> segs, Segment seg) {
        boolean inserted = false;
        for (int e = 0; e < segs.size(); e++) {
            if (seg.end <= segs.get(e).end) {
                segs.add(e, seg);
                inserted = true;
                break;
            }
        }
        if (!inserted)
            segs.add(seg);
    }
}
 
