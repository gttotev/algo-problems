// Read inputs from Standard Input (use readline()).
// Write outputs to Standard Output (use print()).

var numAliens = readline()
    , totalCost = readline()
    , budgets = []
    , totalBudgets = 0
    , sharedBudgets = []
    ;

for (var i = 0; i < numAliens; i++) {
    var currentBudget = parseInt(readline());
    totalBudgets += currentBudget;
    budgets.push(currentBudget);
}

if (totalBudgets < totalCost) {
    print('IMPOSSIBLE');
} else {
    budgets.sort(function(a,b) {return a-b;});
    budgetDivider(totalCost, budgets);
    print(sharedBudgets.toString().replace(/,/g, '\n'));
}

function budgetDivider (costLeft, budgetsLeft) {
    var goodShare = Math.floor(costLeft / budgetsLeft.length)
        , currentBudget = budgetsLeft[0]
        , currentShare = 0
        ;
    if (costLeft == 0) { return; }
    if (goodShare <= currentBudget) {
        currentShare = goodShare;
    } else {
        currentShare = currentBudget;
    }
    budgetsLeft.shift();
    costLeft -= currentShare;
    sharedBudgets.push(currentShare);
    budgetDivider (costLeft, budgetsLeft);
}

