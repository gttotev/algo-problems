// Read inputs from Standard Input (use readline()).
// Write outputs to Standard Output (use print()).

var numTeles = readline()
    , teles = []
    , teleGraph = {}
    ;

for (var i = 0; i < numTeles; i++) {
    var currentTele = readline().split('');
    teles.push(currentTele);
}

for (var i = 0; i < numTeles; i++) {
    for (var e = 0; e < teles[i].length - 1; e++) {
        var begVerStr = (e > 9 ? '' : '0') + e + teles[i][e]
            , endVerStr = ((e + 1) > 9 ? '' : '0') + (e + 1) + teles[i][e + 1]
            ;
        
        insertEdge(begVerStr, endVerStr);
    }
}

function insertEdge (begVer, endVer) {
    if (typeof teleGraph[begVer] == 'undefined') {
        teleGraph[begVer] = [];
        teleGraph[begVer].push(endVer);
    } else {
        if (teleGraph[begVer].indexOf(endVer) == -1) {
            teleGraph[begVer].push(endVer);
        }
    }
}

print(JSON.stringify(teleGraph));

