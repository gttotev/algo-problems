use std::io::{self, BufRead};

enum ReadMode {
    Free,
    DigitRead(usize),
    DigitSeek(usize),
}

fn is_symbol(s: &str, i: usize) -> bool {
    let c = s.as_bytes()[i];
    !c.is_ascii_alphanumeric() && c != b'.'
}

fn check_symbol_above_below(rows: &Vec<String>, i: usize, j: usize) -> bool {
    i > 0 && is_symbol(&rows[i-1], j) || i < rows.len()-1 && is_symbol(&rows[i+1], j)
}

fn part1() {
    let mut res = 0;
    let rows: Vec<String> = io::stdin().lock().lines().map(|line| line.unwrap()).collect();
    for (i, line) in rows.iter().enumerate() {
        let mut mode = ReadMode::Free;
        for (j, c) in line.as_bytes().iter().enumerate() {
            loop { match mode {
                ReadMode::DigitSeek(from) => {
                    if !c.is_ascii_digit() {
                        mode = ReadMode::Free;
                        res += line[from..j].parse::<i32>().unwrap();
                    }
                    break;
                }
                ReadMode::Free => {
                    if c.is_ascii_digit() {
                        mode = ReadMode::DigitRead(j);
                        if j > 0 && (is_symbol(line, j-1) || check_symbol_above_below(&rows, i, j-1)) {
                            mode = ReadMode::DigitSeek(j);
                            break;
                        }
                        continue;
                    }
                    break;
                }
                ReadMode::DigitRead(from) => {
                    if is_symbol(line, j) || check_symbol_above_below(&rows, i, j) {
                        mode = ReadMode::DigitSeek(from);
                        continue;
                    }
                    if !c.is_ascii_digit() {
                        mode = ReadMode::Free;
                    }
                    break;
                }
            } }
        }
        if let ReadMode::DigitSeek(i) = mode {
            res += line[i..].parse::<i32>().unwrap();
        }
    }
    println!("Part1: {}", res);
}

fn part2() {
    let mut stars: Vec<(usize, usize)> = Vec::new();
    let mut numbs: Vec<(u64, usize, usize, usize)> = Vec::new();

    let rows: Vec<String> = io::stdin().lock().lines().map(|line| line.unwrap()).collect();
    for (i, line) in rows.iter().enumerate() {
        let mut mode = ReadMode::Free;
        for (j, c) in line.as_bytes().iter().enumerate() {
            if *c == b'*' {
                stars.push((i, j));
            }
            match mode {
                ReadMode::DigitSeek(from) => {
                    if !c.is_ascii_digit() {
                        mode = ReadMode::Free;
                        numbs.push((line[from..j].parse::<u64>().unwrap(), i, from, j));
                    }
                }
                ReadMode::Free => {
                    if c.is_ascii_digit() {
                        mode = ReadMode::DigitSeek(j);
                    }
                }
                _ => ()
            }
        }
        if let ReadMode::DigitSeek(from) = mode {
            numbs.push((line[from..].parse::<u64>().unwrap(), i, from, line.len()));
        }
    }

    let mut res = 0u64;
    for star in stars {
        let mut next = 0;
        let mut prod = 1u64;
        for numb in numbs.iter() {
            if star.0.abs_diff(numb.1) < 2 && numb.2 < star.1 + 2 && star.1 < numb.3 + 1 {
                next += 1;
                prod *= numb.0;
            }
        }
        if next == 2 {
            res += prod;
        }
    }
    println!("Part2: {}", res);
}

fn main() {
    part2();
    part1();
}
