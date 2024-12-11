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
    {} (map-indexed vector grid))))

(defn find-antinodes [a b]
  (let [d (map - b a)
        y (map + b d)
        x (map - a d)]
    [x y]))

(defn icombos [n]
  (mapcat #(map vector (repeat %) (range (inc %) n)) (range n)))

(defn combos [xs]
  (map #(map xs %) (icombos (count xs))))

(defn valid? [grid]
  (let [ii (count grid) jj (count (first grid))]
    (fn [[i j]]
      (and (>= i 0) (< i ii)
           (>= j 0) (< j jj)))))

(defn solve [txt]
  (let [grid (prepare txt)]
    (->> grid
         find-towers
         (#(update-vals % combos))
         vals
         (apply concat)
         (mapcat (partial apply find-antinodes))
         (filter (valid? grid))
         set
         count)))

(solve sample)

(def input (slurp "08/input.txt"))
(solve input)

(comment
  (def grid (prepare sample))
  (def towers (find-towers grid))
  (def paired-towers (update-vals towers combos))
  (def allpairs (apply concat (vals paired-towers)))
  (->> (mapcat (partial apply find-antinodes) allpairs)
       (filter (valid? grid))
       set
       count)
  (find-antinodes [3 4] [5 5])
  ,)
