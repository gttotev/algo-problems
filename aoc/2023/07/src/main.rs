use std::io::BufRead;

#[derive(Debug, Eq, Ord, PartialEq, PartialOrd)]
enum HandType {
    HighCard,
    OnePair,
    TwoPair,
    ThreeKind,
    FullHouse,
    FourKind,
    FiveKind,
}

#[derive(Debug, Eq, Ord, PartialEq, PartialOrd)]
struct Hand {
    htype: HandType,
    cards: [u8; 5],
    bid: u64,
}

impl Hand {
    #[allow(dead_code)]
    fn new1(cards: &[u8; 5], bid: u64) -> Hand {
        let cards = cards.map(|c| match c {
            b'T' => 10,
            b'J' => 11,
            b'Q' => 12,
            b'K' => 13,
            b'A' => 14,
            _ => c - b'0',
        });

        let mut counts = [0, 0, 0, 0, 0, 0];
        let mut ccards = cards.clone();
        ccards.sort();

        let mut prevc = (0, 0);
        for (i, c) in ccards.into_iter().enumerate() {
            if c == prevc.0 {
                prevc.1 += 1;
                continue;
            }
            counts[prevc.1] = prevc.0;
            if prevc.1 == 2 && i <= 3 {
                counts[0] = prevc.0;
            }
            prevc = (c, 1);
        }
        counts[prevc.1] = prevc.0;

        let htype = if counts[5] > 0 {
            HandType::FiveKind
        } else if counts[4] > 0 {
            HandType::FourKind
        } else if counts[3] > 0 {
            if counts[2] > 0 { HandType::FullHouse } else { HandType::ThreeKind }
        } else if counts[2] > 0 {
            if counts[0] > 0 && counts[0] != counts[2] {
                HandType::TwoPair
            } else { HandType::OnePair }
        } else {
            HandType::HighCard
        };

        Hand { htype, bid, cards }
    }

    fn new(cards: &[u8; 5], bid: u64) -> Hand {
        let mut jokers = 0;
        let cards = cards.map(|c| match c {
            b'J' => { jokers += 1; 1 },
            b'T' => 10,
            b'Q' => 12,
            b'K' => 13,
            b'A' => 14,
            _ => c - b'0',
        });

        let mut counts = [0, 0, 0, 0, 0, 0];
        let mut ccards = cards.clone();
        ccards.sort();

        let mut prevc = (0, 0);
        for (i, c) in ccards.into_iter().enumerate() {
            if c == prevc.0 {
                prevc.1 += 1;
                continue;
            }
            counts[prevc.1] = prevc.0;
            if prevc.1 == 2 && i <= 3 {
                counts[0] = prevc.0;
            }
            prevc = (c, 1);
        }
        counts[prevc.1] = prevc.0;

        let htype = if counts[5] > 0 {
            HandType::FiveKind
        } else if counts[4] > 0 {
            if jokers > 0 { HandType::FiveKind } else { HandType::FourKind }
        } else if counts[3] > 0 {
            if counts[2] > 0 {
                if jokers > 0 { HandType::FiveKind } else { HandType::FullHouse }
            } else {
                if jokers > 0 { HandType::FourKind } else { HandType::ThreeKind }
            }
        } else if counts[2] > 0 {
            if counts[0] > 0 && counts[0] != counts[2] {
                match jokers {
                    2 => HandType::FourKind,
                    1 => HandType::FullHouse,
                    _ => HandType::TwoPair,
                }
            } else {
                if jokers > 0 { HandType::ThreeKind } else { HandType::OnePair }
            }
        } else {
            if jokers > 0 { HandType::OnePair } else { HandType::HighCard }
        };

        Hand { htype, bid, cards }
    }
}

fn solve() -> u64 {
    let mut hands: Vec<_> = std::io::stdin().lock().lines().map(|line| {
        let line = line.unwrap();
        Hand::new(line[..5].as_bytes().try_into().unwrap(), line[6..].parse().unwrap())
    }).collect();
    hands.sort_unstable();
    hands.iter().enumerate().fold(0, |acc, (i, hand)| acc + (i+1) as u64 * hand.bid)
}

fn main() {
    println!("Part 2: {}", solve());
}
