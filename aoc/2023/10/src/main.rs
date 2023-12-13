use std::{io, collections::HashSet};

#[derive(PartialEq, Clone, Copy)]
enum Dir { N, E, S, W }
use Dir::*;

type Pos = (usize, usize, Dir);

fn same_pos(a: Pos, b: Pos) -> bool {
    a.0 == b.0 && a.1 == b.1
}

fn next(map: &Vec<Vec<u8>>, (i, j, d): Pos) -> Pos {
    match map[i][j] {
        b'|' => if d == N { (i+1, j, d) } else { (i-1, j, d) }
        b'-' => if d == W { (i, j+1, d) } else { (i, j-1, d) }
        b'L' => if d == N { (i, j+1, W) } else { (i-1, j, S) }
        b'J' => if d == W { (i-1, j, S) } else { (i, j-1, E) }
        b'7' => if d == W { (i+1, j, N) } else { (i, j-1, E) }
        b'F' => if d == S { (i, j+1, W) } else { (i+1, j, N) }
        _ => panic!("wut"),
    }
}

fn _part2(map: Vec<Vec<u8>>, start: Pos) -> usize {
    // The bad practices continue.
    let rstart = (start.0, start.1 + 1, W);

    let mut printer = vec![vec![b' '; map[0].len()]; map.len()];

    let mut a = rstart;
    let mut righthand = true;
    let mut path = HashSet::new();
    path.insert((start.0, start.1));
    while !same_pos(a, start) {
        let b = next(&map, a);
        if a.0 > start.0 && a.1 == start.1 && a.1 != b.1 {
            righthand = b.1 > a.1;
        }

        printer[a.0][a.1] = b'G';
        path.insert((a.0, a.1));
        a = b;
    }

    let mut enclosed = HashSet::new();
    a = rstart;
    righthand = !righthand; // OVERRIDE
    while !same_pos(a, start) {
        let na = 0..0;
        let si = 0..a.0;
        let ei = a.0+1..map.len();
        let sj = 0..a.1;
        let ej = a.1+1..map[0].len();
        let ray = match (map[a.0][a.1], (a.2, righthand)) {
            (b'|', (N, true) | (S, false)) => (na, sj),
            (b'|', (N, false) | (S, true)) => (na, ej),
            (b'-', (W, true) | (E, false)) => (ei, na),
            (b'-', (W, false) | (E, true)) => (si, na),
            (b'L', (N, true) | (E, false)) => (ei, sj),
            (b'L', (N, false) | (E, true)) => (si, ej),
            (b'J', (N, true) | (W, false)) => (si, sj),
            (b'J', (N, false) | (W, true)) => (ei, ej),
            (b'7', (W, true) | (S, false)) => (ei, sj),
            (b'7', (W, false) | (S, true)) => (si, ej),
            (b'F', (E, true) | (S, false)) => (si, sj),
            (b'F', (E, false) | (S, true)) => (ei, ej),
            _ => panic!("wat"),
        };

        let mut rayge: Vec<_> = if ray.0.start == 0 { ray.0.rev().collect() } else { ray.0.collect() };
        for i in rayge {
            let ij = (i, a.1);
            if path.contains(&ij) { break; }
            printer[i][a.1] = b'.';
            enclosed.insert(ij);
        }
        rayge = if ray.1.start == 0 { ray.1.rev().collect() } else { ray.1.collect() }; // Kill me.
        for j in rayge {
            let ij = (a.0, j);
            if path.contains(&ij) { break; }
            printer[a.0][j] = b'.';
            enclosed.insert(ij);
        }

        a = next(&map, a);
    }

    for line in printer {
        println!("{}", String::from_utf8(line).unwrap());
    }
    enclosed.len()
}

fn _part1(map: Vec<Vec<u8>>, start: Pos) -> usize {
    // Yes, I precalculated them. I know.
    let mut a = (start.0, start.1 + 1, W);
    let mut b = (start.0 + 1, start.1, N);
    let mut res = 1;
    while !same_pos(a, b) {
        a = next(&map, a);
        if same_pos(a, b) { break; }
        b = next(&map, b);
        res += 1;
    }

    res
}

fn main() {
    let mut start = (0, 0, N);
    let map: Vec<_> = io::stdin().lines().enumerate().map(|(i, line)| {
        let row = line.unwrap().into_bytes();
        if let Some(j) = row.iter().position(|x| *x == b'S') {
            start = (i, j, N);
        }
        row
    }).collect();

    println!("Part 2: {}", _part2(map, start));
    // println!("Part 1: {}", _part1(map, start));
}
