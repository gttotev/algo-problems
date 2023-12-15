fn fits(springs: &[u8], j: usize, b: usize) -> Option<usize> {
    let mut h = 0;
    if springs[j..j+b].iter().all(|&c| { if c == b'#' { h += 1; } c != b'.' })
        && (j == 0 || springs[j-1] != b'#')
        && (j+b == springs.len() || springs[j+b] != b'#')
    {
        Some(h)
    } else { None }
}

fn arrange(springs: &[u8], broken: &[usize], hcum: &[usize], memotab: &mut Vec<Vec<Option<u64>>>) -> u64 {
    let (si, bi) = (springs.len(), broken.len());
    if memotab[bi][si] == None {
        memotab[bi][si] = Some(if broken.len() == 0 {
            if hcum.len() > 0 && hcum[0] > 0 { 0 } else { 1 }
        } else if springs.len() < broken[0] {
            0
        } else {
            let slen = springs.len();
            let b = broken[0];
            let mut res = 0;
            for j in 0..=springs.len()-b {
                if let Some(h) = fits(springs, j, b) {
                    let sn = slen.min(j+b+1);
                    if h == hcum[0] - *hcum.get(sn).unwrap_or(&0) {
                        res += arrange(&springs[sn..], &broken[1..], &hcum[sn..], memotab);
                    }
                }
                if springs[j] == b'#' { break; }
            }
            res
        })
    }
    memotab[bi][si].unwrap()
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

        let mut hcum: Vec<usize> = springs.iter().rev().scan(0, |state, &s| {
            if s == b'#' { *state += 1; }
            Some(*state)
        }).collect();
        hcum.reverse();
        let mut memotab = vec![vec![None; springs.len()+1]; broken.len()+1];
        let dp = arrange(&springs, &broken, &hcum, &mut memotab);

        res += dp;
    }
    res
}

fn main() {
    println!("Part 2: {}", solve(true));
    // println!("Part 1: {}", solve(false));
}
