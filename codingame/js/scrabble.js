// Read inputs from Standard Input (use readline()).
// Write outputs to Standard Output (use print()).

var numDefs = readline()
    , defArray = []
    , letterValues = {
        e: 1,
        a: 1,
        i: 1,
        o: 1,
        n: 1,
        r: 1,
        t: 1,
        l: 1,
        s: 1,
        u: 1,
        d: 2,
        g: 2,
        b: 3,
        c: 3,
        m: 3,
        p: 3,
        f: 4,
        h: 4,
        v: 4,
        w: 4,
        y: 4,
        k: 5,
        j: 8,
        x: 8,
        q: 10,
        z: 10
        }
    , bestWord = ''
    , bestScore = 0
    ;

for (var i = 0; i < numDefs; i++) {
    defArray.push(readline());
}
var letterList = readline();

for (var iWord = 0; iWord < numDefs; iWord++) {
    var word = defArray[iWord]
        , wordArray = word.split('')
        , tmpLetterList = letterList
        , currentScore = 0
        , check = true
        ;
    
    for (var iLetter = 0; iLetter < wordArray.length; iLetter ++) {
        var letter = wordArray[iLetter];
        if (tmpLetterList.indexOf(letter) != -1) {
            tmpLetterList = tmpLetterList.replace(letter, '');
            currentScore += letterValues[letter];
        } else {
            check = false;
            break;
        }
    }
    if (check) {
        if (currentScore > bestScore) {
            bestScore = currentScore;
            bestWord = word;
        }
    }
}

//print('Definitions:', defArray);
//print('Available letters:', letterList);

print(bestWord);
