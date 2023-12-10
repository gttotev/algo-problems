use std::io::{self, BufRead};

#[derive(Debug)]
struct MapRange {
    dst: u64,
    src: u64,
    len: u64,
}

impl MapRange {
    fn try_apply(&self, value: u64) -> Option<u64> {
        if value < self.src || value >= self.src + self.len {
            None
        } else {
            Some(value - self.src + self.dst)
        }
    }
}

#[derive(Debug)]
struct Mapper {
    ranges: Vec<MapRange>,
}

impl Mapper {
    fn new() -> Self {
        Mapper { ranges: Vec::new() }
    }

    fn add_range(&mut self, range: MapRange) {
        self.ranges.push(range);
    }

    fn apply(&self, value: u64) -> u64 {
        self.ranges.iter().find_map(|r| r.try_apply(value)).unwrap_or(value)
    }
}

#[derive(Debug)]
struct Pipeline {
    stages: Vec<Mapper>,
}

impl Pipeline {
    fn new() -> Self {
        Pipeline { stages: Vec::new() }
    }

    fn add_stage(&mut self, stage: Mapper) {
        self.stages.push(stage);
    }

    fn run(&self, value: u64) -> u64 {
        self.stages.iter().fold(value, |v, stage| stage.apply(v))
    }
}

fn part1() -> u64 {
    let mut lines = io::stdin().lock().lines();

    let seeds: Vec<u64> = lines.next().unwrap().unwrap()[7..].split_ascii_whitespace().map(|s| s.parse().unwrap()).collect();
    lines.next();

    let mut pipe = Pipeline::new();
    let mut stage = Mapper::new();
    for line in lines {
        let line = line.unwrap();
        if line.len() == 0 {
            pipe.add_stage(stage);
            stage = Mapper::new();
            continue;
        }
        if !line.as_bytes()[0].is_ascii_digit() { continue; }
        let i = line.find(" ").unwrap();
        let j = line[i+1..].find(" ").unwrap() + i+1;
        stage.add_range(MapRange { dst: line[..i].parse().unwrap(), src: line[i+1..j].parse().unwrap(), len: line[j+1..].parse().unwrap() })
    }
    pipe.add_stage(stage);

    seeds.iter().map(|seed| pipe.run(*seed)).min().unwrap()
}

fn part2() -> u64 {
    let mut lines = io::stdin().lock().lines();

    let seeds: Vec<u64> = lines.next().unwrap().unwrap()[7..].split_ascii_whitespace().map(|s| s.parse().unwrap()).collect();
    lines.next();

    let mut pipe = Pipeline::new();
    let mut stage = Mapper::new();
    for line in lines {
        let line = line.unwrap();
        if line.len() == 0 {
            pipe.add_stage(stage);
            stage = Mapper::new();
            continue;
        }
        if !line.as_bytes()[0].is_ascii_digit() { continue; }
        let i = line.find(" ").unwrap();
        let j = line[i+1..].find(" ").unwrap() + i+1;
        stage.add_range(MapRange { dst: line[..i].parse().unwrap(), src: line[i+1..j].parse().unwrap(), len: line[j+1..].parse().unwrap() })
    }
    pipe.add_stage(stage);

    seeds.chunks(2).flat_map(|seed_range| seed_range[0]..seed_range[0]+seed_range[1]).map(|seed| pipe.run(seed)).min().unwrap()
}

fn main() {
    println!("Part 2: {}", part2());
    // println!("Part 1: {}", part1());
}
