fn part1() -> u64 {
    let mut os = 0;
    let mut sub = 0;
    let mut rows = 0;
    let mut coli = [0; 128];
    for line in std::io::stdin().lines().flatten() {
        for (j, &c) in line.as_bytes().iter().enumerate() {
            if c == b'O' {
                os += 1;
                sub += coli[j];
                coli[j] += 1;
            } else if c == b'#' {
                coli[j] = rows + 1;
            }
        }

        rows += 1;
    }
    rows * os - sub
}

fn main() {
    println!("Part 1: {}", part1());
}
