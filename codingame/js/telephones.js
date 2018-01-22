// Read inputs from Standard Input (use readline()).
// Write outputs to Standard Output (use print()).

var numTeles = readline()
    , teles = []
    , totalSpace = 0
    ;

for (var i = 0; i < numTeles; i++) {
    var currentTele = readline().split('');
    totalSpace += currentTele.length;
    teles.push(currentTele);
}

if (numTeles > 1) {
    
for (var i = 0; i < numTeles - 1; i++) {

    var firstTele = teles[i]
        , firstLength = firstTele.length
        ;
    
    for (var e = i + 1; e < numTeles; e++) {
        var currentTele = teles[e]
            , currentLength = currentTele.length
            ;
        
        for (var o = 0; o < (firstLength < currentLength ? firstLength : currentLength); o++) {
            if (firstTele[o] == currentTele[o]) {
                print(firstTele[o], currentTele[o]);
                totalSpace --;
            } else {
                break;
            }
        }
    }
    
}

}

print(totalSpace);

