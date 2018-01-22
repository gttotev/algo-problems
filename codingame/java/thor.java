import java.util.*;
import java.io.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    private static String[] dirChart = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int lightX = in.nextInt(); // the X position of the light of power
        int lightY = in.nextInt(); // the Y position of the light of power
        int thorX = in.nextInt(); // Thor's starting X position
        int thorY = in.nextInt(); // Thor's starting Y position
        int yDiff = lightY - thorY;
        int xDiff = lightX - thorX;
        
        int mainDir = 0;
        int[] moveData = new int[3];
        
        double slope = (double) yDiff / xDiff;
        if (slope == 0) {
            mainDir = (xDiff > 0 ? 2 : 6);
            moveData[0] = Math.abs(xDiff);
        } else if (xDiff == 0) {
            mainDir = (yDiff > 0 ? 4 : 0);
            moveData[0] = Math.abs(yDiff);
        } else if (slope > 0) {
            mainDir = (yDiff > 0 ? 3 : 7);
            moveData = solveSlopeMoves(1, thorX, thorY, lightX, lightY);
        } else {
            mainDir = (yDiff > 0 ? 5 : 1);
            moveData = solveSlopeMoves(-1, thorX, thorY, lightX, lightY);
        }

        // game loop
        while (true) {
            int remainingTurns = in.nextInt(); // The remaining amount of turns Thor can move. Do not remove this line.
            int dir = 0;
            if (moveData[0] > 0) {
                dir = mainDir;
                moveData[0]--;
            } else if (moveData[2] > 0) {
                dir = moveData[1];
                moveData[2]--;
            }
            System.out.println(dirChart[dir]);
        }
    }
    
    private static int[] solveSlopeMoves(int slopeMult, int thorX, int thorY, int lightX, int lightY) {
        int b = thorY - (thorX*slopeMult);
        int[] alignLightX = {lightX, lightX*slopeMult + b};
        int[] alignLightY = {(lightY - b)*slopeMult, lightY};
        int[] retVal = new int[3];
        System.err.println(alignLightX[0] + ", " + alignLightX[1]);
        System.err.println(alignLightY[0] + ", " + alignLightY[1]);
        if (Math.abs(thorY - alignLightX[1]) < Math.abs(thorX - alignLightY[0])) {
            retVal[0] = Math.abs(alignLightX[0] - thorX);
            retVal[1] = (lightY - alignLightX[1] > 0 ? 4 : 0);
            retVal[2] = Math.abs(lightY - alignLightX[1]);
        } else {
            retVal[0] = Math.abs(alignLightY[1] - thorY);
            retVal[1] = (lightX - alignLightY[0] > 0 ? 2 : 6);
            retVal[2] = Math.abs(lightX - alignLightY[0]);
            System.err.println(retVal[0] + " " + retVal[1] + " " + retVal[2]);
        }
        return retVal;
    }
}