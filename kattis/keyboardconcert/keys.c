#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
    size_t n, m, i, j, note;
    scanf("%zd %zd", &n, &m);

    char notes[1000][1000];
    for (i = 0; i < 1000; ++i)
        for (j = 0; j < 1000; ++j)
            notes[i][j] = 0;

    for (i = 0; i < n; ++i) {
        scanf("%zd", &j);
        for (; j > 0; --j) {
            scanf("%zd", &note);
            notes[note - 1][i] = 1;
        }
    }

    int sw = 0, sum;
    char ins[1000], *nins;
    for (i = 0; i < 1000; ++i) ins[i] = 1;

    for (i = 0; i < m; ++i) {
        scanf("%zd", &note);
        nins = notes[note - 1];
        for (j = 0, sum = 0; j < 1000; ++j) {
            ins[j] &= nins[j];
            sum += ins[j];
        }

        if (sum == 0) {
            ++sw;
            for (j = 0; j < 1000; ++j) ins[j] = nins[j];
        }
    }

    printf("%d\n", sw);
}
