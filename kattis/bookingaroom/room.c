#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
    unsigned int r, n, i, ri;
    unsigned char *rooms, cr;
    ri = scanf("%d %d", &r, &n);
    if (r == n) {
        puts("too late");
        return 0;
    }

    r = ((r - 1) >> 3) + 1;
    rooms = malloc(r);
    for (i = 0; i < r; ++i) rooms[i] = 0;
    for (i = 0; i < n; ++i) {
        r = scanf("%d", &ri);
        --ri;
        rooms[ri >> 3] |= (1 << (ri & 7));
    }

    for (i = 0; cr = rooms[i], cr == 255; ++i);
    for (ri = 0; cr & 1; cr >>= 1, ++ri);

    printf("%d\n", (i << 3) + ri + 1);
    return 0;
}
