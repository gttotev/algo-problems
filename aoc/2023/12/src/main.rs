fn _part1(mut springs: Vec<u8>, i: usize, unknown: usize, broken: &[u64]) -> u64 {
    if unknown == 0 {
        if String::from_utf8(springs).unwrap().split_ascii_whitespace().map(|s| s.len() as u64).collect::<Vec<_>>() == broken {
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

fn _fits(springs: &[u8], j: usize, b: usize) -> bool {
    springs[j..j+b].iter().all(|&c| c != b'.')
        && (j == 0 || springs[j-1] != b'#')
        && (j+b == springs.len() || springs[j+b] != b'#') 
}

fn _arrangements(springs: &[u8], broken: &[usize]) -> u64 {
    let mut table = vec![vec![0u64; springs.len()]; broken.len()];
    let mut lastb = broken[0];
    for j in 0..=springs.len()-lastb {
        if _fits(springs, j, lastb) { table[0][j] = 1; }
    }

    let mut boff = lastb + 1;
    for (i, &b) in broken.iter().enumerate().skip(1) {
        for j in boff..=springs.len()-b {
            if !_fits(springs, j, b) { continue; }
            // dbg!(i, j, b);
            // table[i][j] = dbg!(&table[i-1][..j-lastb]).iter().sum();
            table[i][j] = table[i-1][..j-lastb].iter().sum();
        }
        boff += b + 1;
        lastb = b;
    }

    // dbg!(&table);
    table.last().unwrap().iter().sum()
}

fn _fit2(springs: &[u8], j: usize, b: usize) -> Option<usize> {
    let mut h = 0;
    if springs[j..j+b].iter().all(|&c| { if c == b'?' { h += 1; } c != b'.' })
        && (j == 0 || springs[j-1] != b'#')
        && (j+b == springs.len() || springs[j+b] != b'#')
    {
        Some(h)
    } else { None }
}

fn _part2(springs: &[u8], broken: &[usize]) -> u64 {
    let hcum: Vec<usize> = springs.iter().scan(0, |state, &c| {
        if c == b'#' { *state += 1; }
        Some(*state)
    }).collect();

    let mut table = vec![vec![0u64; springs.len()]; broken.len()];
    let mut lastb = broken[0];
    table[0] = (0..springs.len()).scan(0, |state, j| {
        if j <= springs.len()-lastb {
            if let Some(h) = _fit2(springs, j, lastb) {
                if h + hcum[j+lastb-1] == lastb { *state += 1; } // End me.
            }
        }
        Some(*state)
    }).collect();
    // for j in 0..=springs.len()-lastb {
    //     table[0][j] = if j == 0 { 0 } else { table[0][j-1] };
    //     if let Some(h) = _fit2(springs, j, lastb) {
    //         if h + hcum[j+lastb-1] == lastb { table[0][j] += 1; }
    //     }
    // }

    let mut boff = lastb + 1;
    for (i, &b) in broken.iter().enumerate().skip(1) {
        for j in boff..springs.len() {
            table[i][j] = table[i][j-1];
            if j <= springs.len()-b {
                // dbg!(j, b);
                if let Some(h) = _fit2(springs, j, b) {
                    // if dbg!(h + hcum[j+b-1]) - dbg!(hcum[j-1]) == b { table[i][j] += table[i-1][j-lastb-1]; } // Ugh.
                    if h + hcum[j+b-1] - hcum[j-1] == b { table[i][j] += table[i-1][j-lastb-1]; } // Ugh.
                }
            }
        }
        boff += b + 1;
        lastb = b;
    }

    // Final try here.
    if table.len() == 1 { return *table[0].last().unwrap(); }
    let mut res = 0;
    let prev = &table[table.len()-2];
    let b = lastb;
    let htot = *hcum.last().unwrap();
    lastb = broken[broken.len()-2];
    for j in (boff-b-1..=springs.len()-b).rev() {
        if let Some(h) = _fit2(springs, j, b) {
            if h + htot - hcum[j-1] == b { res += prev[j-lastb-1]; }
        }
    }

    // dbg!(&table);
    // *table.last().unwrap().last().unwrap()
    res
}

fn solve() -> u64 {
    let mut res = 0;
    for line in std::io::stdin().lines() {
        let line = line.unwrap();
        let space = line.find(" ").unwrap();
        let springs = line[..space].as_bytes();
        let broken: Vec<usize> = line[space+1..].split(",").map(|s| s.parse().unwrap()).collect();
        // let dp = arrangements(springs, &broken);
        // let dp = dbg!(_part2(springs, &broken));
        let dp = _part2(springs, &broken);

        let springs = line[..space].replace(".", " ").into_bytes();
        let broken: Vec<u64> = line[space+1..].split(",").map(|s| s.parse().unwrap()).collect();
        let unknown = springs.iter().filter(|&&c| c == b'?').count();
        let recur = _part1(springs, 0, unknown, &broken);

        if dp != recur { dbg!(line, dp, recur); }

        res += dp;
    }
    res
}

fn main() {
    println!("Part 1: {}", solve());
}
