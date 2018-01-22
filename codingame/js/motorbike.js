// Read init information from standard input, if any
var road = readline()
    , gap = parseInt(readline())
    , lander = readline()
    , jumpedOver = false
    ;

while (1) {
    // Read information from standard input
    var bikeSpeed = readline()
        , bikePlace = readline()
        ;

    // Compute logic here
    if (bikePlace == road - 1) {
        print('JUMP');
        jumpedOver = true;
    } else {
        if (!jumpedOver) {
            if (bikeSpeed < gap + 1) {
                print('SPEED');
            } else if (bikeSpeed == gap + 1) {
                print('WAIT');
            } else {
                print('SLOW');
            }
        } else {
            print('SLOW');
        }
    }

    // printErr("Debug messages...");

    // Write action to standard output
}

