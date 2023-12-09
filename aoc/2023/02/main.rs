use std::{
    cmp::max,
    io::{self, BufRead},
};

fn part1() {
    let mut res = 0;
    let stdin = io::stdin();
    for (i, line) in stdin.lock().lines().enumerate() {
        let mut cmp = 0;
        for tok in line.unwrap().split_whitespace() {
            if cmp == 0 {
                cmp = tok.parse().unwrap_or(-1);
                continue;
            }
            if cmp > if tok.starts_with("red") {
                12
            } else if tok.starts_with("green") {
                13
            } else { 14 } {
                break;
            }
            cmp = 0;
        }
        if cmp == 0 {
            res += i+1;
        }
    }
    println!("{}", res);
}

fn part2() {
    let mut res = 0;
    let stdin = io::stdin();
    for line in stdin.lock().lines() {
        let mut cmp = 0;
        let mut r = 0;
        let mut g = 0;
        let mut b = 0;
        for tok in line.unwrap().split_whitespace() {
            if cmp == 0 {
                cmp = tok.parse().unwrap_or(-1);
                continue;
            }
            if tok.starts_with("red") {
                r = max(r, cmp);
            } else if tok.starts_with("green") {
                g = max(g, cmp);
            } else {
                b = max(b, cmp);
            }
            cmp = 0;
        }
        if cmp == 0 {
            res += r * g * b;
        }
    }
    println!("{}", res);
}

fn main() {
    // part1();
    part2();
}
