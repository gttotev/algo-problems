fn _reflect_part1(arr: &[u64]) -> u64 {
    let len = arr.len();
    for i in 1..len {
        let mut found = true;
        for j in 0.. {
            if j >= i || i + j >= len { break; }
            if arr[i+j] != arr[i-j-1] {
                found = false;
                break;
            }
        }
        if found { return i as u64; }
    }
    0
}

fn _reflect_part2(arr: &[u64]) -> u64 {
    let len = arr.len();
    for i in 1..len {
        let mut found = false;
        for j in 0.. {
            if j >= i || i + j >= len { break; }
            let d = arr[i+j] ^ arr[i-j-1];
            if d.is_power_of_two() && !found {
                found = true;
            } else if d > 0 {
                found = false;
                break;
            }
        }
        if found { return i as u64; }
    }
    0
}

fn solve(reflect: fn (&[u64]) -> u64) -> u64 {
    let mut res = 0;
    let mut rows = Vec::new();
    let mut cols = Vec::new();
    for line in std::io::stdin().lines() {
        let line = line.unwrap();
        if line.is_empty() { // IMPORTANT: ensure trailing NL!!
            res += 100 * reflect(&rows) + reflect(&cols);
            rows.clear();
            cols.clear();
            continue;
        }

        let mut r = 0;
        for (j, &c) in line.as_bytes().iter().enumerate() {
            if cols.len() == j { cols.push(0); }
            cols[j] <<= 1;
            r <<= 1;
            if c == b'#' {
                cols[j] += 1;
                r += 1;
            }
        }
        rows.push(r);
    }
    res
}

fn main() {
    println!("Part 2: {}", solve(_reflect_part2));
    // println!("Part 1: {}", solve(_reflect_part1));
}
