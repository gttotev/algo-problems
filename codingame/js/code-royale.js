'use strict';
const NEUTRAL = -1;
const FRIEND = 0;
const ENEMY = 1;

const MINE = 0;
const TOWER = 1;
const BARRACKS = 2;

const QUEEN = -1;
const KNIGHT = 0;
const ARCHER = 1;
const GIANT = 2;

const SITE_STR = ['MINE', 'TOWER', 'BARRACKS-KNIGHT', 'BARRACKS-ARCHER', 'BARRACKS-GIANT'];
const TOWER_MAX_RADIUS = 510;

const INF_DIST = 2200;
const RETURN_DIST = 250;
const DANGER_DIST = 100;
const Q1_DIST = 480;
const MID_DIST = 960;
const Q3_DIST = 1440;

function readLine() { return readline(); }
function cmpDist(a, b) { return Math.abs(sites[b].x - mside) - Math.abs(sites[a].x - mside); }

function Site(x, y, r) {
    this.x = x;
    this.y = y;
    this.radius = r;
    this.setParams = function(i1, i2, t, o, p1, p2) {
        this.gold = i1;
        this.maxRate = i2;
        this.type = t;
        this.owner = o;
        this.p1 = p1;
        this.p2 = p2;
    };
}

function Unit(x, y, o, t, h) {
    this.x = x;
    this.y = y;
    this.owner = o;
    this.type = t;
    this.h = h;
}

function dist(x1, y1, x2, y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
}

function edist(e1, e2) {
    return dist(e1.x, e1.y, e2.x, e2.y);
}

function distTo(entity) {
    return dist(mx, my, entity.x, entity.y);
}

function findNearest(entities, keys) {
    let minSite = NEUTRAL;
    let minDist = INF_DIST;
    let entries = (keys ? keys : Object.keys(entities)/*Array.from({length: entities.length}, (v, i) => i)*/);
    for (let i of entries) {
        let s = entities[i];
        let d = distTo(s);
        if (d < minDist) {
            minSite = i;
            minDist = d;
        }
    }
    return minSite;
}

function findNearSite() { // RETURNS siteId
    let minSite = NEUTRAL;
    let minDist = INF_DIST;
    for (let i = 0; i < numSites; i++) {
        if (i == blockSite) continue;
        let s = sites[i];
        let d = distTo(s);
        if (s.owner != FRIEND && d < minDist) {
            minSite = i;
            minDist = d;
        }
    }
    return minSite;
}

function findStrategicBar() { // Implement sorting and ranking system (calculate whether queen will reach)
    let minBar = NEUTRAL;
    let minDist = INF_DIST;
    for (let b of barracks) {
        let bar = sites[b];
        let d = edist(bar, units[equeen]);
        if (bar.p2 == KNIGHT && d < minDist) {
            minBar = b;
            minDist = d;
        }
    }
    return minBar;
}

var numSites = parseInt(readLine()), numUnits, inputs;
var sites = [], units = [];
var barracks = [], archerBars = [], knightBars = [];
var towers = [];
var myHalfSites = [];
var mx, my, mhealth, mside = NEUTRAL;
var gold, touchedSite;
var numMines, numArchers;
var maxMines;
var near, blockSite = -1;
var lastBuild = NEUTRAL, lastBtype;
var danger, knightDist, touchedDanger;
var equeen, numEKnights;
for (let i = 0; i < numSites; i++) {
    inputs = readLine().split(' ');
    let siteId = parseInt(inputs[0]);
    let x = parseInt(inputs[1]);
    let y = parseInt(inputs[2]);
    let radius = parseInt(inputs[3]);
    sites[i] = new Site(x, y, radius);
}

// game loop
while (true) {
    inputs = readLine().split(' ');
    gold = parseInt(inputs[0]);
    touchedSite = parseInt(inputs[1]); // -1 if none
    barracks = [];
    archerBars = [];
    numArchers = 0;
    knightBars = [];
    towers = [];
    numMines = 0;
    let mth = Infinity;
    let minTower = NEUTRAL;
    for (let i = 0; i < numSites; i++) { // SITES
        inputs = readLine().split(' ');
        let siteId = parseInt(inputs[0]);
        let ignore1 = parseInt(inputs[1]); // used in future leagues
        let ignore2 = parseInt(inputs[2]); // used in future leagues
        let type = parseInt(inputs[3]); // -1 = No structure, 2 = Barracks
        let owner = parseInt(inputs[4]); // -1 = No structure, 0 = Friendly, 1 = Enemy
        let param1 = parseInt(inputs[5]);
        let param2 = parseInt(inputs[6]);
        if (owner == FRIEND) {
            if (type == BARRACKS) {
                barracks.push(siteId);
                if (param2 == ARCHER) {
                    archerBars.push(siteId);
                    if (param1 > 0) numArchers += 2;
                } else if (param2 == KNIGHT) {
                    knightBars.push(siteId);
                }
            } else if (type == TOWER) {
                towers.push(siteId);
                if (param1 < mth) {
                    mth = param1;
                    minTower = siteId;
                }
            } else if (type == MINE) {
                numMines++;
            }
        }
        sites[siteId].setParams(ignore1, ignore2, type, owner, param1, param2);
    }
    numUnits = parseInt(readLine());
    units = [];
    numEKnights = 0;
    knightDist = INF_DIST;
    touchedDanger = false;
    for (let i = 0; i < numUnits; i++) { // UNITS
        inputs = readLine().split(' ');
        let x = parseInt(inputs[0]);
        let y = parseInt(inputs[1]);
        let owner = parseInt(inputs[2]);
        let unitType = parseInt(inputs[3]); // -1 = QUEEN, 0 = KNIGHT, 1 = ARCHER
        let health = parseInt(inputs[4]);
        if (owner == ENEMY) {
            if (unitType == KNIGHT || unitType == GIANT) {
                numEKnights++;
                touchedDanger = dist(mx, my, x, y) < 80;
                let kdist = Math.abs(x - mside);
                if (kdist < knightDist) knightDist = kdist;
            } else if (unitType == QUEEN) {
                equeen = i;
            }
        } else {
            if (unitType == QUEEN) {
                if (mx == x && my == y) {
                    blockSite = near;
                } else {
                    blockSite = -1;
                }
                mx = x;
                my = y;
                mhealth = health;
            } else if (unitType == ARCHER) {
                numArchers++;
            }
        }
        units[i] = new Unit(x, y, owner, unitType, health);
    }
    // Set up myHalfSites -- seed=327095553,630989280
    if (mside == NEUTRAL) { // TODO: Adapt based on site count
        mside = (mx < MID_DIST ? 0 : 1920);
        for (let i = 0; i < numSites; i++)
            if (Math.abs(mside - sites[i].x) < MID_DIST) myHalfSites.push(i);
        myHalfSites.sort(cmpDist);
        maxMines = myHalfSites.length - 4;
        printErr(JSON.stringify(myHalfSites));
    }
    
    // BUILD instructions
    if (barracks.length < 2) {
        lastBuild = findNearSite();
        lastBtype = SITE_STR[BARRACKS + (archerBars.length < 1 ? ARCHER : KNIGHT)];
    } else if (lastBuild == NEUTRAL) {
        if (touchedDanger || (knightDist < MID_DIST - 100 && (towers.length < 2 || numArchers < 2))) {
            let closeTower = findNearest(sites, towers);
            while (closeTower > NEUTRAL && sites[closeTower].p2 === TOWER_MAX_RADIUS) {
                towers.splice(towers.indexOf(closeTower), 1);
                closeTower = findNearest(sites, towers);
            }
            lastBuild = findNearSite();
            if (closeTower > NEUTRAL && distTo(sites[closeTower]) < distTo(sites[lastBuild]))
                lastBuild = closeTower;
            lastBtype = SITE_STR[TOWER];
        } else {
            let minDist = INF_DIST;
            for (let i = 2; i < myHalfSites.length; i++) {
                let st = sites[myHalfSites[i]];
                let d = distTo(st);
                //printErr(d + ' ' + JSON.stringify(st));
                if (d < minDist && (st.owner != FRIEND || st.type == MINE)
                    && (st.maxRate < 0 || st.p1 < st.maxRate)) {
                    minDist = d;
                    lastBuild = myHalfSites[i];
                }
                //printErr(lastBuild);
            }
            //if (lastBuild == NEUTRAL) lastBuild = findNearest(sites, towers);
            //printErr(lastBuild + ' ' + sites[lastBuild]);
            lastBtype = SITE_STR[lastBuild > NEUTRAL && !sites[lastBuild].gold ? TOWER : MINE];
        }
        if (lastBuild == NEUTRAL) {
            lastBuild = (minTower == NEUTRAL ? findNearSite() : minTower);
            lastBtype = SITE_STR[TOWER];
        }
    }
    print('BUILD ' + lastBuild + ' ' + lastBtype);
    printErr(JSON.stringify(sites[lastBuild]));
    if (touchedSite == lastBuild) lastBuild = NEUTRAL;
    
    // TRAIN instructions
    let train = [];
    let stratBar = findStrategicBar();
    if (stratBar > NEUTRAL && gold >= (knightDist < Q3_DIST ? 180 : 80)) {
        train.push(stratBar);
        gold -= 80;
    }
    if (knightDist < Q3_DIST) {
        for (let b of archerBars) {
            if (numArchers >= 6 || gold < 100) break;
            if (sites[b].p1 === 0) {
                train.push(b);
                gold -= 100;
                numArchers += 2;
            }
        }
    }
    print('TRAIN' + (train.length > 0 ? ' ' + train.join(' ') : ''));
}
