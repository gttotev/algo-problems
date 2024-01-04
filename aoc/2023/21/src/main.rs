use std::collections::{HashSet, VecDeque};

fn walk((si, sj): (usize, usize), s0: usize, steps: usize, grid: &Vec<Vec<u8>>) -> u64 {
    let n = grid.len();  // Assume square grid
    let sr = steps % 2;

    let mut res = 0;
    let mut visited = HashSet::new();
    let mut walkq = VecDeque::new();
    walkq.push_back((s0, si, sj));

    while let Some((s, i, j)) = walkq.pop_front() {
        let ij = (i, j);
        if visited.contains(&ij) || grid[i][j] == b'#' { continue; }
        visited.insert(ij);

        if s % 2 == sr { res += 1; }
        if s == steps { continue; }

        let s = s + 1;
        if i > 0   { walkq.push_back((s, i-1, j)); }
        if i < n-1 { walkq.push_back((s, i+1, j)); }
        if j > 0   { walkq.push_back((s, i, j-1)); }
        if j < n-1 { walkq.push_back((s, i, j+1)); }
    }
    res
}

fn part2(start: (usize, usize), steps: usize, grid: &Vec<Vec<u8>>) -> u64 {
    let n = grid.len();  // Assume n and n/2 odd
    let k = ((steps - n/2) / n) as u64;  // Assume divisible and quotient even

    let spike = steps - n + 1;  // Assume unobstructed central row and column
    let corner = spike + n/2 + 1;
    let base = corner - n;

    let ends = [0, n-1];  // Assume unobstructed borders
    let ends = ends.iter().flat_map(|i| ends.iter().map(|j| (*i, *j)));
    let bases = (k-1) * ends.clone().map(|start| walk(start, base, steps, grid)).sum::<u64>();
    let corners = k * ends.map(|start| walk(start, corner, steps, grid)).sum::<u64>();
    let spikes: u64 = [0, n-1].into_iter().flat_map(|a| [(a, n/2), (n/2, a)]).map(|start| walk(start, spike, steps, grid)).sum();

    let full_odd = walk(start, 0, n, grid);
    let full_even = walk(start, 0, n+1, grid);
    let k2 = k * k;
    k2*full_even + (k2 - 2*k + 1)*full_odd + spikes + corners + bases
}

fn main() {
    let steps = std::env::args().skip(1).next().unwrap().parse().unwrap();
    let mut start = (0, 0);
    let mut grid = Vec::new();
    for (i, line) in std::io::stdin().lines().flatten().enumerate() {
        if let Some(j) = line.find('S') {
            start = (i, j);
        }
        grid.push(line.into_bytes());
    }
    println!("Part 2: {}", part2(start, steps, &grid));
    // println!("Part 1: {}", walk(start, 0, steps, &grid));
}
