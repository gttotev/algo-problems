#include <stdio.h>
#include <stddef.h>
#include <stdint.h>

int main(int argc, char *argv[]) {
    size_t n, m, i, j, w, note;
    w = scanf("%zu %zu", &n, &m);

    uint64_t notes[1000][16], bm, sum;
    for (i = 0; i < 1000; ++i)
        for (j = 0; j < 16; ++j)
            notes[i][j] = 0;

    for (i = 0; i < n; ++i) {
        w = scanf("%zu", &j);
        w = i >> 6;
        bm = UINT64_C(1) << (i & 63);
        for (; j > 0; --j) {
            sum = scanf("%zu", &note);
            notes[note - 1][w] |= bm;
        }
    }

    size_t sw = 0;
    uint64_t ins[16], *nins;
    for (i = 0; i < 16; ++i) ins[i] = UINT64_MAX;

    for (i = 0; i < m; ++i) {
        w = scanf("%zu", &note);
        nins = notes[note - 1];
        for (j = 0, sum = 0; j < 16; ++j)
            sum |= (ins[j] &= nins[j]);

        if (!sum) {
            ++sw;
            for (j = 0; j < 16; ++j) ins[j] = nins[j];
        }
    }

    printf("%zu\n", sw);
}
