#include <stdio.h>
#include <stddef.h>
#include <stdint.h>

int main(int argc, char *argv[]) {
    size_t n, m, i, j, w, note;
    w = scanf("%zu %zu", &n, &m);

    uint32_t notes[1000][32], bm, sum;
    for (i = 0; i < 1000; ++i)
        for (j = 0; j < 32; ++j)
            notes[i][j] = 0;

    for (i = 0; i < n; ++i) {
        w = scanf("%zu", &j);
        w = i >> 5;
        bm = UINT32_C(1) << (i & 31);
        for (; j > 0; --j) {
            sum = scanf("%zu", &note);
            notes[note - 1][w] |= bm;
        }
    }

    size_t sw = 0;
    uint32_t ins[32], *nins;
    for (i = 0; i < 32; ++i) ins[i] = UINT32_MAX;

    for (i = 0; i < m; ++i) {
        w = scanf("%zu", &note);
        nins = notes[note - 1];
        for (j = 0, sum = 0; j < 32; ++j)
            sum |= (ins[j] &= nins[j]);

        if (!sum) {
            ++sw;
            for (j = 0; j < 32; ++j) ins[j] = nins[j];
        }
    }

    printf("%zu\n", sw);
}
