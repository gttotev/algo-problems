use std::{collections::BinaryHeap, cmp::Reverse};

const DIRS: [u8; 4] = [b'R', b'D', b'L', b'U'];
fn solve(part2: bool) -> i64 {
    let mut vert = BinaryHeap::new();
    let mut pos = (0, 0);
    for line in std::io::stdin().lines().flatten() {
        let dir;
        let step;
        if part2 {
            let ll = line.len();
            dir = DIRS[(line.as_bytes()[ll-2] - b'0') as usize];
            step = i64::from_str_radix(&line[ll-7..ll-2], 16).unwrap();
        } else {
            dir = line.as_bytes()[0];
            step = line[2..4].trim().parse().unwrap();
        }
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

    let first = vert.pop().unwrap();
    let mut res = 0;
    let mut col = first.0.0;
    let mut wall = vec![(first.1, first.2)];
    while let Some((Reverse(j), ia, ib)) = vert.pop() {
        let wl = wall.len();
        let mut ii = wl;
        let delta = j - col;
        for (i, &(wia, wib)) in wall.iter().enumerate() {
            if ia <= wib && ii == wl { ii = i; }
            res += (wib - wia + 1) * delta;
        }
        col = j;

        if ii == wl || ib < wall[ii].0 {
            wall.insert(ii, (ia, ib));
            continue;
        }

        let (wia, wib) = wall[ii];
        if ib == wia {
            wall[ii].0 = ia;
        } else if ia == wib {
            wall[ii].1 = ib;
            if ii < wl-1 && wall[ii+1].0 == ib {
                wall[ii].1 = wall[ii+1].1;
                wall.remove(ii+1);
            }
        } else if ia > wia && ib < wib {
            res += ib - ia - 1;
            wall[ii].1 = ia;
            wall.insert(ii+1, (ib, wib));
        } else if ia == wia {
            res += ib - ia;
            wall[ii].0 = ib;
            if ib == wib {
                res += 1;
                wall.remove(ii);
            }
        } else if ib == wib {
            res += ib - ia;
            wall[ii].1 = ia;
        } else {
            panic!("{:?} {} {:?}", &wall, ii, (ia, ib));
        }
    }

    res
}

fn main() {
    println!("Part 2: {}", solve(true));
    // println!("Part 1: {}", solve(false));
}
