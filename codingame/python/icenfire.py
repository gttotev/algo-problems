import sys
import math
import random
random.seed()

X = 0
Y = 1
OWNER_ME = 0
G_OWNED = 'O'
G_ENEMY = 'X'
G_VOID = '#'
BU_OWNER = 2
B_TYPE = 3
B_TYPE_HQ = 0
B_TYPE_MINE = 1
B_TYPE_TOWER = 2
U_ID = 3
U_LVL = 4
CLOSE_WEIGHT = 1
ENEMY_WEIGHT = 4
B_TYPE_WEIGHT = [0, 6, 2, 0]
U_LVL_WEIGHT = [0, 2, 1, 0]
U_LVL_TRAIN_GOLD = [0, 10, 20, 30]
U_LVL_UPKEEP_GOLD = [0, 1, 4, 20]
U_LVL_MAX_TRAIN = [0, 10, 3, 2]

gmap = [None for i in range(12)]
bmap = None
umap = None
hq = None
visited = None
total_terr = 0
unit_count = 0
units = []
unit_counts_lvl = [0, 0, 0, 0]
mine_spots = []
tower_spots = []
mines = []
towers = []
gold = 0
income = 0

def cmpa(a):
    return visited[a[Y]][a[X]]
def cmpm(m):
    return abs(m[X] - hq[X]) + abs(m[Y] - hq[Y])

def can_move(x, y, u=(-1, -1, -2, -2, 4)):
    moves = []
    if (not is_invalid(x + 1, y, u)):
        moves.append((x + 1, y))
    if (not is_invalid(x - 1, y, u)):
        moves.append((x - 1, y))
    if (not is_invalid(x, y + 1, u)):
        moves.append((x, y + 1))
    if (not is_invalid(x, y - 1, u)):
        moves.append((x, y - 1))
    return moves

def is_invalid(x, y, u=(-1, -1, -2, -2, 4)):
    if (x < 0 or x > 11 or y < 0 or y > 11 or gmap[y][x] == G_VOID):
        return True
    if (u == 4):
        return False
    ul = u[U_LVL]
    uo = u[BU_OWNER]
    um = umap[y][x]
    uml = um[U_LVL]
    if (uo == bmap[y][x][BU_OWNER] or uo == um[BU_OWNER] or (uml >= ul and (not (uml == 3 and uml == ul)))):
        return True
    if (ul >= 3):
        return False
    tofo = [(x, y)] + can_move(x, y)
    tf = False
    for t in tofo:
        tx = t[X]
        ty = t[Y]
        if (gmap[ty][tx] == G_ENEMY and bmap[ty][tx][B_TYPE] == B_TYPE_TOWER):
            tf = True
            break
    return tf

def next_train_lvl():
    tmax = 0
    trec = 4
    for l in range(1, 4):
        if (gold >= U_LVL_TRAIN_GOLD[l] and income >= U_LVL_UPKEEP_GOLD[l]):
            tmax = l
            if (l < trec and unit_counts_lvl[l] < U_LVL_MAX_TRAIN[l]):
                trec = l
    return (tmax, trec)

def get_min_ud(x, y, excl):
    min_u = (-1, -1, -2, -1, 0)
    min_dist = 500
    for u in units:
        if (u in excl):
            continue
        dist = abs(x - u[X]) + abs(y - u[Y])
        if (dist == min_dist and u[U_LVL] > min_u[U_LVL]):
            min_u = u
        elif (dist < min_dist):
            min_dist = dist
            min_u = u
    return (min_u, min_dist)

def visit(x, y, is_close, acts, deff):
    visited[y][x] += 1
    is_not_mine = gmap[y][x] != G_OWNED
    is_enemy = gmap[y][x].upper() == G_ENEMY
    a = (x, y)
    if (is_not_mine and a not in acts):
        ul = umap[y][x][U_LVL]
        weight = B_TYPE_WEIGHT[bmap[y][x][B_TYPE] + 1] + U_LVL_WEIGHT[ul]
        weight += CLOSE_WEIGHT if is_close else 0
        weight += ENEMY_WEIGHT if is_enemy and ul == 0 else 0
        if (weight > 0):
            visited[y][x] += weight
            deff.discard(a)
            acts.add(a)
        else:
            deff.add(a)
        return
    if (is_not_mine or visited[y][x] > 1):
        return
    # my cell: visit recursively
    #total_terr += 1
    close = bmap[y][x][B_TYPE] == B_TYPE_HQ or umap[y][x][U_LVL] > 0
    moves = can_move(x, y)
    for m in moves:
        visit(m[X], m[Y], close, acts, deff)

mine_spot_count = int(input())
for i in range(mine_spot_count):
    x, y = [int(j) for j in input().split()]
    mine_spots.append((x, y))

for i in range(1, 6, 2):
    for j in range(1, 12, 2):
        t = (i, j)
        if (t not in mine_spots):
            tower_spots.append(t)
for i in range(6, 12, 2):
    for j in range(0, 12, 2):
        t = (i, j)
        if (t not in mine_spots):
            tower_spots.append(t)

# game loop
while True:
    gold = int(input())
    income = int(input())
    opponent_gold = int(input())
    opponent_income = int(input())
    outlist = ''

    for i in range(12):
        gmap[i] = list(input())

    bmap = [[(j, i, -1, -1) for j in range(12)] for i in range(12)]
    building_count = int(input())
    mines = []
    towers = []
    for i in range(building_count):
        owner, btype, x, y = [int(j) for j in input().split()]
        bmap[y][x] = (x, y, owner, btype)
        if (owner == OWNER_ME):
            if (btype == B_TYPE_HQ):
                hq = bmap[y][x]
            elif (btype == B_TYPE_MINE):
                mines.append(bmap[y][x])
            elif (btype == B_TYPE_TOWER):
                towers.append(bmap[y][x])

    umap = [[(j, i, -1, -1, 0) for j in range(12)] for i in range(12)]
    unit_count = int(input())
    unit_counts_lvl = [0, 0, 0, 0]
    units = []
    for i in range(unit_count):
        owner, id, level, x, y = [int(j) for j in input().split()]
        umap[y][x] = (x, y, owner, id, level)
        if (owner == OWNER_ME):
            units.append(umap[y][x])
            unit_counts_lvl[level] += 1

    for m in mine_spots:
        x = m[X]
        y = m[Y]
        cost = 20 + 4 * len(mines)
        if (gmap[y][x] == G_OWNED and bmap[y][x][B_TYPE] < 0 and gold >= cost + 20):
            u = umap[y][x]
            if (u[U_LVL] > 0):
                moves = can_move(x, y, u)
                if (len(moves) == 0 or u not in units):
                    continue
                units.remove(u)
                mvx = moves[0][X]
                mvy = moves[0][Y]
                outlist += f'MOVE {u[U_ID]} {mvx} {mvy};'
                gmap[mvy][mvx] = G_OWNED
                umap[y][x] = (x, y, -1, -1, 0)
                umap[mvy][mvx] = (mvx, mvy, OWNER_ME, u[U_ID], u[U_LVL])
                bmap[mvy][mvx] = (mvx, mvy, -1, -1)
            outlist += f'BUILD MINE {x} {y};'
            gold -= cost
            bmap[y][x] = (x, y, OWNER_ME, B_TYPE_MINE)
            mines.append(bmap[y][x])
    
    for t in tower_spots:
        x = t[X]
        y = t[Y]
        if (gmap[y][x] == G_OWNED and bmap[y][x][B_TYPE] < 0 and gold >= 35 and income > 5):
            u = umap[y][x]
            if (u[U_LVL] > 0):
                moves = can_move(x, y, u)
                if (len(moves) == 0 or u not in units):
                    continue
                units.remove(u)
                mvx = moves[0][X]
                mvy = moves[0][Y]
                outlist += f'MOVE {u[U_ID]} {mvx} {mvy};'
                gmap[mvy][mvx] = G_OWNED
                umap[y][x] = (x, y, -1, -1, 0)
                umap[mvy][mvx] = (mvx, mvy, OWNER_ME, u[U_ID], u[U_LVL])
                bmap[mvy][mvx] = (mvx, mvy, -1, -1)
            outlist += f'BUILD TOWER {x} {y};'
            gold -= 15
            bmap[y][x] = (x, y, OWNER_ME, B_TYPE_TOWER)
            towers.append(bmap[y][x])

    visited = [[0 for i in range(12)] for i in range(12)]
    total_terr = 0
    acts = set()
    deff = set()
    units.sort(key=cmpm, reverse=True)
    for u in units:
        x = u[X]
        y = u[Y]
        moves = can_move(x, y, u)
        #print(moves, file=sys.stderr)
        if (len(moves) > 0):
            moves.sort(key=cmpm)
            u_mov = moves[-1]
            for m in moves:
                if (gmap[m[Y]][m[X]] != G_OWNED):
                    u_mov = m
                    break
            mx = u_mov[X]
            my = u_mov[Y]
            outlist += f'MOVE {u[U_ID]} {mx} {my};'
            visited[my][mx] = 0
            gmap[my][mx] = G_OWNED
            bmap[my][mx] = (mx, my, -1, -1)
            umap[y][x] = (x, y, -1, -1, 0)
            umap[my][mx] = (mx, my, OWNER_ME, u[U_ID], u[U_LVL])
    visit(hq[X], hq[Y], False, acts, deff)
    while len(acts) + len(deff) > 0 and gold >= 10 and income >= 1:
        a = max(acts, key=cmpa) if len(acts) > 0 else max(deff, key=cmpa)
        x = a[X]
        y = a[Y]
        acts.discard(a)
        deff.discard(a)
        ntl = next_train_lvl()
        for l in range(1, ntl[0] + 1):
            tu = (x, y, OWNER_ME, -1, l)
            if (not is_invalid(x, y, tu)):
                if (unit_counts_lvl[l] < U_LVL_MAX_TRAIN[l]):
                    outlist += f'TRAIN {l} {x} {y};'
                    gold -= U_LVL_TRAIN_GOLD[l]
                    income -= U_LVL_UPKEEP_GOLD[l]
                    unit_counts_lvl[l] += 1
                    visited[y][x] = 0
                    gmap[y][x] = G_OWNED
                    bmap[y][x] = (x, y, -1, -1)
                    umap[y][x] = tu
                    visit(x, y, False, acts, deff)
                break
    
    # Write an action using print
    print(outlist if len(outlist) > 0 else 'WAIT')
