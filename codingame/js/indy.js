// Read init information from standard input, if any
var firstLine = readline().split(' ')
    , width = firstLine[0]
    , height = firstLine[1]
    , roomMap = []
    , roomDefs = {
        0: {'TOP': 'death', 'RIGHT': 'death', 'LEFT': 'death'},
        1: {'TOP': 'BOTTOM', 'RIGHT': 'BOTTOM', 'LEFT': 'BOTTOM'},
        2: {'TOP': 'death', 'RIGHT': 'LEFT', 'LEFT': 'RIGHT'},
        3: {'TOP': 'BOTTOM', 'RIGHT': 'death', 'LEFT': 'death'},
        4: {'TOP': 'LEFT', 'RIGHT': 'BOTTOM', 'LEFT': 'death'},
        5: {'TOP': 'RIGHT', 'RIGHT': 'death', 'LEFT': 'BOTTOM'},
        6: {'TOP': 'death', 'RIGHT': 'LEFT', 'LEFT': 'RIGHT'},
        7: {'TOP': 'BOTTOM', 'RIGHT': 'BOTTOM', 'LEFT': 'death'},
        8: {'TOP': 'death', 'RIGHT': 'BOTTOM', 'LEFT': 'BOTTOM'},
        9: {'TOP': 'BOTTOM', 'RIGHT': 'death', 'LEFT': 'BOTTOM'},
        10: {'TOP': 'LEFT', 'RIGHT': 'death', 'LEFT': 'death'},
        11: {'TOP': 'RIGHT', 'RIGHT': 'death', 'LEFT': 'death'},
        12: {'TOP': 'death', 'RIGHT': 'BOTTOM', 'LEFT': 'death'},
        13: {'TOP': 'death', 'RIGHT': 'death', 'LEFT': 'BOTTOM'}
    }
    ;

for (h = 0; h < height; h++) {
    var currentWidth = readline().split(' ')
        , currentRoomMap = []
        ;
    for (var w = 0; w < width; w++) {
        currentRoomMap.push(currentWidth[w]);
    }
    roomMap.push(currentRoomMap);
}
var exitPlace = readline();

while (1) {
    // Read information from standard input
    var indyLine = readline().split(' ')
        , indyX = parseInt(indyLine[0])
        , indyY = parseInt(indyLine[1])
        , indyRoomEnter = indyLine[2]
        , indyRoom = roomMap[indyY][indyX]
        , indyRoomExit = roomDefs[indyRoom][indyRoomEnter]
        , indyNextRoom = []
        ;

    // Compute logic here
    if (indyRoomExit == 'death') {
        break;
    }
    switch (indyRoomExit) {
        case 'BOTTOM':
            indyNextRoom.push(indyX)
            indyNextRoom.push(indyY + 1)
            break;
        case 'RIGHT':
            indyNextRoom.push(indyX + 1)
            indyNextRoom.push(indyY)
            break;
        case 'LEFT':
            indyNextRoom.push(indyX - 1)
            indyNextRoom.push(indyY)
            break;
    }
    print(indyNextRoom[0], indyNextRoom[1]);

    printErr('Indy:', indyLine);
    printErr('Room Map:', JSON.stringify(roomMap));
}

