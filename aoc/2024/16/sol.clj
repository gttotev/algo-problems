(ns aoc2024.day16
  (:require [clojure.string :as s]))

(def sample
  "###############
  #.......#....E#
  #.#.###.#.###.#
  #.....#.#...#.#
  #.###.#####.#.#
  #.#.#.......#.#
  #.#.#####.###.#
  #...........#.#
  ###.#.#####.#.#
  #...#.....#.#.#
  #.#.#.###.#.#.#
  #.....#...#.#.#
  #.###.#.#.#.#.#
  #S..#.....#...#
  ###############")

(def sample2
  "#################
#...#...#...#..E#
#.#.#.#.#.#.#.#.#
#.#.#.#...#...#.#
#.#.#.#.###.#.#.#
#...#.#.#.....#.#
#.#.#.#.#.#####.#
#.#...#.#.#.....#
#.#.#####.#.###.#
#.#.#.......#...#
#.#.###.#####.###
#.#.#...#.....#.#
#.#.#.#####.###.#
#.#.#.........#.#
#.#.#.#########.#
#S#.............#
#################")

(defn prepare [txt]
  (->> txt
       s/split-lines
       (mapv (comp vec s/trim))))

(def $inf Long/MAX_VALUE)
(def movedir [[0 1] [1 0] [0 -1] [-1 0]])

(do
  (defn dijkstra [grid found news]
    (if (empty? news) found
      (let [mink (apply min-key news (keys news))
            minv (news mink)
            [md & ij] mink
            rm (mod (inc md) 4)
            lm (mod (dec md) 4)
            eg (map (fn [m s] [(cons m (map + ij (movedir m))) (+ s minv)])
                    [md rm lm] [1 1001 1001])
            eg (remove #(or (found (first %)) (= \# (get-in grid (nfirst %)))) eg)]
        (recur grid (assoc found mink minv)
               (merge-with min (apply hash-map (apply concat eg)) (dissoc news mink))))))

  (defn solve [txt]
    (let [grid (prepare txt)
          ij [(- (count grid) 2) 1]
          ee [1 (- (count (grid 0)) 2)]
          dijk (dijkstra grid {} {(cons 0 ij) 0})
          part1 (apply min (map #(dijk (cons % ee) $inf) [0 3]))] 
      part1))

  (solve sample))

(solve sample2)

(def input (slurp "16/input.txt"))
(solve input)
