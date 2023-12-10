fn solve(races: &[(f64, f64)]) -> i64 {
    let mut res = 1;
    for (t, r) in races {
        let sqt = (t*t - 4.*r).sqrt();
        let h = (t + sqt) / 2. - 1.;
        let l = (t - sqt) / 2. + 1.;
        res *= h.ceil() as i64 - l as i64 + 1;
    }
    res
}

const SAMPLE_P1: [(f64, f64); 3] = [(7., 9.), (15., 40.), (30., 200.)];
const INPUT_P1:  [(f64, f64); 4] = [
    (54., 446.), (81., 1292.), (70., 1035.), (88., 1007.),
];
const SAMPLE_P2:  [(f64, f64); 1] = [(71530., 940200.)];
const INPUT_P2:  [(f64, f64); 1] = [(54817088., 446129210351007.)];

fn main() {
    println!("Sample Part 1: {}", solve(&SAMPLE_P1));
    println!("Solution Part 1: {}", solve(&INPUT_P1));
    println!("Sample Part 2: {}", solve(&SAMPLE_P2));
    println!("Solution Part 2: {}", solve(&INPUT_P2));
}
