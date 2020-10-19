#include <math.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct {
    double perbox;
    double nextper;
    int pop;
    int nboxes;
} City;

void printh(City *heap[], int n) {
    for (int i = 1; i <= n; ++i) {
        City *c = heap[i];
        printf("%f %f %d %d\n", c->perbox, c->nextper, c->pop, c->nboxes - 1);
    }
}

void swap(City *heap[], int i, int j) {
    City *tmp = heap[i];
    heap[i] = heap[j];
    heap[j] = tmp;
}

void siftd(City *heap[], int i, int size) {
    int d, g = i;
    for (int k = 0, ch = 2 * i; ch <= size && k < 2; ++k, ++ch) {
        d = heap[ch]->perbox - heap[g]->perbox;
        if (d > 0 || (d == 0 && heap[ch]->nextper < heap[g]->nextper)) g = ch;
    }
    if (g != i) {
        swap(heap, i, g);
        siftd(heap, g, size);
    }
}

int solve(int n, int b) {
    int pop, i;
    City *c, **cities = malloc((n+1) * sizeof(City *));
    for (i = 1; i <= n; ++i) {
        c = malloc(sizeof(City));
        scanf("%d", &pop);
        c->perbox = pop;
        c->nextper = pop / 2.0;
        c->pop = pop;
        c->nboxes = 2;
        cities[i] = c;
    }

    // make heap
    for (i = n / 2; i > 0; --i) {
        siftd(cities, i, n);
    }

    for (i = b - n; i > 0; --i) {
        c = cities[1];
        c->perbox = c->nextper;
        c->nextper = (double) c->pop / ++c->nboxes;
        siftd(cities, 1, n);
//        printh(cities, n);
//        puts("");
    }

    return (int) ceil(cities[1]->perbox);
}

int main(int argc, char *argv[]) {
    int n, b;
    scanf("%d %d", &n, &b);
    while (n != -1 && b != -1) {
        printf("%d\n", solve(n, b));
        scanf("%d %d", &n, &b);
    }
    return 0;
}
