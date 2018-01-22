// Read init information from standard input, if any
var firstLine = readline().split(' ')
    , width = parseInt(firstLine[0])
    , height = parseInt(firstLine[1])
    , maxJumps = parseInt(readline())
    , batman = readline().split(' ')
    , batmanX = parseInt(batman[0])
    , batmanY = parseInt(batman[1])
    , bombArea = {
        UL: [0,0],
        DL: [0, height - 1],
        DR: [width - 1, height - 1],
        UR: [width - 1, 0]
    }
    ;

while (1) {
    // Read information from standard input
    var bombDir = readline()
        , newBombArea = {}
        , roundDirX = ''
        , roundDirY = ''
        ;

    // Compute logic here
    switch (bombDir) {
        case 'U':
            newBombArea = {            
                UL: [batmanX, bombArea['UL'][1]],
                DL: [batmanX, batmanY],
                DR: [batmanX, batmanY],
                UR: [batmanX, bombArea['UR'][1]]
            };
            roundDirX = '';
            roundDirY = 'floor';
            break;
        case 'UR':
            newBombArea = {            
                UL: [batmanX, bombArea['UL'][1]],
                DL: [batmanX, batmanY],
                DR: [bombArea['DR'][0], batmanY],
                UR: [bombArea['UR'][0], bombArea['UR'][1]]
            };
            roundDirX = 'ceil';
            roundDirY = 'floor';
            break;
        case 'R':
            newBombArea = {            
                UL: [batmanX, batmanY],
                DL: [batmanX, batmanY],
                DR: [bombArea['DR'][0], batmanY],
                UR: [bombArea['UR'][0], batmanY]
            };
            roundDirX = 'ceil';
            roundDirY = '';
            break;
        case 'DR':
            newBombArea = {            
                UL: [batmanX, batmanY],
                DL: [batmanX, bombArea['DL'][1]],
                DR: [bombArea['DR'][0], bombArea['DR'][1]],
                UR: [bombArea['UR'][0], batmanY]
            };
            roundDirX = 'ceil';
            roundDirY = 'ceil';
            break;
        case 'D':
            newBombArea = {            
                UL: [batmanX, batmanY],
                DL: [batmanX, bombArea['DL'][1]],
                DR: [batmanX, bombArea['DR'][1]],
                UR: [batmanX, batmanY]
            };
            roundDirX = '';
            roundDirY = 'ceil';
            break;
        case 'DL':
            newBombArea = {            
                UL: [bombArea['UL'][0], batmanY],
                DL: [bombArea['DL'][0], bombArea['DL'][1]],
                DR: [batmanX, bombArea['DR'][1]],
                UR: [batmanX, batmanY]
            }
            roundDirX = 'floor';
            roundDirY = 'ceil';
            break;
        case 'L':
            newBombArea = {            
                UL: [bombArea['UL'][0], batmanY],
                DL: [bombArea['DL'][0], batmanY],
                DR: [batmanX, batmanY],
                UR: [batmanX, batmanY]
            }
            roundDirX = 'floor';
            roundDirY = '';
            break;
        case 'UL':
            newBombArea = {            
                UL: [bombArea['UL'][0], bombArea['UL'][1]],
                DL: [bombArea['DL'][0], batmanY],
                DR: [batmanX, batmanY],
                UR: [batmanX, bombArea['UR'][1]]
            }
            roundDirX = 'floor';
            roundDirY = 'floor';
            break;
    }
    bombArea = newBombArea;
    var xMiddle = bombArea['UL'][0] + (bombArea['UR'][0] - bombArea['UL'][0]) / 2
        , yMiddle = bombArea['UL'][1] + (bombArea['DL'][1] - bombArea['UL'][1]) / 2
        ;
    
    if (roundDirX == 'floor') {
        xMiddle = Math.floor(xMiddle);
    } else if (roundDirX == 'ceil') {
        xMiddle = Math.ceil(xMiddle);
    }
    if (roundDirY == 'floor') {
        yMiddle = Math.floor(yMiddle);
    } else if (roundDirY == 'ceil') {
        yMiddle = Math.ceil(yMiddle);
    }
    print(xMiddle, yMiddle);
    batmanX = xMiddle;
    batmanY = yMiddle;

    // printErr("Debug messages...");
}

