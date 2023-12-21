#![allow(dead_code)]
use std::{collections::{HashSet, BinaryHeap}, cmp::Reverse};

type Pos = (i32, i32);

fn floodfill(pos: Pos, perim: &HashSet<Pos>, found: &mut HashSet<Pos>) -> u32 {
    if found.contains(&pos) || perim.contains(&pos) { return 0; }
    found.insert(pos);
    let (i, j) = pos;
    1
        + floodfill((i, j+1), perim, found)
        + floodfill((i, j-1), perim, found)
        + floodfill((i+1, j), perim, found)
        + floodfill((i-1, j), perim, found)
}

fn part1() -> u32 {
    let mut perim = HashSet::new();

    let mut pos = (0i32, 0i32);
    let (mut mini, mut maxi, mut minj, mut maxj) = (0, 0, 0, 0);
    for line in std::io::stdin().lines().flatten() {
        let dir = line.as_bytes()[0];
        let step: i32 = line[2..4].trim().parse().unwrap();
        for _ in 0..step {
            match dir {
                b'R' => pos.1 += 1,
                b'L' => pos.1 -= 1,
                b'U' => pos.0 -= 1,
                b'D' => pos.0 += 1,
                _ => panic!("wat"),
            }
            perim.insert(pos);

            mini = mini.min(pos.0);
            maxi = maxi.max(pos.0);
            minj = minj.min(pos.1);
            maxj = maxj.max(pos.1);
        }
    }
    dbg!(mini, minj);

    // let mut printer = vec![vec![b'.'; (maxj-minj+1) as usize]; (maxi-mini+1) as usize];
    // for line in printer {
    //     println!("{}", String::from_utf8(line).unwrap());
    // }

    let mut found = HashSet::new();
    floodfill((20+mini, 4+minj), &perim, &mut found) + perim.len() as u32 // input.txt
    // floodfill((1, 1), &perim, &mut found) + perim.len() as u32 // sample.txt
}

const DIR: [u8; 4] = [b'R', b'D', b'L', b'U'];
fn part2() -> i64 {
    let mut vert = BinaryHeap::new();
    let mut pos = (0, 0);
    for line in std::io::stdin().lines().flatten() {
        let dir = line.as_bytes()[0];
        let step: i32 = line[2..4].trim().parse().unwrap();
        // let ll = line.len();
        // let dir = DIR[line.as_bytes()[ll-2] - b'0'];
        // let step = i64::from_str_radix(&line[ll-7..ll-2], 16).unwrap();
        match dir {
            b'R' => pos.1 += step,
            b'L' => pos.1 -= step,
            b'D' => {
                vert.push((Reverse(pos.1), pos.0, pos.0 + step));
                pos.0 += step;
            }
            b'U' => {
                vert.push((Reverse(pos.1), pos.0 - step, pos.0));
                pos.0 -= step;
            }
            _ => panic!("wat"),
        }
    }

    let mut wall: Vec<(i32, i32, i32)> = Vec::new();
    while let Some((Reverse(j), mut ia, mut ib)) = vert.pop() {
        let wl = wall.len();
        let mut ii = Some(wl);
        for i in 0..wl {
            let (wj, wia, wib) = wall[i];
            if ib <= wia {
                ii = Some(i);
                break;
            }

            if ia == wib && (i == wl-1 || wall[i+1].1 - wib > 1) { continue; } // Assume connected
            if ia <= wib {
                ii = None;
                let mut di = i;
                while di < wl && ib >= wall[di].1 {
                    let (wj, wia, wib) = wall[di];
                    let span = ib.min(wib) - ia.max(wia) + 1;
                    println!("X {:?} {:?} {}", (ia, ib), (wia, wib), span);

                    di += 1;
                }
                break;
            }
        }
        if let Some(i) = ii {
            if i < wl && ib == wall[i].1 { ib -= 1; }
            if i > 0 && ia == wall[i-1].2 { ia += 1; }
            wall.insert(i, (j, ia, ib));
        }
        println!("{:?}", wall);
    }

    0
}

fn main() {
    println!("Part 2: {}", part2());
    // println!("Part 1: {}", part1());
}
