use std::{io, collections::HashSet};

fn solve(expansion_factor: usize) -> usize {
    let mut galaxies = Vec::new();
    let mut galaxy_rows = HashSet::new();
    let mut galaxy_cols = HashSet::new();
    for (i, line) in io::stdin().lines().enumerate() {
        let line = line.unwrap().into_bytes();
        for (j, _) in line.iter().enumerate().filter(|(_, &c)| c == b'#') {
            galaxies.push((i, j));
            galaxy_rows.insert(i);
            galaxy_cols.insert(j);
        }
    }
    
    let xx = expansion_factor - 1;
    let mut res = 0;
    for (i, ga) in galaxies.iter().enumerate() {
        for gb in &galaxies[i+1..] {
            let ir = if ga.0 < gb.0 { ga.0..gb.0 } else { gb.0..ga.0 };
            let jr = if ga.1 < gb.1 { ga.1..gb.1 } else { gb.1..ga.1 };
            let ix = ir.clone().filter(|i| !galaxy_rows.contains(i)).count();
            let jx = jr.clone().filter(|j| !galaxy_cols.contains(j)).count();
            res += ir.len() + jr.len() + ix * xx + jx * xx;
        }
    }

    res
}

fn main() {
    println!("Part 2: {}", solve(1000000));
    println!("Part 1: {}", solve(2));
}
