// Read init information from standard input, if any

while (1) {
    // Read information from standard input
    var firstLine = readline().split(' ')
        , numPlayers = firstLine[0]
        , myID = firstLine[1]
        , playerCoords = []
        ;
    
    for (var i = 0; i < numPlayers; i++) {
        var currentCoords = readline().split(' ');
        playerCoords.push(currentCoords);
    }
    var myPlace = playerCoords[myID];

    // Compute logic here
    

    // printErr("Debug messages...");

    // Write action to standard output

}

