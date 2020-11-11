package main

import (
	"fmt"
)

func ask(l1, r1, l2, r2 int) (a, b bool) {
	var x, y int
	fmt.Println("Q", l1, r1, l2, r2)
	fmt.Scanf("%d%d\n", &x, &y)
	return x == 1, y == 1
}

func max(x, y int) int {
	if x > y {
		return x
	}
	return y
}

var n int

func watch(l, r int) int {
	l = max(l, 1)
	r = -max(-r, -n)
	size := r - l

	switch size {
	case 0:
		return r
	case 1, 2:
		a, b := ask(l, l, r, r)
		switch {
		case a:
			return l
		case b:
			return r
		}
		return r - 1

	case 3:
		a, b := ask(l, l + 1, l + 1, r - 1)
		switch {
		case a && b:
			return l + 1
		case a:
			return l
		case b:
			return r - 1
		}
		return r
	}

	mid := (l + r) / 2
	lm, rm := (l + mid) / 2, (r + mid) / 2

	a, b := ask(l, mid, lm, rm)
	switch {
	case a && b:
		return watch(lm - 1, mid + 1)
	case a:
		if size < 8 {
			return l
		}
		return watch(l - 1, lm)
	case b:
		if size < 7 {
			return rm
		}
		return watch(mid, rm + 1)
	}
	if size < 5 {
		return r
	}
	return watch(rm, r + 1)
}

func main() {
	fmt.Scanf("%d\n", &n)
	t := watch(1, n)
	fmt.Println("A", t)
}
