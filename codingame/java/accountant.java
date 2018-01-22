//CodinGame - The Accountant
import java.util.*;
import java.io.*;

class Player {
    public static final int MAP_WIDTH = 16000;
    public static final int MAP_HEIGHT = 9000;
    public static final int WOLFF_MOVE_DISTANCE = 1000;
    public static final int MAX_FOLLOW_STEPS = 8;
    public static final double MAX_HP_ALLOW_FACTOR = 0.2000;
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        while (true) {
            int mx = in.nextInt();
            int my = in.nextInt();
            Data[] datas;
            Enemy[] enemies;
            int[] topEnemies;
            boolean inDanger = false;
            
            int numData = in.nextInt();
            datas = new Data[numData];
            for (int i = 0; i < numData; i++) {
                datas[i] = new Data(in.nextInt(), in.nextInt(), in.nextInt());
            }
            int numEnemy = in.nextInt();
            enemies = new Enemy[numEnemy+1];
            enemies[numEnemy] = new Enemy();
            // TODO: Consider full sorted enemies list for better detection
            topEnemies = new int[] {numEnemy, numEnemy, numEnemy};
            System.err.println(numEnemy);
            for (int i = 0; i < numEnemy; i++) {
                enemies[i] = new Enemy(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), datas, mx, my);
                if (distance(enemies[i].tsteps[0], mx, my) <= Enemy.KILL_DISTANCE) {
                    inDanger = true;
                }
                if (enemies[i].pscore > enemies[topEnemies[0]].pscore) {
                    topEnemies[2] = topEnemies[1];
                    topEnemies[1] = topEnemies[0];
                    topEnemies[0] = i;
                } else if (enemies[i].pscore > enemies[topEnemies[1]].pscore) {
                    topEnemies[2] = topEnemies[1];
                    topEnemies[1] = i;
                } else if (enemies[i].pscore > enemies[topEnemies[2]].pscore) {
                    topEnemies[2] = i;
                }
                
                for (int e = 0; e < enemies[i].tsteps.length; e++) {
                    System.err.print(enemies[i].tsteps[e][0] + "," + enemies[i].tsteps[e][1] + " ");
                }
                System.err.println("("+enemies[i].id+")");
            }
            
            if (inDanger) {
                int[] rxy = findRandomStepXY(mx, my, WOLFF_MOVE_DISTANCE, enemies, numEnemy);
                System.out.println("MOVE " + rxy[0] + " " + rxy[1]);
            } else {
                // TODO: Optimize and tune up enemy finding / shooting...
                //       Specifically heavy armored enemies close to targets (extremely high pscore?)
                // TODO: Case 2: Sequence of events move and shoot not always best consider in place........
                int tenemy = -1;
                int minShots = Integer.MAX_VALUE;
                int enemyTops = (numEnemy < topEnemies.length ? numEnemy : topEnemies.length) - 1;
                int sx = mx;
                int sy = my;
                for (int i = 0; i < MAX_FOLLOW_STEPS; i++) {
                    for (int e = 0; e <= enemyTops && e < numEnemy; e++) {
                        Enemy cenemy = enemies[topEnemies[e]];
                        int ehp = cenemy.hp;
                        for (int si = i; si < cenemy.tsteps.length; si++) {
                            int[] exy = cenemy.tsteps[si];
                            ehp -= Enemy.shootDamage(sx, sy, exy[0], exy[1]);
                            if (ehp <= 0/*cenemy.hp * MAX_HP_ALLOW_FACTOR*/ && (si - i + 1) < minShots) {
                                tenemy = cenemy.id;
                                minShots = (si - i + 1);
                                enemyTops = e;
                                break;
                            }
                        }
                        if (tenemy > -1)
                            break;
                    }
                    int[] topStep = enemies[topEnemies[0]].tsteps[0];
                    int[] topnsxy = nextStepXY(topStep[0], topStep[1], sx, sy, Enemy.MOVE_DISTANCE + 2);
                    int[] nsxy = nextStepXY(sx, sy, topnsxy[0], topnsxy[1], WOLFF_MOVE_DISTANCE);
                    if (sx == nsxy[0] && sy == nsxy[1]) {
                        break;
                    } else if (isSafeStep(nsxy[0], nsxy[1], enemies, numEnemy)) {
                        sx = nsxy[0];
                        sy = nsxy[1];
                    } else {
                        break;
                    }
                }
                
                if (tenemy > -1) {
                    if (sx == mx && sy == my) {
                        System.out.println("SHOOT " + tenemy);
                    } else {
                        System.out.println("MOVE " + sx + " " + sy);
                    }
                } else {
                    int[] rxy = findRandomStepXY(mx, my, WOLFF_MOVE_DISTANCE, enemies, numEnemy);
                    System.out.println("MOVE " + rxy[0] + " " + rxy[1]);
                }
            }
        }
    }
    
    static double distance(int x1, int y1, int x2, int y2, boolean decimal) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }
    
    static int distance(int x1, int y1, int x2, int y2) {
        return (int) distance(x1, y1, x2, y2, true);
    }
    static int distance(int[] xy1, int x2, int y2) {
        return distance(xy1[0], xy1[1], x2, y2);
    }
    
    static int[] nextStepXY(int x, int y, int tx, int ty, int moveDist) {
        double dist = distance(x, y, tx, ty, true);
        if (dist < moveDist)
            return new int[] {tx, ty};
        double delx = (double) ((tx - x) * moveDist) / dist;
        double dely = (double) ((ty - y) * moveDist) / dist;
        return new int[] {(int) (x + delx), (int) (y + dely)};
    }
    static boolean isSafeStep(int x, int y, Enemy[] enemies, int numEnemy) {
        for (int i = 0; i < numEnemy; i++) {
            if (distance(enemies[i].tsteps[0], x, y) <= Enemy.KILL_DISTANCE) {
                return false;
            }
        }
        return true;
    }
    static int[] findRandomStepXY(int x, int y, int moveDist, Enemy[] enemies, int numEnemy) {
        Random rand = new Random();
        int[] rxy = new int[2];
        while (true) {
            rxy[0] = rand.nextInt(MAP_WIDTH);
            rxy[1] = rand.nextInt(MAP_HEIGHT);
            int[] nxy = nextStepXY(x, y, rxy[0], rxy[1], moveDist);
            if (isSafeStep(nxy[0], nxy[1], enemies, numEnemy))
                return rxy;
        }
    }
    
    static class Enemy {
        public static final int MOVE_DISTANCE = 500;
        public static final int KILL_DISTANCE = 2000;
        public static final int MIN_PSCORE = -132147;
        
        int id;
        int x;
        int y;
        int hp;
        Data target;
        double tdist;
        int[][] tsteps;
        int pscore;
        
        public Enemy(int id, int x, int y, int hp, Data[] datas, int mx, int my) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.hp = hp;
            tdist = Double.MAX_VALUE;
            // OBSERVE: Targets at tdist == 0, proper handling
            for (int i = 0; i < datas.length; i++) {
                double dist = getDistance(datas[i]);
                if (dist < tdist && dist > 0) {
                    target = datas[i];
                    tdist = dist;
                }
            }
            tsteps = new int[(int) Math.ceil(tdist / MOVE_DISTANCE)][2];
            int posx = this.x;
            int posy = this.y;
            for (int step = 0; step < tsteps.length; step++) {
                int[] nxy = nextStepXY(posx, posy, target.x, target.y, MOVE_DISTANCE);
                posx = nxy[0];
                posy = nxy[1];
                tsteps[step] = nxy;
            }
            tsteps[tsteps.length-1][0] = target.x;
            tsteps[tsteps.length-1][1] = target.y;
            // TODO: Find optimal pscore alignment for most effective search (count steps not distance to mxy)
            pscore = hp - (int) Math.pow(tsteps.length, 2) - (int) Math.pow(getDistance(mx, my), 1.2);
        }
        public Enemy() {
            this.pscore = MIN_PSCORE;
        }
        public int shoot(int x, int y) {
            return hp - shootDamage(x, y, this.x, this.y);
        }
        public double getDistance(Data d) {
            return distance(this.x, this.y, d.x, d.y, true);
        }
        public int getDistance(int x, int y) {
            return distance(this.x, this.y, x, y);
        }
        public String toString() {
            return "/" + id + "\\(" + x + "," + ")";
        }
        
        public static int shootDamage(int x, int y, int ex, int ey) {
            double dist = distance(x, y, ex, ey, true);
            return (int) Math.round(125000.0 / Math.pow(dist, 1.2));
        }
    }
    
    static class Data {
        int id;
        int x;
        int y;
        
        public Data(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
        public String toString() {
            return "[(" + id + ")" + x + "," + y + "]";
        }
    }
}
