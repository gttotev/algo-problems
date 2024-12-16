(ns aoc2024.day15
  (:require [clojure.string :as s]))

(def grid-big
  "##########
  #..O..O.O#
  #......O.#
  #.OO..O.O#
  #..O@..O.#
  #O#..O...#
  #O..O..O.#
  #.OO.O.OO#
  #....O...#
  ##########")

(def moves-big
  "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
  vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
  ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
  <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
  ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
  ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
  >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
  <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
  ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
  v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^")

(def grid-small
  "########
  #..O.O.#
  ##@.O..#
  #...O..#
  #.#.O..#
  #...O..#
  #......#
  ########")

(def moves-small
  "<^^>>>vv<v>>v<<")

(defn prepare [g m]
  (let [vt (comp vec s/trim)]
    [(mapv vt (s/split-lines g)) (apply concat (map vt (s/split-lines m)))]))

(def movedir {\< [0 -1] \> [0 1] \^ [-1 0] \v [1 0] \[ [0 1] \] [0 -1]})
(defn walk [[grid ij] move]
  (let [md (movedir move)
        nij (map + ij md)
        try-move (fn []
                   (let [fij (some #(and (not= \O (get-in grid %)) %)
                               (iterate (partial map + md) nij))]
                     (if (= \# (get-in grid fij)) [grid ij]
                       [(-> grid (assoc-in nij \.) (assoc-in fij \O)) nij])))]
    (case (get-in grid nij)
      \. [grid nij]
      \# [grid ij]
      \O (try-move))))

(defn findatij [grid]
  (reduce-kv
    (fn [res i row]
      (if-not (nil? res) (reduced res)
        (reduce-kv (fn [_ j c] (if (= c \@) (reduced [i j]))) nil row))) nil grid))

(defn findat [grid]
  (let [ij (findatij grid)]
    [(assoc-in grid ij \.) ij]))

(defn gps [box grid]
  (map-indexed (fn [i row] (map-indexed (fn [j c] (if (= c box) (+ (* 100 i) j) 0)) row)) grid))

(defn solve [box grid]
  (->> grid
       (gps box)
       (apply concat)
       (reduce +)))

(defn solve1 [g m]
  (let [[grid moves] (prepare g m)
        [grid ij] (findat grid)
        [grid ij] (reduce walk [grid ij] moves)]
    (solve \O grid)))

(solve1 grid-small moves-small)
(solve1 grid-big moves-big)

(def grid-input (slurp "15/input-grid.txt"))
(def moves-input (slurp "15/input-moves.txt"))

(solve1 grid-input moves-input)

(defn recboxes [grid md ij]
  (let [cur (get-in grid ij)
        nij (map + md ij)
        nxt (get-in grid nij)
        bmd (movedir cur)]
    (if bmd
      (clojure.set/union
        #{ij (map + bmd ij)}
        (recboxes grid md nij)
        (if (not= cur nxt) (recboxes grid md (map + bmd nij)))))))

(defn walk2 [[grid ij] move]
  (let [md (movedir move)
        nij (map + ij md)
        try-move (fn []
                   (let [[boxes [fij]] (split-with #(#{\] \[} (get-in grid %))
                                         (rest (iterate (partial map + md) nij)))]
                     (if (= \# (get-in grid fij)) [grid ij]
                       [(-> (reduce (fn [grid [a b]] (-> grid (assoc-in a \[) (assoc-in b \])))
                                    grid (partition 2 (sort-by second (concat boxes [fij]))))
                            (assoc-in nij \.))
                        nij])))]
    (case (get-in grid nij)
      \. [grid nij]
      \# [grid ij]
      (if (zero? (first md)) (try-move)
        (let [rb (recboxes grid md nij)
              nb (map (partial map + md) rb)
              ng (map (partial get-in grid) nb)]
          (if (some #{\#} ng) [grid ij]
            [(reduce #(assoc-in %1 %2 (get-in grid (map - %2 md)))
                     (reduce #(assoc-in %1 %2 \.) grid rb) nb)
             nij]))))))

(defn solve2 [g m]
  (let [[grid moves] (prepare g m)
        [grid ij] (findat grid)
        ij (map * [1 2] ij)
        grid (mapv #(vec (mapcat (fn [x] (if (= x \O) [\[ \]] [x x])) %)) grid)
        [grid ij] (reduce walk2 [grid ij] moves)]
    (solve \[ grid)))

(solve2 "#######
        #...#.#
        #.....#
        #..OO@#
        #..O..#
        #.....#
        #######" "<vv<<^^<<^^")
(solve2 grid-big moves-big)

(solve2 grid-input moves-input)
