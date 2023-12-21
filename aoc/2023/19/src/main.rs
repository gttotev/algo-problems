use std::collections::HashMap;

#[derive(Debug)]
enum RuleCmp { Less, More, Any }
#[derive(Debug)]
struct Rule {
    categ: usize,
    cmp: RuleCmp,
    value: u32,
    next: String,
}

fn category(c: u8) -> usize {
    match c {
        b'x' => 0,
        b'm' => 1,
        b'a' => 2,
        b's' => 3,
        _ => panic!("wat"),
    }
}

fn part1() -> u32 {
    let mut workflows = HashMap::new();
    let mut lines = std::io::stdin().lines().flatten();
    for line in lines.by_ref() {
        let ll = line.len();
        if ll == 0 { break; }
        let open = line.find("{").unwrap();
        let key = line[..open].to_owned();
        let rules: Vec<_> = line[open+1..ll-1].split(",").map(|rule| {
            if let Some(i) = rule.find(":") {
                let rb = rule.as_bytes();
                Rule {
                    categ: category(rb[0]),
                    cmp: if rb[1] == b'<' { RuleCmp::Less } else { RuleCmp::More },
                    value: rule[2..i].parse().unwrap(),
                    next: rule[i+1..].to_owned()
                }
            } else {
                Rule { categ: 0, cmp: RuleCmp::Any, value: 0, next: rule.to_owned() }
            }
        }).collect();
        workflows.insert(key, rules);
    }

    lines.map(|part| -> [u32; 4] {
        part[1..part.len()-1].split(",").map(|cat| cat[2..].parse().unwrap()).collect::<Vec<_>>().try_into().unwrap()
    }).filter(|part| {
        let mut wf = "in";
        loop {
            if wf == "A" { break true; }
            if wf == "R" { break false; }
            for rule in &workflows[wf] {
                let p = part[rule.categ];
                match rule.cmp {  // This kinda sucks. Oh well.
                    RuleCmp::Any => {
                        wf = &rule.next;
                        break;
                    }
                    RuleCmp::Less => {
                        if p < rule.value {
                            wf = &rule.next;
                            break;
                        }
                    }
                    RuleCmp::More => {
                        if p > rule.value {
                            wf = &rule.next;
                            break;
                        }
                    }
                }
            }
        }
    }).flatten().sum()
}

fn main() {
    println!("Part 1: {}", part1());
}
