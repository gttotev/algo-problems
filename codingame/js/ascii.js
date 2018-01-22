//CodingGame - ASCII Art Solution

// Read inputs from Standard Input (use readline()).
// Write outputs to Standard Output (use print()).

var width = parseInt(readline())
    , height = parseInt(readline())
    , text = readline().toUpperCase()
    , patt = new RegExp('[^A-Z]')
    , numLetter = 0
    , outStr = ''
    , abc = {}
    , ascii = {}
    , letters = {}
    , line = []
    ;
    
for (i = 65; i <= 90; i++) {
    abc[String.fromCharCode(i)] = i - 65;
}

for (var i = 0; i < height; i++) {
    ascii[i] = readline();
}

for (var i = 0; i < height; i++) {
    line = [];
    for (var e = 0; e < 27 * width; e += width) {
        line.push(ascii[i].slice(e, e + width));
    }
    letters[i] = line;
}

for (var i = 0; i < height; i++) {
    for (var e = 0; e < text.length; e++) {
        if (patt.test(text.charAt(e))) {
            outStr += letters[i][26]
        } else {
            numLetter = abc[text.charAt(e)];
            outStr += letters[i][numLetter];
        }
    }
    outStr += '\n';
}

print(outStr);