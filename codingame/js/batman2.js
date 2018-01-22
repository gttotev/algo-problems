// Read init information from standard input, if any
var firstLine = readline().split(' ')
    , width = parseInt(firstLine[0])
    , height = parseInt(firstLine[1])
    , maxJumps = parseInt(readline())
    , batman = readline().split(' ')
    , batmanX = parseInt(batman[0])
    , batmanY = parseInt(batman[1])
    , oldBatmanX = 0
    , oldBatmanY = 0
    , windows = []
    , bombWindows = []
    , first = true
    ;

while (1) {
    // Read information from standard input
    var bombInfo = readline()
        , newBombArea = {}
        , jumpToX = 0
        , jumpToY = 0
        ;

    // Compute logic here
    if (bombInfo == 'UNKNOWN') {
        jumpToX = Math.floor(Math.random() * width);
        jumpToY = Math.floor(Math.random() * height);
        oldBatmanX = batmanX;
        oldBatmanY = batmanY;
        batmanX = jumpToX;
        batmanY = jumpToY;
    } else {
        if (first) {
            first = false;
            for (var x = 0; x < width; x++) {
                for (var y = 0; y < height; y++) {
                    var oldDistance = Math.sqrt(Math.pow(oldBatmanX - x, 2) + Math.pow(oldBatmanY - y, 2));
                    var newDistance = Math.sqrt(Math.pow(batmanX - x, 2) + Math.pow(batmanY - y, 2));
                    //printErr(oldDistance, newDistance);
                    switch (bombInfo) {
                        case 'COLDER':
                            if (oldDistance < newDistance) {
                                windows.push([x, y]);
                            } else {
                                printErr('COLDER', x, y);
                            }
                            break;
                        case 'WARMER':
                            if (oldDistance > newDistance) {
                                windows.push([x, y]);
                            } else {
                                printErr('WARMER', x, y);
                            }
                            break;
                        case 'SAME':
                            if (oldDistance == newDistance) {
                                windows.push([x, y]);
                            } else {
                                printErr('SAME', x, y);
                            }
                            break;
                    }
                }
            }
        } else {
            bombWindows = [];
            for (var i = 0; i < windows.length; i++) {
                var oldDistance = Math.sqrt(Math.pow(oldBatmanX - windows[i][0], 2) + Math.pow(oldBatmanY - windows[i][1], 2));
                var newDistance = Math.sqrt(Math.pow(batmanX - windows[i][0], 2) + Math.pow(batmanY - windows[i][1], 2));
                switch (bombInfo) {
                    case 'COLDER':
                        if (oldDistance < newDistance) {
                            bombWindows.push(windows[i]);
                        //    i--;
                        }
                        break;
                    case 'WARMER':
                        if (oldDistance > newDistance) {
                            bombWindows.push(windows[i]);
                        //    i--;
                        }
                        break;
                    case 'SAME':
                        if (oldDistance == newDistance) {
                            bombWindows.push(windows[i]);
                        //    i--;
                        }
                        break;
                }
            }
            windows = bombWindows;
        }
        var randWindow = Math.floor(Math.random() * windows.length);
        oldBatmanX = batmanX;
        oldBatmanY = batmanY;
        batmanX = windows[randWindow][0];
        batmanY = windows[randWindow][1];
    }
    print(batmanX, batmanY);

    printErr(JSON.stringify(windows));
}

