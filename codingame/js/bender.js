// Read inputs from Standard Input (use readline()).
// Write outputs to Standard Output (use print()).

var firstLine = readline().split(' ')
    , numRows = firstLine[0]
    , numColumns = firstLine[1]
    , cityMap = []
    , normPriorities = ['SOUTH', 'EAST', 'NORTH', 'WEST']
    , invPriorities = ['WEST', 'NORTH', 'EAST', 'SOUTH']
    , iTest = 0
    , moveCounter = 0
    , bender
    , alive = true
    , moveDirection = 'SOUTH'
    , moveList = ''
    , tps = []
    , invert = false
    , beer = false
    ;

for (var i = 0; i < numRows; i++) {
    var currentRow = readline().split('');
    cityMap.push(currentRow);
    if (typeof bender == 'undefined') {
        if (currentRow.indexOf('@') != -1) {
            bender = [currentRow.indexOf('@'), i];
        }
    }
    if (tps.length < 2) {
        if (currentRow.indexOf('T') != -1) {
            tps.push([currentRow.indexOf('T'), i]);
        }
    }
}
//Created cityMap, bender, and tps variables

while (alive && moveCounter <= 750000) {
    var movePlace
        , x = bender[0]
        , y = bender[1]
        ;
    
    switch (moveDirection) {
        case 'SOUTH':
            y ++;
            break;
        case 'EAST':
            x ++;
            break;
        case 'NORTH':
            y --;
            break;
        case 'WEST':
            x --;
            break;
    }
    movePlace = cityMap[y][x];
    //Determined where Bender will move to
    if (movePlace == '#' || movePlace == 'X') {
        if (movePlace == 'X' && beer == true) {
            cityMap[y][x] = ' ';
            //Bender breaks the X obstacle
        } else {
            moveDirection = (invert == false ? normPriorities[iTest] : invPriorities[iTest]);
            iTest ++;
            continue;
            //Bender changes direction trying S, E, N, W
        }
    }
    iTest = 0;
    //Determined that there's a valid space to move in
    moveList += (moveDirection + '\n');
    bender[0] = x;
    bender[1] = y;
    moveCounter ++;
    //Moved Bender
    switch (cityMap[bender[1]][bender[0]]) {
        case 'E':
            moveDirection = 'EAST';
            break;
        case 'N':
            moveDirection = 'NORTH';
            break;
        case 'W':
            moveDirection = 'WEST';
            break;
        case 'S':
            moveDirection = 'SOUTH';
            break;
        case 'I':
            invert = !invert;
            break
        case 'B':
            beer = !beer;
            break;
        case 'T':
            if (bender[0] == tps[0][0] && bender[1] == tps[0][1]) {
                bender[0] = tps[1][0];
                bender[1] = tps[1][1];
            } else {
                bender[0] = tps[0][0];
                bender[1] = tps[0][1];
            }
            break;
        case '$':
            alive = false;
            break;
        case ' ':
        case '@':
            break;
        default: 
            print('Missed map marker:', cityMap[bender[1]][bender[0]]);
    }
    //Checked everything Bender might be on and acted accordingly
}

/*print('City map', JSON.stringify(cityMap));
print('Bender location:', bender);
print('Teleporters:', JSON.stringify(tps));*/

if (moveCounter >= 750000) {
    print('LOOP');
} else {
    print(moveList);
}

