// Read init information from standard input, if any
var pointNum = readline()
    , points = {}
    , sameLandXFrom = 0
    , sameLandXTo = 0
    , sameLandY = 0
    , desiredAngle = 0
    , desiredThrust = 0
    ;

function angleFinder (distanceX, distanceY, horSpeed) {
    var angle = 0
        , absDistanceX = Math.abs(distanceX)
        , absHorSpeed = Math.abs(horSpeed)
        ;
    
    printErr(absHorSpeed);
    printErr(distanceY);
    if (absDistanceX > 3000) {
        if (absHorSpeed > 40) {
            angle = 5;
        } else {
            angle = -25;
        }
    } else if (absDistanceX > 1250) {
        if (absHorSpeed > 30) {
            angle = 10;
        } else {
            angle = -20;
        }
    } else if (absDistanceX > 1000) {
        if (absHorSpeed > 20) {
            angle = 15;
        } else {
            angle = -10;
        }
    } else {
        if (distanceY < 500) {
            angle = 0;
        } else {
            if (absHorSpeed > 15) {
                angle = 5;
            } else {
                angle = -5;
            }
        }
    }
    
    return distanceX > 0 ? angle : -angle;
}

for (i = 0; i < pointNum; i++) {
    var currentPoint = readline().split(' ');
    points[i] = [];
    points[i].push(parseInt(currentPoint[0]));
    points[i].push(parseInt(currentPoint[1]));
}

sameLandXFrom = points[0][0];
sameLandY = points[0][1];

for (i = 1; i < pointNum; i++) {
    if (sameLandY == points[i][1]) {
        sameLandXTo = points[i][0];
        break;
    } else {
        sameLandY = points[i][1];
        sameLandXFrom = points[i][0];
    }
}

while (1) {
    // Read information from standard input
    var n = readline()
        , lander = n.split(' ')
        , landerX = lander[0]
        , landerY = lander[1]
        , hSpeed = lander[2]
        , vSpeed = lander[3]
        , fuel = lander[4]
        , angle = lander[5]
        , thrustPower = lander[6]
        , landPointX = (sameLandXFrom + sameLandXTo) / 2
        , distLandZoneX = landPointX - landerX
        , distLandZoneY = landerY - sameLandY
        ;

    // Compute logic here
    // Write action to standard output
    if (vSpeed <= -40) {
        desiredThrust = 4;
    } else if (vSpeed <= -30) {
        desiredThrust = 4;
    } else if (vSpeed <= -20) {
        desiredThrust = 3;
    } else if (vSpeed <= -10) {
        desiredThrust = 1;
    } else {
        desiredThrust = 0;
    }
    
    
    desiredAngle = angleFinder(distLandZoneX, distLandZoneY, hSpeed);
    printErr(distLandZoneX);
    print(desiredAngle, desiredThrust);
}

