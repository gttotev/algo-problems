fn _part1() -> u64 {
    let mut os = 0;
    let mut sub = 0;
    let mut rows = 0;
    let mut coli = [0; 128];
    for line in std::io::stdin().lines().flatten() {
        for (j, &c) in line.as_bytes().iter().enumerate() {
            if c == b'O' {
                os += 1;
                sub += coli[j];
                coli[j] += 1;
            } else if c == b'#' {
                coli[j] = rows + 1;
            }
        }

        rows += 1;
    }
    rows * os - sub
}

fn tilt(grid: &mut Vec<Vec<u8>>, ns: bool, nw: bool) -> u64 {
    let (rows, cols) = (grid.len(), grid[0].len());
    let (il, jl) = if nw { (0, 0) } else { (rows-1, cols-1) };
    let mut res = 0;
    let mut coli = [0usize; 128];
    for i in 0..rows {
        let ii = il.abs_diff(i);
        let mut rowj = 0usize;
        for j in 0..cols {
            let jj = jl.abs_diff(j);
            let c = grid[ii][jj];
            if c == b'O' {
                grid[ii][jj] = b'.';
                res += i + 1;
                if ns {
                    grid[coli[j].abs_diff(il)][jj] = b'O';
                } else {
                    grid[ii][rowj.abs_diff(jl)] = b'O';
                }
                coli[j] += 1;
                rowj += 1;
            } else if c == b'#' {
                coli[j] = i + 1;
                rowj = j + 1;
            }
        }
    }
    res as u64
}

fn cycle(grid: &mut Vec<Vec<u8>>) {
    tilt(grid, true, true);
    tilt(grid, false, true);
    tilt(grid, true, false);
    tilt(grid, false, false);
}

fn part2() -> u64 {
    const CYCLES: usize = 1000000000;
    let mut history = Vec::new();
    let mut grid: Vec<_> = std::io::stdin().lines().flatten().map(|s| s.into_bytes()).collect();
    for i in 0..CYCLES {
        cycle(&mut grid);
        for j in (0..i).rev() {
            if history[j] == grid {
                return tilt(&mut history[j + (CYCLES-j-1) % (i-j)], false, false);
            }
        }
        history.push(grid.clone());
    }
    0
}

fn main() {
    println!("Part 2: {}", part2());
    // println!("Part 1: {}", _part1());
}
