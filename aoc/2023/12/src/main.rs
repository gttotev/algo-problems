fn _part1(mut springs: Vec<u8>, i: usize, unknown: usize, broken: &[usize]) -> u64 {
    if unknown == 0 {
        if String::from_utf8(springs).unwrap().split_ascii_whitespace().map(|s| s.len()).collect::<Vec<_>>() == broken {
            1
        } else {
            0
        }
    } else if springs[i] != b'?' {
        _part1(springs, i + 1, unknown, broken)
    } else {
        springs[i] = b'#';
        let broke = _part1(springs.clone(), i+1, unknown-1, broken);
        springs[i] = b' ';
        broke + _part1(springs, i+1, unknown-1, broken)
    }
}

fn _fits(springs: &[u8], j: usize, b: usize) -> Option<usize> {
    let mut h = 0;
    if springs[j..j+b].iter().all(|&c| { if c == b'#' { h += 1; } c != b'.' })
        && (j == 0 || springs[j-1] != b'#')
        && (j+b == springs.len() || springs[j+b] != b'#')
    {
        Some(h)
    } else { None }
}

fn _part2(springs: &[u8], broken: &[usize], huse: usize, htot: usize) -> u64 {
    if broken.len() == 0 {
        if huse == htot { 1 } else { 0 }
    } else if springs.len() < broken[0] {
        0
    } else {
        let slen = springs.len();
        let b = broken[0];
        let mut res = 0;
        for j in 0..=springs.len()-b {
            if let Some(h) = _fits(springs, j, b) {
                res += _part2(&springs[slen.min(j+b+1)..], &broken[1..], huse+h, htot);
            }
            if springs[j] == b'#' { break; }
        }
        res
    }
}

fn solve(part2: bool) -> u64 {
    let mut res = 0;
    for line in std::io::stdin().lines() {
        let line = line.unwrap();
        let space = line.find(" ").unwrap();
        let mut springs: Vec<u8> = line[..space].into();
        let mut broken: Vec<_> = line[space+1..].split(",").map(|s| s.parse().unwrap()).collect();
        if part2 {
            broken = broken.repeat(5);
            springs = [&springs[..]; 5].join(&b'?');
        }

        let _hcum: Vec<usize> = springs.iter().scan(0, |state, &s| {
            if s == b'#' { *state += 1; }
            Some(*state)
        }).collect();
        let dp = _part2(&springs, &broken, 0, springs.iter().filter(|&&c| c == b'#').count());

        // let springs = line[..space].replace(".", " ").into_bytes();
        // let unknown = springs.iter().filter(|&&c| c == b'?').count();
        // let recur = _part1(springs, 0, unknown, &broken);

        // if dp != recur { dbg!(line, dp, recur); }

        res += dp;
    }
    res
}

fn main() {
    println!("Part 2: {}", solve(true));
    // println!("Part 1: {}", solve(false));
}
