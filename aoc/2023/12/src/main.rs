fn arrangements(mut springs: Vec<u8>, i: usize, unknown: usize, broken: &[u64]) -> u64 {
    if unknown == 0 {
        if String::from_utf8(springs).unwrap().split_ascii_whitespace().map(|s| s.len() as u64).collect::<Vec<_>>() == broken {
            1
        } else {
            0
        }
    } else if springs[i] != b'?' {
        arrangements(springs, i + 1, unknown, broken)
    } else {
        springs[i] = b'#';
        let broke = arrangements(springs.clone(), i+1, unknown-1, broken);
        springs[i] = b' ';
        broke + arrangements(springs, i+1, unknown-1, broken)
    }
}

fn part1() -> u64 {
    let mut res = 0;
    for line in std::io::stdin().lines() {
        let line = line.unwrap();
        let space = line.find(" ").unwrap();
        let springs = line[..space].replace(".", " ").into_bytes();
        let broken: Vec<u64> = line[space+1..].split(",").map(|s| s.parse().unwrap()).collect();
        let unknown = springs.iter().filter(|&&c| c == b'?').count();
        res += arrangements(springs, 0, unknown, &broken);
    }

    res
}

fn main() {
    println!("Part 1: {}", part1());
}
