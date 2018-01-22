// Read inputs from Standard Input (use readline()).
// Write outputs to Standard Output (use print()).

var origData = readline()
    , numLines = readline()
    , currentLine = [1, origData]
    ;
if (numLines == 1) {
    print(origData);
} else if (numLines == 2) {
    print(currentLine.toString().replace(/,/g, ' '));
} else {
    
for (var i = 2; i < numLines; i++) {
    var sameData = currentLine[0]
        , numSame = 1
        , partLine = []
        ;
    
    for (var e = 1; e < currentLine.length; e++) {
        if (sameData == currentLine[e]) {
            numSame ++;
        } else {
            partLine.push(numSame);
            partLine.push(sameData);
            numSame = 1;
            sameData = currentLine[e];
        }
        if (e == currentLine.length - 1) {
            partLine.push(numSame);
            partLine.push(sameData);
        }
    }
    currentLine = partLine;
}

print(currentLine.toString().replace(/,/g, ' '));
}
