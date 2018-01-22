// Read init information from standard input, if any
var firstLine = readline().split(' ')
    , numNodes = firstLine[0]
    , numLinks = firstLine[1]
    , numGates = firstLine[2]
    , linksArray = []
    , links = {}
    , gates = []
    ;
    
for (var i = 0; i < numLinks; i++) {
    var currentLink = readline().split(' ');
    linksArray.push(currentLink);
}
for (var i = 0; i < numGates; i++) {
    var currentGate = readline();
    gates.push(currentGate);
}
for (var i = 0; i < numLinks; i++) {
    if (gates.indexOf(linksArray[i][0]) != -1) {
        addLinks(linksArray[i][1], linksArray[i][0]);
    }
    if (gates.indexOf(linksArray[i][1]) != -1) {
        addLinks(linksArray[i][0], linksArray[i][1]);
    }
}

while (1) {
    // Read information from standard input
    var virusPlace = readline()
        , madeCut = false
        ;

    // Compute logic here
    if (virusPlace in links) {
        for (var g = 0; g < numGates; g++) {
            //Looping through gates to find virus
            var currentGate = gates[g]
                , virusToGate = links[virusPlace].indexOf(currentGate)
                ;
            
            if (virusToGate != -1) {
                //Discovered that virus is near to gate
                print(virusPlace, currentGate);
                links[virusPlace].splice(virusToGate, 1);
                madeCut = true;
                break;
                //Cut that link and deleted connections in link variable
            }
        }
    } else {
        //Virus not near a gate
        for (var n in links) {
            //Looping through nodes connected to gates
            var currentNodeConns = links[n]
                ;
            
            if (currentNodeConns.length == 2) {
                //The node is connected to 2 gates
                print(n, links[n][0]);
                links[n].splice(0, 1);
                madeCut = true;
                break;
                //Cut one of the gate's two node links
            }
        }
    }
    if (madeCut == false) {
        //Virus not near gate, no gates with 2 conns
        for (var n in links) {
            //Systematic loop through all connected node-gate links
            var currentNodeConns = links[n];
            if (currentNodeConns.length == 0) {
                continue;
            } else {
                print(n, links[n][0]);
                links[n].splice(0, 1);
                madeCut = true;
                break;
            }
        }
    }
}

function addLinks (from, to) {
    if (typeof links[from] == 'undefined') {
        links[from] = [];
    }
    links[from].push(to);
}
printErr(JSON.stringify(links));

