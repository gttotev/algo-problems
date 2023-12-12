use std::{io::{self, BufRead}, collections::HashMap};

fn _part1() -> u64 {
    let mut lines = io::stdin().lock().lines();
    let path = lines.next().unwrap().unwrap().into_bytes();
    lines.next();

    let mut network = HashMap::new();
    for line in lines {
        let line = line.unwrap();
        network.insert(line[..3].to_owned(), (line[7..10].to_owned(), line[12..15].to_owned()));
    }
    
    let mut res = 0;
    let mut node = "AAA";
    while node != "ZZZ" {
        let next = network.get(node).unwrap();
        node = if path[res % path.len()] == b'L' { &next.0 } else { &next.1 };
        res += 1;
    }

    res as u64
}

type Node = [u8; 3];

fn part2() -> u64 {
    let mut lines = io::stdin().lock().lines();
    let path = lines.next().unwrap().unwrap().into_bytes();
    lines.next();

    let mut network = HashMap::new();
    let mut nodes = Vec::new();
    for line in lines {
        let line = line.unwrap();
        let node: Node = line[..3].as_bytes().try_into().unwrap();
        let lnode: Node = line[7..10].as_bytes().try_into().unwrap();
        let rnode: Node = line[12..15].as_bytes().try_into().unwrap();
        network.insert(node, [lnode, rnode]);
        if node[2] == b'A' {
            nodes.push(node);
        }
    }
    
    let mut res = 0;
    let mut done = false;
    while !done {
        let p = if path[res % path.len()] == b'L' { 0 } else { 1 };
        res += 1;
        done = true;
        for node in &mut nodes {
            *node = network.get(node).unwrap()[p];
            if node[2] != b'Z' {
                done = false;
            }
        }
    }

    res as u64
}

fn main() {
    println!("Part 2: {}", part2());
    // println!("Part 1: {}", _part1());
}
