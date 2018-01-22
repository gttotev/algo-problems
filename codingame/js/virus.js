// Read init information from standard input, if any
var firstLine = readline().split(' ')
    , numNodes = firstLine[0]
    , numLinks = firstLine[1]
    , numGates = firstLine[2]
    , links = {}
    , gates = []
    ;
    
for (var i = 0; i < numLinks; i++) {
    var currentLink = readline().split(' ');
    addLinks(currentLink[0], currentLink[1]);
}
for (var i = 0; i < numGates; i++) {
    gates.push(readline());
}

while (1) {
    // Read information from standard input
    var virusPlace = readline()
        , gateVirusLink = false
        ;

    // Compute logic here
    for (var g = 0; g < numGates; g++) {
        //First looping of gates
        var currentGate = gates[g]
            , virusToGate = links[virusPlace].indexOf(currentGate)
            , gateToVirus = links[currentGate].indexOf(virusPlace)
            ;
        
        if (virusToGate != -1 && gateToVirus != -1) {
            //Discovered that virus is near to gate
            gateVirusLink = true;
            print(virusPlace, currentGate);
            links[virusPlace].splice(virusToGate, 1);
            links[currentGate].splice(gateToVirus, 1);
            //Cut that link and deleted connections is link var.
        }
        if (gateVirusLink == true) {
            break;
        }
    }
    printErr(gateVirusLink);
    if (gateVirusLink == false) {
        //Didn't cut any connections in above loop
        for (var g = 0; g < numGates; g++) {
            //Second looping of gates
            var currentGate = gates[g]
                , gateConnections = links[currentGate]
                ;
            if (gateConnections.length == 0) { continue; }
            
            var remover = gateConnections[0]
                , removerToGate = links[remover].indexOf(currentGate)
                ;
            
            print(currentGate, remover);
            links[currentGate].splice(0, 1);
            links[remover].splice(removerToGate, 1);
            break;
            //Cut link from first connection in the currentGate's links
        }
    }
}

function addLinks (from, to) {
    if (typeof links[from] == 'undefined') {
        links[from] = [];
    }
    links[from].push(to);
    
    if (typeof links[to] == 'undefined') {
        links[to] = [];
    }
    links[to].push(from);
}
//printErr(JSON.stringify(links));

