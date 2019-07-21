import java.util.Scanner;
import java.util.Arrays;

public class PointsAndSegments {
    private static int[] countSegments(LineStruct[] line, int numPts) {
        int[] segPts = new int[numPts];
        int segDepth = 0;
        for (int i = 0; i < line.length; i++) {
            LineStruct struct = line[i];
            if (struct.structType == LineStruct.SEG_START) {
                segDepth++;
            } else if (struct.structType == LineStruct.SEG_END) {
                segDepth--;
            } else {
                segPts[struct.origI] = segDepth;
            }
        }
        return segPts;
    }
    
    public static class LineStruct implements Comparable<LineStruct> {
        public static final int SEG_START = 0;
        public static final int POINT = 1;
        public static final int SEG_END = 2;
        
        int linePos;
        int structType;
        int origI;
        
        public LineStruct(int pos, int type, int i) {
            linePos = pos;
            structType = type;
            origI = i;
        }
        public int compareTo(LineStruct struct) {
            int posCmp = Integer.compare(this.linePos, struct.linePos);
            return (posCmp == 0 ? Integer.compare(this.structType, struct.structType) : posCmp);
        }
        public String toString() {
            if (structType == POINT)
                return "{(" + linePos + ")@" + origI +"}";
            return (structType == SEG_START ? "|"+linePos+"~" : "~"+linePos+"|");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int s = scanner.nextInt();
        int p = scanner.nextInt();
        LineStruct[] line = new LineStruct[p + 2*s];
        for (int i = 0; i < s; i++) {
            line[2*i] = new LineStruct(scanner.nextInt(), LineStruct.SEG_START, i);
            line[2*i+1] = new LineStruct(scanner.nextInt(), LineStruct.SEG_END, i);
        }
        for (int i = 0; i < p; i++) {
            line[2*s + i] = new LineStruct(scanner.nextInt(), LineStruct.POINT, i);
        }
        Arrays.sort(line);
        
        int[] cnt = countSegments(line, p);
        for (int x : cnt) {
            System.out.print(x + " ");
        }
        /*String lineStr = Arrays.toString(line);
        System.out.println(lineStr.substring(1, lineStr.length()-1));*/
    }
}
