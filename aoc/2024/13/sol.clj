(ns aoc2024.day13)

(defn gcd [a b]
  (if (zero? a) b (recur (rem b a) a)))

(defn modinv [a b]
  (if (= a 1) 1
    (let [q1 (quot b a) r (rem b a)
          q2 (quot a r) o (rem a r)]
      (if (zero? o) (- b q1) (inc (* q1 q2))))))

(defn mbcb [[x xa xb] [ma ca]]
  [(/ (* ma xa) (- xb)) (/ (- x (* xa ca)) xb)])

(defn maca [xs]
  (println "maca")  ; never called :')
  (let [g (apply gcd (rest xs))
        [x xa xb] (map #(/ % g) xs)
        xa (rem xa xb)
        [ma ca] (if (int? x) [xb (rem (* (modinv xa xb) x) xb)] :error)
        [mb cb] (mbcb xs [ma ca])
        mink (if (< cb 0) :errork 0)
        price (+ (* (+ ma ma ma mb) mink) ca ca ca cb)]
    price))

(def sample
  "Button A: X+94, Y+34
  Button B: X+22, Y+67
  Prize: X=8400, Y=5400
  
  Button A: X+26, Y+66
  Button B: X+67, Y+21
  Prize: X=12748, Y=12176
  
  Button A: X+17, Y+86
  Button B: X+84, Y+37
  Prize: X=7870, Y=6450
  
  Button A: X+69, Y+23
  Button B: X+27, Y+71
  Prize: X=18641, Y=10279")

(defn calc-machine [xy [xa ya xb yb x y]]
  (let [x (+ x xy) y (+ y xy)
        nm (- (* x yb) (* y xb))
        dn (- (* xa yb) (* xb ya))]
    (if (zero? dn) (if (zero? nm) (maca [x xa xb]) 0)
      (let [a (/ nm dn) b (/ (- x (* a xa)) xb)]
        (if (and (int? b) (int? a)) (+ a a a b) 0)))))

(defn solve [part2? txt]
  (->> txt
    (re-seq #"Button A: X\+(\d+), Y\+(\d+)\n\s*Button B: X\+(\d+), Y\+(\d+)\n\s*Prize: X=(\d+), Y=(\d+)")
    (map (comp (partial calc-machine (if part2? 10000000000000 0)) (partial map parse-long) rest))
    (reduce +)))

(solve false sample)
(solve true sample)

(def input (slurp "13/input.txt"))
(solve false input)
(solve true input)
