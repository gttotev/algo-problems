use std::collections::HashMap;

#[derive(Debug)]
enum RuleCmp { Less, More, Any }
#[derive(Debug)]
struct Rule {
    categ: usize,
    cmp: RuleCmp,
    value: u16,
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

fn part1(lines: impl Iterator<Item = String>, workflows: &HashMap<String, Vec<Rule>>) -> u32 {
    lines.map(|part| -> [u32; 4] {
        part[1..part.len()-1].split(",").map(|cat| cat[2..].parse().unwrap()).collect::<Vec<_>>().try_into().unwrap()
    }).filter(|part| {
        let mut wf = "in";
        while wf != "A" && wf != "R" {
            wf = if let Some(rule) = workflows[wf].iter().find(|&rule| {
                let p = part[rule.categ];
                match rule.cmp {
                    RuleCmp::Less if p < rule.value as u32 => true,
                    RuleCmp::More if p > rule.value as u32 => true,
                    _ => false,
                }
            }) {
                &rule.next
            } else {
                &workflows[wf].last().unwrap().next
            };
        }
        wf == "A"
    }).flatten().sum()
}

type Constraint = [(u16, u16); 4];
fn apply_rule(mut cst: Constraint, rule: &Rule) -> Constraint {
    match rule.cmp {
        RuleCmp::Any => (),
        RuleCmp::Less => cst[rule.categ].1 = rule.value-1,
        RuleCmp::More => cst[rule.categ].0 = rule.value+1,
    }
    cst
}
fn apply_rule_inv(mut cst: Constraint, rule: &Rule) -> Constraint {
    match rule.cmp {
        RuleCmp::Any => (),
        RuleCmp::Less => cst[rule.categ].0 = rule.value,
        RuleCmp::More => cst[rule.categ].1 = rule.value,
    }
    cst
}

fn part2(mut cst: Constraint, wf: &str, workflows: &HashMap<String, Vec<Rule>>) -> u64 {
    if wf == "R" || cst.iter().any(|c| c.0 > c.1) { return 0; }
    if wf == "A" { return cst.iter().map(|c| c.1 as u64 - c.0 as u64 + 1).product(); }

    workflows[wf].iter().map(|rule| {
        let res = part2(apply_rule(cst, rule), &rule.next, &workflows);
        cst = apply_rule_inv(cst, rule);
        res
    }).sum()  // Assume constraints are mutually exclusive
}

fn main() {
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
                    next: rule[i+1..].to_owned(),
                }
            } else {
                Rule { categ: 0, cmp: RuleCmp::Any, value: 0, next: rule.to_owned() }
            }
        }).collect();
        workflows.insert(key, rules);
    }

    println!("Part 2: {}", part2([(1, 4000); 4], "in", &workflows));
    println!("Part 1: {}", part1(lines, &workflows));
}
