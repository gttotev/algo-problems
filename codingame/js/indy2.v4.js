// Read init information from standard input, if any
//Global variables defs
var firstLine = readline().split(' ')
    , width = firstLine[0]
    , height = firstLine[1]
    , roomMap = []
    , roomDefs = {
        0: {'TOP': 'death', 'RIGHT': 'death', 'LEFT': 'death', 'tRight': 0, 'tLeft': 0},
        1: {'TOP': 'BOTTOM', 'RIGHT': 'BOTTOM', 'LEFT': 'BOTTOM', 'tRight': 1, 'tLeft': 1},
        2: {'TOP': 'death', 'RIGHT': 'LEFT', 'LEFT': 'RIGHT', 'tRight': 3, 'tLeft': 3},
        3: {'TOP': 'BOTTOM', 'RIGHT': 'death', 'LEFT': 'death', 'tRight': 2, 'tLeft': 2},
        4: {'TOP': 'LEFT', 'RIGHT': 'BOTTOM', 'LEFT': 'death', 'tRight': 5, 'tLeft': 5},
        5: {'TOP': 'RIGHT', 'RIGHT': 'death', 'LEFT': 'BOTTOM', 'tRight': 4, 'tLeft': 4},
        6: {'TOP': 'death', 'RIGHT': 'LEFT', 'LEFT': 'RIGHT', 'tRight': 7, 'tLeft': 9},
        7: {'TOP': 'BOTTOM', 'RIGHT': 'BOTTOM', 'LEFT': 'death', 'tRight': 8, 'tLeft': 6},
        8: {'TOP': 'death', 'RIGHT': 'BOTTOM', 'LEFT': 'BOTTOM', 'tRight': 9, 'tLeft': 7},
        9: {'TOP': 'BOTTOM', 'RIGHT': 'death', 'LEFT': 'BOTTOM', 'tRight': 6, 'tLeft': 8},
        10: {'TOP': 'LEFT', 'RIGHT': 'death', 'LEFT': 'death', 'tRight': 11, 'tLeft': 13},
        11: {'TOP': 'RIGHT', 'RIGHT': 'death', 'LEFT': 'death', 'tRight': 12, 'tLeft': 10},
        12: {'TOP': 'death', 'RIGHT': 'BOTTOM', 'LEFT': 'death', 'tRight': 13, 'tLeft': 11},
        13: {'TOP': 'death', 'RIGHT': 'death', 'LEFT': 'BOTTOM', 'tRight': 10, 'tLeft': 12}
    }
    , numTurns = 0
    , totalTurns = 0
    ;

//roomMap generation
for (h = 0; h < height; h++) {
    var currentWidth = readline().split(' ')
        , currentRoomMap = []
        ;
    for (var w = 0; w < width; w++) {
        currentRoomMap.push(parseInt(currentWidth[w]));
    }
    roomMap.push(currentRoomMap);
}
//More global variables
var exitX = parseInt(readline())
    , exitY = parseInt(height - 1)
    , exitPlace = [exitX, exitY]
    ;

while (1) {
    // Read information from standard input
    //VARIABLES for each turn!
    var indyLine = readline().split(' ')
        , indyX = parseInt(indyLine[0])
        , indyY = parseInt(indyLine[1])
        , indyRoomEnter = indyLine[2]
        , indyRoom = roomMap[indyY][indyX]
        , indyRoomAbs = Math.abs(indyRoom)
        , indyRoomExit = roomDefs[indyRoomAbs][indyRoomEnter]
        , numRocks = parseInt(readline())
        , rocks = []
        ;
        //rocks generation
        for (var r = 0; r < numRocks; r++) {
            var currentRock = readline().split(' ');
            rocks.push(currentRock);
        }
    //<------------------------------------------------------------------------------------------------->

    //Call pathFinder on first turn
    if (totalTurns == 0) {
        var movesToTop = pathFinder(exitX, exitY, indyX, indyY, roomMap[exitY][exitX], 'BOTTOM');
    }
    //<------------------------------------------------------------------------------------------------->

    //Action Zone
    var outputValue = '';
    if (numTurns < movesToTop.length) {  //Checks to see if hasn't turned all available
        //Finding Indy's next room
        var indyNext = futureLoc(indyX, indyY, indyRoomExit, 1);
            var indyNextX = indyNext[0]
            , indyNextY = indyNext[1]
            , indyNextRoom = roomMap[indyNextY][indyNextX]
            , indyNextRoomAbs = Math.abs(roomMap[indyNextY][indyNextX])
            //Finding Indy's room two moves ahead
            , indyNextNext = futureLoc(indyX, indyY, indyRoomExit, 2)
            , indyNextNextX = indyNextNext[0]
            , indyNextNextY = indyNextNext[1]
            ;
        //<------------------------------------------------------------------------------------------->
        
        //IF Indy's 2 ahead room has 2 turns
        if (
        (indyNextNextX == movesToTop[numTurns][0]/* && indyNextNextX == movesToTop[numTurns+1][0]*/)
        && 
        (indyNextNextY == movesToTop[numTurns][1]/* && indyNextNextY == movesToTop[numTurns+1][1]*/)
        ) {
            outputValue = movesToTop[numTurns][0] + ' ' + movesToTop[numTurns][1] + ' ' + movesToTop[numTurns][2];
            //Changes the room of turning in roomMap
            var turnDir = '';
            if (movesToTop[numTurns][2] == 'RIGHT') {
                turnDir = 'tRight';
            } else {
                turnDir = 'tLeft';
            }
            roomMap[indyNextNextY][indyNextNextX] = -(Math.abs(roomDefs[Math.abs(roomMap[indyNextNextY][indyNextNextX])][turnDir]));
            numTurns++;
        //<----------------------------------------------------------------------------------------------------->
        } else if ( (indyNextX == movesToTop[numTurns][0] && indyNextY == movesToTop[numTurns][1]) ) { //IF Indy's next room has turn OR 2 ahead has 2 turns 
            outputValue = movesToTop[numTurns][0] + ' ' + movesToTop[numTurns][1] + ' ' + movesToTop[numTurns][2];
            //Changes the room of turning in roomMap
            var turnDir = '';
            if (movesToTop[numTurns][2] == 'RIGHT') {
                turnDir = 'tRight';
            } else {
                turnDir = 'tLeft';
            }
            printErr('indy Stuff', indyNext, roomMap[indyNextY][indyNextX]);
            roomMap[indyNextY][indyNextX] = -(Math.abs(roomDefs[Math.abs(roomMap[indyNextY][indyNextX])][turnDir]));
            numTurns++;
        }
    }
    //<-------------------------------------------- ROCKS START ------------------------------------------------------------>
    if (rocks.length != 0 && outputValue == '') { //IF there's rocks AND no turn was made
        var rockTurn = false;
        for (var checkLength = 1; checkLength <= 2; checkLength++) {
            for (var r = 0; r < rocks.length; r++) {
                var currentRock = rocks[r]
                    , rockX = parseInt(currentRock[0])
                    , rockY = parseInt(currentRock[1])
                    , rockCellTypeAbs = Math.abs(roomMap[rockY][rockX])
                    , rockEnter = currentRock[2]
                    , rockExit = roomDefs[rockCellTypeAbs][rockEnter]
                    , nextRCell = futureLoc(rockX, rockY, rockExit, checkLength)
                    , nextRCellX = nextRCell[0]
                    , nextRCellY = nextRCell[1];
                    if (nextRCell.length == 0) {
                        rockTurn = true;
                        break;
                    }
                    var nextRCellType = roomMap[nextRCellY][nextRCellX]
                    , nextRCellTypeAbs = Math.abs(roomMap[nextRCellY][nextRCellX])
                    ;
                
                if (nextRCellType > 0 && (nextRCellX != indyX || nextRCellY != indyY)) { //IF the rocks next cell is not locked AND next cell isn't on Indy
                    outputValue = nextRCellX + ' ' + nextRCellY + ' ' + 'RIGHT';
                    roomMap[nextRCellY][nextRCellX] = -(roomDefs[roomMap[nextRCellY][nextRCellX]]['tRight']);
                    rockTurn = true;
                    break;
                }
            }
            //Rocks loop BREAK zone
            if (rockTurn)
                break;
        }
    }
    //<-------------------------------------------------- WAIT LOGIC ------------------------------------------------------->
    if (outputValue == '') { //IF nothing else WAIT
        outputValue = 'WAIT';
    }

    //PRINT ZONE (turn the actual room increase totalTurns)
    print(outputValue);
    totalTurns++;
    
    //printErr('Indy:', indyLine);
    //printErr('Room Map:', JSON.stringify(roomMap));
    //printErr('MovesToTop: ', JSON.stringify(movesToTop));
}

function futureLoc(cX, cY, rExit, checkLength) { //Calculates the next cell to checkLength for (cX, cY)
    var eX = 0
        , eY = 0
        ;
    
    for (var i = 1; i <= checkLength; i++) {
        var nextCellEnter = '';
        
        switch (rExit) {
            case 'BOTTOM':
                if (cY != height - 1) {
                    eX = cX;
                    eY = cY + 1;
                    nextCellEnter = 'TOP';
                }
                break;
            case 'RIGHT':
                if (cX != width - 1) {
                    eX = cX + 1;
                    eY = cY;
                    nextCellEnter = 'LEFT';
                }
                break;
            case 'LEFT':
                if (cX != 0) {
                    eX = cX - 1;
                    eY = cY;
                    nextCellEnter = 'RIGHT';
                }
                break;
        }
        if (nextCellEnter == '') {
            return [];
        }
        var nextCellTypeAbs = Math.abs(roomMap[eY][eX]);
        cX = eX;
        cY = eY;
        rExit = roomDefs[nextCellTypeAbs][nextCellEnter];
    }
    return [eX, eY];
}

function pathFinder(fX, fY, tX, tY, roomType, exitDir) { //Finds the turns to be made in the map (bottom up)
    var retValue = '';
    if (roomType == 0) {
        return '';
    }
    if (fX == tX && fY == tY) {
        return [];
    } else {
        var newRoomType = roomType;
        for (var i = 0; i <= 3; i++) {
            //printErr('fX', fX, 'fY', fY, 'i', i, 'exitDir', exitDir, 'roomType', newRoomType);
            if (fY != 0 && roomDefs[Math.abs(newRoomType)]['TOP'] == exitDir) {
                retValue = pathFinder (fX, fY - 1, tX, tY, roomMap[fY - 1][fX], 'BOTTOM');
            }
            if (fX != (width - 1) && roomDefs[Math.abs(newRoomType)]['RIGHT'] == exitDir && typeof retValue == 'string') {
                retValue = pathFinder (fX + 1, fY, tX, tY, roomMap[fY][fX + 1], 'LEFT');
            }
            if (fX != 0 && roomDefs[Math.abs(newRoomType)]['LEFT'] == exitDir && typeof retValue == 'string') {
                retValue = pathFinder (fX - 1, fY, tX, tY, roomMap[fY][fX - 1], 'RIGHT');
            }
            if (Math.abs(roomType) != roomType) {
                //printErr('LOCKED', fX, fY);
                break;
            }
            newRoomType = roomDefs[newRoomType]['tRight'];
            if (typeof retValue != 'string') {
                if (i == 1) {
                    retValue.push([fX, fY, 'RIGHT']);
                } else if (i == 2) {
                    retValue.push([fX, fY, 'RIGHT']);
                    retValue.push([fX, fY, 'RIGHT']);
                } else if (i == 3) {
                    retValue.push([fX, fY, 'LEFT']);
                }
                break;
            }
        }
        return retValue;
    }
}

