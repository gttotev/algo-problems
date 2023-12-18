use std::collections::BinaryHeap;

fn try_step(i: usize, j: usize, gr: usize, gc: usize, dir: usize) -> Option<(usize, usize)> {
    match dir {
        0 => if j < gc-1 { Some((i, j+1)) } else { None },
        1 => if j > 0 { Some((i, j-1)) } else { None },
        2 => if i < gr-1 { Some((i+1, j)) } else { None },
        3 => if i > 0 { Some((i-1, j)) } else { None },
        _ => panic!("wat"),
    }
}

fn disti(i: usize, j: usize, gc: usize, dir: usize, step: usize) -> usize {
    (i*gc + j)*12 + dir*3 + step
}

fn part1(grid: &Vec<Vec<i32>>) -> i32 {
    let (gr, gc) = (grid.len(), grid[0].len());
    let mut dist = vec![i32::MIN; gr*gc*12];
    let mut heap = BinaryHeap::new();

    heap.push((grid[0][1], 0, 1, 0, 0));
    heap.push((grid[1][0], 1, 0, 2, 0));
    while let Some((d, i, j, dir, step)) = heap.pop() {
        let di = disti(i, j, gc, dir, step);
        if d < dist[di] { continue; }

        for dx in [2, 3, 0] {
            let dir = dir ^ dx;
            let step = if dx == 0 { step + 1 } else { 0 };
            if step == 3 { continue; }
            if let Some((ii, jj)) = try_step(i, j, gr, gc, dir) {
                let dd = d + grid[ii][jj];
                let di = disti(ii, jj, gc, dir, step);
                if dd > dist[di] {
                    dist[di] = dd;
                    heap.push((dd, ii, jj, dir, step));
                }
            }
        }
    }

    -*dist[(gr*gc - 1)*12..].iter().max().unwrap()
}

fn disti2(i: usize, j: usize, gc: usize, dir: usize, step: usize) -> usize {
    (i*gc + j)*40 + dir*10 + step
}

fn part2(grid: &Vec<Vec<i32>>) -> i32 {
    let (gr, gc) = (grid.len(), grid[0].len());
    let mut dist = vec![i32::MIN; gr*gc*40];
    let mut heap = BinaryHeap::new();

    heap.push((grid[0][1], 0, 1, 0, 0));
    heap.push((grid[1][0], 1, 0, 2, 0));
    while let Some((d, i, j, dir, step)) = heap.pop() {
        let di = disti2(i, j, gc, dir, step);
        if d < dist[di] { continue; }

        for dx in [2, 3, 0] {
            let dir = dir ^ dx;
            let ss = if dx == 0 { step + 1 } else { 0 };
            if ss == 10 || dx != 0 && step < 3 { continue; }
            if let Some((ii, jj)) = try_step(i, j, gr, gc, dir) {
                let dd = d + grid[ii][jj];
                let di = disti2(ii, jj, gc, dir, ss);
                if dd > dist[di] {
                    dist[di] = dd;
                    heap.push((dd, ii, jj, dir, ss));
                }
            }
        }
    }

    -*dist[(gr*gc - 1)*40..].iter().enumerate()
        .filter_map(|(di, d)| if di % 10 > 2 { Some(d) } else { None })
        .max().unwrap()
}

fn main() {
    let z = b'0' as i32;
    let grid: Vec<Vec<_>> = std::io::stdin().lines().flatten().map(|s| s.as_bytes().iter().map(|&c| z - c as i32).collect()).collect();
    println!("Part 2: {}", part2(&grid));
    println!("Part 1: {}", part1(&grid));
}
