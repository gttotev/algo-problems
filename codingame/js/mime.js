//CodinGame - MIME Types Solution

// Read inputs from Standard Input (use readline()).
// Write outputs to Standard Output (use print()).

var numDefs = readline()
    , numFiles = readline()
    , defs = {}
    , files = []
    ;

for (var i = 0; i < numDefs; i++) {
    var currentDef = readline().split(' ');
    defs[currentDef[0].toLowerCase()] = currentDef[1];
}

for (var i = 0; i < numFiles; i++) {
    files.push(readline());
}

for (var i = 0; i < files.length; i++) {
    var lowerFile = files[i].toLowerCase()
        , from = lowerFile.lastIndexOf('.')
        , to = lowerFile.length
        , ext = lowerFile.substr(from + 1, to)
        ;
    if (from == -1 || defs[ext] == undefined) {
        print('UNKNOWN');
    } else {
        print(defs[ext]);
    }
}

//print(JSON.stringify(defs));
//print(JSON.stringify(files));