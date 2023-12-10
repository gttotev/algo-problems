use std::{io::{self, BufRead}, collections::HashSet};

fn part1() -> i32 {
    io::stdin().lock().lines().map(|line| {
        let line = line.unwrap();
        let i = line.find(":").unwrap();
        let j = line.find("|").unwrap();
        let wins: HashSet<i32> = line[i+2..j-1].split_ascii_whitespace().map(|s| s.parse::<i32>().unwrap()).collect();
        let guess: HashSet<i32> = line[j+2..].split_ascii_whitespace().map(|s| s.parse::<i32>().unwrap()).collect();
        let matches = wins.intersection(&guess).count();
        if matches > 0 { 1 << (matches - 1) } else { 0 }
    }).fold(0, |acc, x| acc + x)
}

fn part2() -> usize {
    let matches: Vec<usize> = io::stdin().lock().lines().map(|line| {
        let line = line.unwrap();
        let i = line.find(":").unwrap();
        let j = line.find("|").unwrap();
        let wins: HashSet<i32> = line[i+2..j-1].split_ascii_whitespace().map(|s| s.parse::<i32>().unwrap()).collect();
        let guess: HashSet<i32> = line[j+2..].split_ascii_whitespace().map(|s| s.parse::<i32>().unwrap()).collect();
        wins.intersection(&guess).count()
    }).collect();

    let mut cards = vec![1; matches.len()];
    for (i, m) in matches.iter().enumerate() {
        for j in i+1..i+1+m {
            cards[j] += cards[i];
        }
    }

    cards.iter().fold(0, |acc, x| acc + x)
}

fn main() {
    println!("Part 2: {}", part2());
    println!("Part 1: {}", part1());
}
