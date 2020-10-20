#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

int main(int argc, char *argv[]) {
    size_t n, m, i, j, w, note;
    w = scanf("%zu %zu", &n, &m);

    unsigned char notes[1000][125], bm, sum;
    for (i = 0; i < 1000; ++i)
        for (j = 0; j < 125; ++j)
            notes[i][j] = 0;

    for (i = 0; i < n; ++i) {
        w = scanf("%zu", &j);
        w = i >> 3;
        bm = 1U << (i & 7);
        for (; j > 0; --j) {
            sum = scanf("%zu", &note);
            notes[note - 1][w] |= bm;
        }
    }

    size_t sw = 0;
    unsigned char ins[125], *nins;
    for (i = 0; i < 125; ++i) ins[i] = 255;

    for (i = 0; i < m; ++i) {
        w = scanf("%zu", &note);
        nins = notes[note - 1];
        for (j = 0, sum = 0; j < 125; ++j)
            sum |= (ins[j] &= nins[j]);

        if (!sum) {
            ++sw;
            for (j = 0; j < 125; ++j) ins[j] = nins[j];
        }
    }

    printf("%zu\n", sw);
}
