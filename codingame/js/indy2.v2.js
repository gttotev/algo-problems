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
    , doubleTurn = false
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
        , indyRoomEnter = indyLine[2];
        printErr('problem room', roomMap[3][1]);
        var indyRoom = roomMap[indyY][indyX]
        , indyRoomAbs = Math.abs(indyRoom);
        printErr('indyRoom + abs, indyRoomEnter', indyRoom, indyRoomAbs, indyRoomEnter);
        var indyRoomExit = roomDefs[indyRoomAbs][indyRoomEnter]
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
        var indyNext = futureLoc(indyX, indyY, indyRoomExit);
        printErr('indy next, indy, roomExit', JSON.stringify(indyNext), indyX, indyY, indyRoomExit);
            var indyNextX = indyNext[0]
            , indyNextY = indyNext[1]
            , indyNextRoom = roomMap[indyNextY][indyNextX]
            , indyNextRoomAbs = Math.abs(roomMap[indyNextY][indyNextX])
            , indyNextRoomEnter = '';
            if (indyRoomExit == 'TOP')
                indyNextRoomEnter = 'BOTTOM';
            else if (indyRoomExit == 'RIGHT') 
                indyNextRoomEnter = 'LEFT';
            else if (indyRoomExit == 'LEFT')
                indyNextRoomEnter = 'RIGHT';
            
            var indyNextRoomExit = roomDefs[indyNextRoomAbs][indyNextRoomEnter]
            //Finding Indy's room two moves ahead
            , indyNextNext = futureLoc(indyNextX, indyNextY, indyNextRoomExit)
            , indyNextNextX = indyNextNext[0]
            , indyNextNextY = indyNextNext[1]
            ;
            //<------------------------------------------------------------------------------------------->
        
        //printErr time!! DEBUG
        printErr('VALUES', indyNextX, indyNextY, movesToTop[numTurns][0], movesToTop[numTurns][1], indyRoomExit);
        //printErr('vAlUeS', JSON.stringify(movesToTop));
        printErr('NextNext + enter', indyNextNext, indyNextRoomEnter);
        printErr('roomMap', JSON.stringify(roomMap));
        //<----------------------------------------------------------------------------------------------->
        
        //IF Indy's 2 ahead room has 2 turns
        if (
        (indyNextNextX == movesToTop[numTurns][0]/* && indyNextNextX == movesToTop[numTurns+1][0]*/)
        && 
        (indyNextNextY == movesToTop[numTurns][1]/* && indyNextNextY == movesToTop[numTurns+1][1]*/)
        ) {
            printErr('#1');
            outputValue = movesToTop[numTurns][0] + ' ' + movesToTop[numTurns][1] + ' ' + movesToTop[numTurns][2];
            //Changes the room of turning in roomMap
            var turnDir = '';
            if (movesToTop[numTurns][2] == 'RIGHT') {
                turnDir = 'tRight';
            } else {
                turnDir = 'tLeft';
            }
            //roomMap[indyNextY][indyNextX] = roomDefs[roomMap[indyNextY][indyNextX]]['RIGHT'];
            roomMap[indyNextNextY][indyNextNextX] = roomDefs[roomMap[indyNextNextY][indyNextNextX]][turnDir];
            printErr('turnDir, problem square', turnDir, roomMap[2][6]);
            numTurns++;
            /*if (indyNextNextX == movesToTop[numTurns+1][0] && indyNextNextY == movesToTop[numTurns+1][1])
                doubleTurn = true;*/
        } else if ( (indyNextX == movesToTop[numTurns][0] && indyNextY == movesToTop[numTurns][1]) || doubleTurn ) { //IF Indy's next room has turn OR 2 ahead has 2 turns 
            /*
            <!!!!!!ATTENTION!!!!!!>  Turning only when Indy right in front = problem!  <!!!!!!ATTENTION!!!!!!>
            <******EXCEPTION******>  What if two RIGHTs required and he's in front?  <******EXCEPTION******>
            <!!!!!!ATTENTION!!!!!!>  Only 1 per turn + no turning w/ Indy inside!  <!!!!!!ATTENTION!!!!!!>
            */
            printErr('#2');
            outputValue = movesToTop[numTurns][0] + ' ' + movesToTop[numTurns][1] + ' ' + movesToTop[numTurns][2];
            //Changes the room of turning in roomMap
            var turnDir = '';
            if (movesToTop[numTurns][2] == 'RIGHT') {
                turnDir = 'tRight';
            } else {
                turnDir = 'tLeft';
            }
            roomMap[indyNextY][indyNextX] = roomDefs[roomMap[indyNextY][indyNextX]][turnDir];
            //roomMap[indyNextY][indyNextX] = roomDefs[roomMap[indyNextY][indyNextX]]['RIGHT'];
            numTurns++;
            doubleTurn = false;
        }
    }
    if (rocks.length != 0 && outputValue == '') { //IF there's rocks AND no turn will be made
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
            
            if (nextRCellType > 0 && (nextRCellX != indyX || nextRCellY != indyY)) { //IF the rocks next cell is not locked AND next cell isn't on Indy
            printErr('#3');
                outputValue = nextRCellX + ' ' + nextRCellY + ' ' + 'RIGHT';
                break;
            }
        }
    }
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

function futureLoc(cX, cY, rExit) { //Calculates the next cell for (cX, cY)
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

