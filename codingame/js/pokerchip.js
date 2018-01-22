/**
 * It's the survival of the biggest!
 * Propel your chips across a frictionless table top to avoid getting eaten by bigger foes.
 * Aim for smaller oil droplets for an easy size boost.
 * Tip: merging your chips will give you a sizeable advantage.
 **/

var myPlayerId = parseInt(readline()); // your id (0 to 4)

// game loop
while (true) {
    var playerChipCount = parseInt(readline()) // The number of chips under your control
    , entityCount = parseInt(readline()) // The total number of entities on the table, including your chips
    , myChips = {}
    , myChipsCounter = 0
    , allEntities = {}
    ;
    
    for (var i = 0; i < entityCount; i++) {
        var inputs = readline().split(' ')
        , entityId = parseInt(inputs[0]) // Unique identifier for this entity
        , playerId = parseInt(inputs[1]) // The owner of this entity (-1 for neutral droplets)
        , radius = parseFloat(inputs[2]) // the radius of this entity
        , x = parseFloat(inputs[3]) // the X coordinate (0 to 799)
        , y = parseFloat(inputs[4]) // the Y coordinate (0 to 514)
        , vx = parseFloat(inputs[5]) // the speed of this entity along the X axis
        , vy = parseFloat(inputs[6]) // the speed of this entity along the Y axis
        ;
        allEntities[entityId] = {};
        allEntities[entityId]['rad'] = radius;
        allEntities[entityId]['x'] = x;
        allEntities[entityId]['y'] = y;
        allEntities[entityId]['vx'] = vx;
        allEntities[entityId]['vy'] = vy;
        allEntities[entityId]['playerId'] = playerId;
        
        if (playerId == myPlayerId) {
            myChips[myChipsCounter] = {};
            myChips[myChipsCounter]['id'] = entityId;
            myChips[myChipsCounter]['rad'] = radius;
            myChips[myChipsCounter]['x'] = x;
            myChips[myChipsCounter]['y'] = y;
            myChips[myChipsCounter]['vx'] = vx;
            myChips[myChipsCounter]['vy'] = vy;
            myChipsCounter++;
        }
        
    }
    printErr(JSON.stringify(allEntities[5]));
    for (var i = 0; i < playerChipCount; i++) {

        // Write an action using print()
        // To debug: printErr('Debug messages...');

        print('WAIT'); // One instruction per chip: 2 real numbers (x y) for a propulsion, or 'WAIT'.
    }
}

