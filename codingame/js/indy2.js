// Read init information from standard input, if any
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

for (h = 0; h < height; h++) {
    var currentWidth = readline().split(' ')
        , currentRoomMap = []
        ;
    for (var w = 0; w < width; w++) {
        currentRoomMap.push(parseInt(currentWidth[w]));
    }
    roomMap.push(currentRoomMap);
}
var exitX = parseInt(readline())
    , exitY = parseInt(height - 1)
    , exitPlace = [exitX, exitY]
    ;

while (1) {
    // Read information from standard input INFO variables
    var indyLine = readline().split(' ')
        , indyX = parseInt(indyLine[0])
        , indyY = parseInt(indyLine[1])
        , indyRoomEnter = indyLine[2]
        , indyRoom = roomMap[indyY][indyX]
        , indyRoomAbs = Math.abs(roomMap[indyY][indyX])
        , indyRoomExit = roomDefs[indyRoomAbs][indyRoomEnter]
        , numRocks = parseInt(readline())
        , rocks = []
        ;
        for (var r = 0; r < numRocks; r++) {
            var currentRock = readline().split(' ');
            rocks.push(currentRock);
        }

    //Call pathFinder on first turn
    if (totalTurns == 0) {
        var movesToTop = pathFinder(exitX, exitY, indyX, indyY, roomMap[exitY][exitX], 'BOTTOM');
    }

    //Action Zone
    var outputValue = '';
    if (numTurns < movesToTop.length) {
        var indyNext = futureLoc(indyX, indyY, indyRoomExit)
            , indyNextX = indyNext[0]
            , indyNextY = indyNext[1]
            ;
            
        
        printErr('VALUES', indyNextX, indyNextY, movesToTop[numTurns][0], movesToTop[numTurns][1], indyRoomExit);
        printErr('vAlUeS', JSON.stringify(movesToTop));
        printErr('roomMap', roomMap[indyNextY][indyNextX]);
        
        if (indyNextX == movesToTop[numTurns][0] && indyNextY == movesToTop[numTurns][1]) {
            /*
            !!!!!!ATTENTION!!!!!!>Turning only when Indy right in front = problem!
            ******EXCEPTION******>What if two RIGHTs required and he's in front?
            !!!!!!ATTENTION!!!!!!>Only 1 per turn + no turning w/ Indy inside!
            */
            outputValue = movesToTop[numTurns][0] + ' ' + movesToTop[numTurns][1] + ' ' + movesToTop[numTurns][2];
            var turnDir = '';
            if (movesToTop[numTurns][2] == 'RIGHT') {
                turnDir = 'tRight';
            } else {
                turnDir = 'tLeft';
            }
            roomMap[indyNextY][indyNextX] = roomDefs[roomMap[indyNextY][indyNextX]][turnDir]; //ENDED HERE RECHECK!!!
            numTurns++;
        }
    }
    if (rocks.length != 0 && outputValue == '') {
        for (var r = 0; r < rocks.length; r++) {
            var currentRock = rocks[r]
                , rockX = parseInt(currentRock[0])
                , rockY = parseInt(currentRock[1])
                , currentCellTypeAbs = Math.abs(roomMap[rockY][rockX])
                , rockEnter = currentRock[2]
                , rockExit = roomDefs[currentCellTypeAbs][rockEnter]
                , nextRCell = futureLoc(rockX, rockY, rockExit)
                , nextRCellX = nextRCell[0]
                , nextRCellY = nextRCell[1]
                , nextRCellType = roomMap[nextRCellY][nextRCellX]
                ;
            
            if (nextRCellType > 0 && (nextRCellX != indyX || nextRCellY != indyY)) {
                outputValue = nextRCellX + ' ' + nextRCellY + ' ' + 'RIGHT';
                break;
            }
        }
    }
    if (outputValue == '') {
        outputValue = 'WAIT';
    }

    //PRINT ZONE!!!!!!!!!
    print(outputValue);
    totalTurns++;
    //printErr('Indy:', indyLine);
    //printErr('Room Map:', JSON.stringify(roomMap));
    //printErr('MovesToTop: ', JSON.stringify(movesToTop));
}

function futureLoc(cX, cY, rExit) {
    switch (rExit) {
        case 'BOTTOM':
            if (cY != height - 1)
                return [cX, cY + 1];
            break;
        case 'RIGHT':
            if (cX != width - 1)
                return [cX + 1, cY];
            break;
        case 'LEFT':
            if (cX != 0)
                return [cX - 1, cY];
            break;
    }
    return [];
} 

function pathFinder(fX, fY, tX, tY, roomType, exitDir) {
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

