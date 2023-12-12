use std::io;

fn predict(seq: Vec<i32>, part1: bool) -> i32 {
    if seq.iter().all(|x| *x == 0) {
        return 0;
    }
    let next = predict(seq.windows(2).map(|w| w[1] - w[0]).collect(), part1);
    if part1 { seq.last().unwrap() + next } else { seq.first().unwrap() - next }
}

fn solve(part1: bool) -> i32 {
    io::stdin().lines()
        .map(|line| predict(line.unwrap().split_ascii_whitespace().map(|s| s.parse().unwrap()).collect(), part1))
        .fold(0, |acc, x| acc + x)
}

fn main() {
    println!("Part 2: {}", solve(false));
    // println!("Part 1: {}", solve(true));
}
