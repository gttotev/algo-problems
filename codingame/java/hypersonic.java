// Passes Wood 3 and 2 Leagues..

import java.util.*;
import java.io.*;

class Player {
    static int width;
    static int wMid;
    static int height;
    static int hMid;
    static Entity[][] map;
    
    static int myId;
    static int mx = -1;
    static int my = -1;
    static int mbombs = 1;
    static int brange = 3;
    static int[] mgoal = {-1, -1};
    static int[] rgoal;
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        width = in.nextInt();
        wMid = width / 2;
        height = in.nextInt();
        hMid = height / 2;
        myId = in.nextInt();
        map = new Entity[height][width];
        rgoal = new int[] {wMid, hMid};
        in.nextLine();
        
        boolean areSymBoxes = true;
        //boolean areSymBoxes = false;
        while (true) {
            for (int i = 0; i < height; i++) {
                map[i] = Entity.getEntityRow(in.nextLine());
            }
            int entities = in.nextInt();
            for (int i = 0; i < entities; i++) {
                int entityType = in.nextInt();
                int owner = in.nextInt();
                int x = in.nextInt();
                int y = in.nextInt();
                int p1 = in.nextInt();
                int p2 = in.nextInt();
                map[y][x] = new Entity(entityType, owner, p1, p2);
                if (entityType == Entity.PLAYER && owner == myId) {
                    mx = x;
                    my = y;
                    mbombs = p1;
                    brange = p2;
                } else if (entityType == Entity.BOMB) {
                    boolean[] dirBlocks = new boolean[4];
                    for (int rad = 1; rad < p2; rad++) {
                        for (int xc = -1; xc <= 2; xc++) {
                            int sqx = x + (rad * (xc % 2));
                            int sqy = y + rad*(Math.abs(xc) - 1);
                            if (!dirBlocks[xc+1] && inRange(sqx, sqy)) {
                                if (map[sqy][sqx].isClear()) {
                                    map[sqy][sqx].type = Entity.DEAD_ZONE;
                                } else {
                                    if (map[sqy][sqx].isRemovableStop()) {
                                        map[sqy][sqx].type = Entity.DEAD_BOX;
                                    }
                                    dirBlocks[xc+1] = true;
                                }
                            }
                        }
                    }
                }
            }
            in.nextLine();
            
            if (isValidGoal(mgoal)) {
                // Go to the goal! Beware staying in one square 2 turns -> goal unreachable
            } else {
                
            }
            
            if (areSymBoxes) {
                goal = findSymBoxes();
                if (goal[0] == -1)
                    areSymBoxes = false;
            }
            if (!areSymBoxes) {
                goal = findNearBoxes();
            }
            
            if (goal[0] == mx && goal[1] == my) {
                System.out.println("BOMB " + mx + " " + my);
            } else {
                System.out.println("MOVE " + goal[0] + " " + goal[1]);
            }
            
            // TODO: Sym boxes blocked, use different finding method.
            // TODO: Implement target system for tracking goals (use isValidGoal()).
            // TODO: Use DEAD_ZONEs to avoid bomb blasts
            
            printMap();
        }
    }
    
    // TODO (maybe): Find closest sym box
    static int[] findSymBoxes() {
        boolean[] validX = {false, true, false, true};
        boolean[] validY = {true, false, true, false};
        for (int c = wMid - brange + 1; c < wMid + brange; c++) {
            if (c == wMid)
                continue;
            for (int r = 0; r < height; r++) {
                int[] goal = {wMid, r};
                if (map[r][c].isBox() && isValidGoal(goal, validX)) {
                    return goal;
                }
            }
        }
        for (int r = hMid - brange + 1; r < hMid + brange; r++) {
            if (r == wMid)
                continue;
            for (int c = 0; c < width; c++) {
                int[] goal = {c, hMid};
                if (map[r][c].isBox() && isValidGoal(goal, validY)) {
                    return goal;
                }
            }
        }
        return new int[] {-1, -1};
    }
    
    static int[] findNearBoxes() {
        int minDist = width + height;
        int dirx = 0;
        int diry = 0;
        for (int r = 0; r < height; r++) {
            if (map[r][mx].isBox() && Math.abs(r - my) < minDist) {
                minDist = Math.abs(r - my);
                diry = (r - my) / minDist;
            }
        }
        for (int c = 0; c < width; c++) {
            if (map[my][c].isBox() && Math.abs(c - mx) < minDist) {
                minDist = Math.abs(c - mx);
                dirx = (c - mx) / minDist;
                diry = 0;
            }
        }
        minDist = (minDist - brange) + 1;
        if (dirx == 0 && diry == 0) {
            if (rgoal[0] == mx && rgoal[1] == my) {
                Random rand = new Random();
                rgoal[0] = rand.nextInt(width);
                rgoal[1] = rand.nextInt(height);
            }
            return rgoal;
        } else if (minDist <= 0) {
            return new int[] {mx, my};
        }
        return new int[] {mx + (dirx * minDist), my + (diry * minDist)};
    }
    
    static boolean isValidGoal(int[] goal, boolean[] dirBlocks) {
        if (!inRange(goal[0], goal[1]) || !map[goal[1]][goal[0]].isMovable())
            return false;
        for (int rad = 1; rad < brange; rad++) {
            for (int xc = -1; xc <= 2; xc++) {
                int sqx = goal[0] + (rad * (xc % 2));
                int sqy = goal[1] + rad*(Math.abs(xc) - 1);
                if (!dirBlocks[xc+1] && inRange(sqx, sqy) && !map[sqy][sqx].isClear()) {
                    if (map[sqy][sqx].isBox())
                        return true;
                    dirBlocks[xc+1] = true;
                }
            }
        }
        return false;
    }
    static boolean isValidGoal(int[] goal) {
        return isValidGoal(goal, new boolean[4]);
    }
    
    static boolean inRange(int x, int y) {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }
    
    static void printMap() {
        for (int i = 0; i < map.length; i++) {
            for (int e = 0; e < map[0].length; e++) {
                System.err.print(map[i][e] + " ");
            }
            System.err.println();
        }
    }
    
    static class Entity {
        public static final int PLAYER = 0;
        public static final int BOMB = 1;
        public static final int ITEM = 2;
        public static final int WALL = 5;
        public static final int DEAD_ZONE = 6;
        public static final int DEAD_BOX = 7;
        public static final int BOX = 8;
        public static final int FLOOR = 9;
        
        int type;
        int owner;
        int p1;
        int p2;
        int boxItem;
        
        public Entity(int type, int owner, int p1, int p2) {
            this.type = type;
            this.owner = owner;
            this.p1 = p1;
            this.p2 = p2;
        }
        public Entity(char rowType) {
            int item = Character.digit(rowType, 10);
            if (item > -1) {
                this.type = BOX;
                this.boxItem = item;
            } else {
                this.type = (rowType == '.' ? FLOOR : WALL);
            }
        }
        public boolean isMovable() {
            return (type == FLOOR || type == PLAYER || type == ITEM);
        }
        public boolean isClear() {
            return (type == FLOOR || type == PLAYER || type == DEAD_ZONE);
        }
        public boolean isRemovableStop() {
            return (type == BOX || type == ITEM || type == BOMB);
        }
        public boolean isBox() {
            return type == BOX;
        }
        public boolean isWall() {
            return type == WALL;
        }
        public String toString() {
            switch (type) {
                case PLAYER:
                    return "@";
                case BOMB:
                    return "*";
                case ITEM:
                    return "$";
                case WALL:
                    return "X";
                case DEAD_ZONE:
                    return "+";
                case DEAD_BOX:
                    return "0";
                case BOX:
                    return "O";
                case FLOOR:
                    return ".";
            }
            return " ";
        }
        
        public static Entity[] getEntityRow(String row) {
            Entity[] arr = new Entity[row.length()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = new Entity(row.charAt(i));
            }
            return arr;
        }
        public static void printMap(Entity[][] map) {
            for (int i = 0; i < map.length; i++) {
                for (int e = 0; e < map[0].length; e++) {
                    System.err.print(map[i][e] + " ");
                }
                System.err.println();
            }
        }
    }
}
