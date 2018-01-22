// Read inputs from Standard Input (use readline()).
// Write outputs to Standard Output (use print()).

var numInflus = readline()
    , influGraph = {}
    , connGraph = {}
    , largest = 0
    ;

for (var i = 0; i < numInflus; i++) {
    var influArray = readline().split(' ');
    insertToGraph(influArray[0], influArray[1]);
    countConnections(influArray[0], influArray[1]);
}

for (var vertex in connGraph) {
    if (connGraph[vertex] == 0) {
        recursiveFinder(vertex, [vertex]);
    }
}

function insertToGraph(from, to) {
    if (typeof influGraph[from] == 'undefined') {
        influGraph[from] = [];
    }
    influGraph[from].push(to);
}

function countConnections(from, to) {
    if (typeof connGraph[to] == 'undefined') {
        connGraph[to] = 0;
    }
    connGraph[to] += 1;
    if (typeof connGraph[from] == 'undefined') {
        connGraph[from] = 0;
    }
}

function recursiveFinder(first, path) {
    if (typeof influGraph[first] == 'undefined') {
        if (path.length > largest) {
            largest = path.length;
        }
        //print('path', path);
    } else {
        for (var i = 0; i < influGraph[first].length; i++) {
            var vertex = influGraph[first][i]
                , newPath = path.slice(0)
                ;
            
            newPath.push(vertex);
            recursiveFinder(vertex, newPath);
        }
    }
}

//print('influGraph', JSON.stringify(influGraph));
//print('connGraph', JSON.stringify(connGraph));
print(largest);
