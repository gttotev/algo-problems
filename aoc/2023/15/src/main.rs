fn _part1() -> u64 {
    let mut buf = String::new();
    let _ = std::io::stdin().read_line(&mut buf);

    buf.trim().split(",").map(
        |s| s.as_bytes().iter().fold(
            0,
            |acc, &c| (acc + c as u64)*17 % 256,
        )
    ).sum()
}

fn part2() -> u64 {
    let mut buf = String::new();
    let _ = std::io::stdin().read_line(&mut buf);

    const VEC: Vec<(&[u8], u64)> = Vec::new();
    let mut boxes = [VEC; 256];
    for cmd in buf.trim().split(",") {
        let cmd = cmd.as_bytes();
        let cl = cmd.len();
        let label = &cmd[..cl - if cmd[cl-1] == b'-' { 1 } else { 2 }];
        let boxx = &mut boxes[label.iter().fold(0, |acc, &c| (acc + c as usize)*17 % 256)];
        let pos = boxx.iter().position(|&(lab, _)| lab == label);
        if cmd[cl-1] == b'-' {
            if let Some(i) = pos {
                boxx.remove(i);
            }
        } else {
            let foc = (cmd[cl-1] - b'0') as u64;
            if let Some(i) = pos {
                boxx[i].1 = foc
            } else {
                boxx.push((label, foc));
            }
        }
    }

    boxes.iter().enumerate().flat_map(|(i, b)| {
        b.iter().enumerate().map(move |(j, &(_, foc))| {
            (i+1) as u64 * (j+1) as u64 * foc
        })
    }).sum()
}

fn main() {
    println!("Part 2: {}", part2());
    // println!("Part 1: {}", _part1());
}
