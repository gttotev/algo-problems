// Read inputs from Standard Input (use readline()).
// Write outputs to Standard Output (use print()).

var numTeles = readline()
    , teles = []
    , teleGraph = {}
    , totalSpace = 0
    ;

for (var i = 0; i < numTeles; i++) {
    var currentTele = readline().split('');
    teles.push(currentTele);
}

for (var i = 0; i < numTeles; i++) {
    teleGraph = insertEdge(teles[i], teleGraph);
}

function insertEdge (currentTele, teleObject) {
    if (currentTele.length == 0) {
        return teleObject;
    } else {
        currentDigit = currentTele.shift();
        if (currentDigit in teleObject) {
            var thing = teleObject[currentDigit];
            teleObject[currentDigit] = insertEdge(currentTele, thing);
        } else {
            totalSpace ++;
            teleObject[currentDigit] = insertEdge(currentTele, {});
        }
        return teleObject;
    }
}

//print(JSON.stringify(teleGraph));
print(totalSpace);

