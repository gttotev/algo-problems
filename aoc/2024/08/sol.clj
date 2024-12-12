(def sample
  "............
........0...
.....0......
.......0....
....0.......
......A.....
............
............
........A...
.........A..
............
............")

(defn prepare [txt]
  (->> txt
       clojure.string/split-lines
       (map vec)
       vec))

(defn find-towers [grid]
  (reduce
    (fn [res [i row]]
      (reduce
        (fn [res [j c]]
          (if (= c \.) res
            (assoc res c (conj (res c []) [i j]))))
        res (map-indexed vector row)))
    {} (map-indexed vector grid)))

(defn find-antinodes [a b]
  (let [d (map - b a)
        y (map + b d)
        x (map - a d)]
    [x y]))

(defn find-all-antinodes [valid? a b]
  (let [d (map - b a)]
    (concat
      (take-while valid?
                  (iterate #(map + % d) b))
      (take-while valid?
                  (iterate #(map - % d) a)))))

(defn icombos [n]
  (mapcat #(map vector (repeat %) (range (inc %) n)) (range n)))

(defn combos [xs]
  (map #(map xs %) (icombos (count xs))))

(defn valid? [grid]
  (let [ii (count grid) jj (count (first grid))]
    (fn [[i j]]
      (and (>= i 0) (< i ii)
           (>= j 0) (< j jj)))))

(defn solve1 [txt]
  (let [grid (prepare txt)]
    (->> grid
         find-towers
         vals
         (mapcat combos)
         (mapcat (partial apply find-antinodes))
         (filter (valid? grid))
         set
         count)))

(defn solve2 [txt]
  (let [grid (prepare txt)]
    (->> grid
         find-towers
         vals
         (mapcat combos)
         (mapcat (partial apply (partial find-all-antinodes (valid? grid))))
         set
         count)))

(solve1 sample)
(solve2 sample)

(def input (slurp "08/input.txt"))
(solve1 input)
(solve2 input)

(comment
  (def T
    "T....#....
...T......
.T....#...
.........#
..#.......
..........
...#......
..........
....#.....
..........")
  (def grid (prepare T))
  (def alltowers (find-towers grid))
  (def towers (dissoc alltowers \#))
  (def pairs (->> towers vals (mapcat combos)))
  (def anodes (set (mapcat (partial apply (partial find-all-antinodes (valid? grid))) pairs)))
  (count anodes)
  ,)
