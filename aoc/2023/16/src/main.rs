const MIRROR_FWD: [u8; 4] = [8, 4, 2, 1];
const MIRROR_BWD: [u8; 4] = [4, 8, 1, 2];
fn step(i: usize, j: usize, dir: u8) -> (usize, usize) {
    match dir {
        1 => (i, j+1),
        2 => (i, j-1),
        4 => (i+1, j),
        8 => (i-1, j),
        _ => panic!("wat"),
    }
}

fn bounce((i, j): (usize, usize), dir: u8, grid: &[Vec<u8>], gdim: (usize, usize), beams: &mut [u8]) -> u64 {
    if i == 0 || j == 0 { return 0; }
    let bi = (i-1) * gdim.0 + j-1;
    if i > gdim.0 || j > gdim.1 || beams[bi] & dir != 0 { return 0; }

    let mut res = if beams[bi] == 0 { 1 } else { 0 };
    beams[bi] |= dir;

    let ndir = match grid[i-1][j-1] {
        b'/' => MIRROR_FWD[dir.ilog2() as usize],
        b'\\' => MIRROR_BWD[dir.ilog2() as usize],
        b'-' if dir == 4 || dir == 8 => {
            res += bounce(step(i, j, 1), 1, grid, gdim, beams);
            2
        }
        b'|' if dir == 1 || dir == 2 => {
            res += bounce(step(i, j, 4), 4, grid, gdim, beams);
            8
        }
        _ => dir
    };

    res + bounce(step(i, j, ndir), ndir, grid, gdim, beams)
}

fn main() {
    let grid: Vec<_> = std::io::stdin().lines().flatten().map(|s| s.into_bytes()).collect();
    let gdim = (grid.len(), grid[0].len());
    let mut beams = vec![0; gdim.0 * gdim.1];

    let part2 =
    (1..=gdim.0).flat_map(|i| [(i, 1, 1), (i, gdim.1, 2)]).chain(
    (1..=gdim.1).flat_map(|j| [(1, j, 4), (gdim.0, j, 8)])).map(|(i, j, dir)| {
        let res = bounce((i, j), dir, &grid, gdim, &mut beams);
        beams.fill(0);
        res
    }).max().unwrap();
    println!("Part 2: {}", part2);

    println!("Part 1: {}", bounce((1, 1), 1, &grid, gdim, &mut beams));
}
