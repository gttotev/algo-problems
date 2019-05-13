#include <stdio.h>

int main() {
    int tests, t;
    scanf("%d", &tests);
    for (t = 1; t <= tests; t++) {
        int n, i;
        int maxSpeed = 0;
        scanf("%d", &n);
        for (i = 0; i < n; i++) {
            int speed;
            scanf("%d", &speed);
            if (speed > maxSpeed)
                maxSpeed = speed;
        }
        printf("Case %d: %d\n", t, maxSpeed);
    }
    return 0;
}
