use std::collections::{HashSet, VecDeque};

fn part1() -> u32 {
    let mut res = 0;
    let mut start = (0, 0);
    let mut grid = Vec::new();
    for (i, line) in std::io::stdin().lines().flatten().enumerate() {
        if let Some(j) = line.find('S') {
            start = (i+1, j+1);
        }
        grid.push(line.into_bytes());
    }

    let steps: u32 = std::env::args().skip(1).next().unwrap().parse().unwrap();
    let sr = steps % 2;
    let mut visited = HashSet::new();
    let mut walkq = VecDeque::new();
    walkq.push_back((steps, start.0, start.1));
    while let Some((s, i, j)) = walkq.pop_front() {
        let ij = (i, j);
        if visited.contains(&ij) { continue; }
        visited.insert(ij);
        if i < 1 || j < 1 || i > grid.len() || j > grid[0].len() || grid[i-1][j-1] == b'#' { continue; }
        if s % 2 == sr { res += 1; }
        if s == 0 { continue; }
        let s = s - 1;
        walkq.push_back((s, i-1, j));
        walkq.push_back((s, i+1, j));
        walkq.push_back((s, i, j-1));
        walkq.push_back((s, i, j+1));
    }

    res
}

fn main() {
    println!("Part 1: {}", part1());
}
