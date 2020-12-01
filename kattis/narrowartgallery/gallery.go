package main

import "fmt"

func max(x, y int) int {
	if x > y {
		return x
	}
	return y
}

func solve(n, k int, gallery [][2]int) int {
	table := make([][][3]int, n+1)
	for i := range table {
		table[i] = make([][3]int, k+1)
		if i == 0 {
			continue
		}

		for j := range table[i] {
			open := gallery[i-1][0] + gallery[i-1][1] + table[i-1][j][0]
			if j == 0 { // no fills, get open as max
				for k := range table[i][j] {
					table[i][j][k] = open
				}
				continue
			}

			// blocking:
			left := gallery[i-1][1] + table[i-1][j-1][1]
			right := gallery[i-1][0] + table[i-1][j-1][2]

			maxlr := max(left, right)
			if j == i {
				table[i][j][0] = maxlr
				table[i][j][1] = left
				table[i][j][2] = right
				break
			}

			table[i][j][0] = max(maxlr, open)
			table[i][j][1] = max(left, open)
			table[i][j][2] = max(right, open)
		}
	}
	return table[n][k][0]
}

func main() {
	var n, k int
	for {
		fmt.Scanf("%d%d\n", &n, &k)
		if n == 0 && k == 0 {
			break
		}
		gallery := make([][2]int, n)
		for i := range gallery {
			fmt.Scanf("%d%d\n", &gallery[i][0], &gallery[i][1])
		}
		fmt.Println(solve(n, k, gallery))
	}
}
