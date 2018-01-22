//CodinGame - Defibrillators Solution

// Read inputs from Standard Input (use readline()).
// Write outputs to Standard Output (use print()).

var userLong = toRad(readline().replace(',', '.'))
    , userLat = toRad(readline().replace(',', '.'))
    , defibNum = readline()
    , defibs = {}
    , distances = {}
    , bestDefibName = ''
    ;
    
function toRad (degStr) {
    var deg = parseFloat(degStr);
    return (deg * Math.PI) / 180;
}

for (var i = 0; i < defibNum; i++) {
    var currentDefib = readline().split(';');
    defibs[i] = currentDefib;
}

for (var i in defibs) {
    var defibLong = toRad(defibs[i][4].replace(',', '.'))
        , defibLat = toRad(defibs[i][5].replace(',', '.'))
        , x = (defibLong - userLong) * Math.cos((userLat + defibLat) / 2)
        , y = defibLat - userLat
        , defibDistance = Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)) * 6371)
        , defibInfo = []
        ;

    defibInfo.push(defibDistance);
    defibInfo.push(defibs[i][1]);
    distances[i] = defibInfo;
}

for (var i in distances) {
    if (i >= 1) {    
        if (distances[i][0] < nearest) {
            nearest = distances[i][0];
            bestDefibName = distances[i][1];
        }
    } else {
        var nearest = distances[0][0];
        bestDefibName = distances[0][1];
    }
}

print(bestDefibName);
//print(JSON.stringify(defibs));
//print(JSON.stringify(distances));