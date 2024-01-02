use std::collections::{HashMap, VecDeque, HashSet};

#[derive(Debug, Clone, PartialEq)]
struct Module {
    kind: u8,
    out: Vec<String>,
    flipflop: u8,
    conjunct: HashMap<String, u8>,
}

impl Module {
    fn new() -> Module {
        Module { kind: 0, out: Vec::new(), flipflop: 0, conjunct: HashMap::new() }
    }

    fn pulse(&mut self, from: &str, pulse: u8) -> Option<u8> {
        if self.kind == b'&' {
            *self.conjunct.get_mut(from).unwrap() = pulse;
            Some(self.conjunct.values().min().unwrap() ^ 1)
        } else if self.kind == b'%' && pulse == 0 {
            self.flipflop ^= 1;
            Some(self.flipflop)
        } else {
            None
        }
    }
}

fn part1(mods: &HashMap<String, Module>) -> u64 {
    let mut res = [1000, 0];
    let mut modules = mods.clone();
    let mut pulseq = VecDeque::new();
    for _ in 0..1000 {
        for next in &mods["roadcaster"].out {
            pulseq.push_back(("roadcaster", &next[..], 0));
        }

        while let Some((from, to, pulse)) = pulseq.pop_front() {
            // println!("{} -{}-> {}", from, pulse, to);
            res[pulse as usize] += 1;
            if let Some(p) = modules.get_mut(to).unwrap().pulse(from, pulse) {
                for next in &mods[to].out {
                    pulseq.push_back((to, next, p));
                }
            }
        }
    }

    res[0] * res[1]
}

fn gcd(a: u64, b: u64) -> u64 {
    if b == 0 {
        a
    } else {
        gcd(b, a % b)
    }
}

fn lcm(a: u64, b: u64) -> u64 {
    (a / gcd(a, b)) * b
}

fn part2(mods: &HashMap<String, Module>) -> u64 {
    if !mods.contains_key("rx") { return 0; }
    let mut res = 1;
    let mut modules = mods.clone();
    let mut pulseq = VecDeque::new();
    let mut invs: HashSet<_> = mods[  // Assume [invs] -> conj -> rx
        mods["rx"].conjunct.keys().next().unwrap()
    ].conjunct.keys().collect();
    for i in 1..=1000000 {
        for next in &mods["roadcaster"].out {
            pulseq.push_back(("roadcaster", next, 0));
        }

        while let Some((from, to, pulse)) = pulseq.pop_front() {
            if let Some(p) = modules.get_mut(to).unwrap().pulse(from, pulse) {
                for next in &mods[to].out {
                    pulseq.push_back((to, next, p));
                }
                if invs.contains(to) && p == 1 {
                    res = lcm(res, i);
                    invs.remove(to);
                }
            }
        }
        if invs.is_empty() { break; }
    }

    res
}

fn main() {
    let mut modules = HashMap::new();
    for line in std::io::stdin().lines().flatten() {
        let mid = line.find('>').unwrap();
        let name = &line[1..mid-2];
        if !modules.contains_key(name) {
            modules.insert(name.to_owned(), Module::new());
        }
        modules.get_mut(name).unwrap().kind = line.as_bytes()[0];
        for next in line[mid+2..].split(", ") {
            modules.get_mut(name).unwrap().out.push(next.to_owned());
            if !modules.contains_key(next) {
                modules.insert(next.to_owned(), Module::new());
            }
            modules.get_mut(next).unwrap().conjunct.insert(name.to_owned(), 0);
        }
    }

    println!("Part 2: {}", part2(&modules));
    println!("Part 1: {}", part1(&modules));
}
