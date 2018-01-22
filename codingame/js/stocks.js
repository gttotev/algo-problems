//CodinGame - Stock Exchange Losses v. 2

// Read inputs from Standard Input (use readline()).
// Write outputs to Standard Output (use print()).

var numStocks = readline()
    , stocks = readline().split(' ')
    , largestDiff = stocks[1] - stocks[0]
    , bigger = parseInt(stocks[0])
    ;

for (var i = 0; i < numStocks - 1; i++) {
    if (stocks[i] >= bigger) {
        bigger = parseInt(stocks[i]);
        for (var e = i + 1; e < numStocks; e++) {
            if (largestDiff > (stocks[e] - stocks[i])) {
                largestDiff = stocks[e] - stocks[i];
            }
        }
    }
}

//print(stocks);
print(largestDiff > 0 ? '0' : largestDiff);