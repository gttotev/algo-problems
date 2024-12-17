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

(def movedir [[0 1] [1 0] [0 -1] [-1 0]])

(defn dijkstra [grid found news]
  (if (empty? news) found
    (let [mink (apply min-key (comp first news) (keys news))
          minv (news mink)
          [md & ij] mink
          rm (mod (inc md) 4)
          lm (mod (dec md) 4)
          eg (map (fn [m s]
                    (let [nij (map + ij (movedir m))]
                      [(cons m nij) [(+ s (first minv)) (conj (second minv) nij)]]))
                  [md rm lm] [1 1001 1001])
          eg (remove #(or (found (first %)) (= \# (get-in grid (nfirst %)))) eg)]
      (recur grid (assoc found mink minv)
             (reduce (fn [res [mij [s path]]]
                       (if-let [[ss spath] (res mij)]
                         (cond
                           (< s ss) (assoc res mij [s path])
                           (> s ss) res
                           :else (assoc res mij [s (clojure.set/union path spath)]))
                         (assoc res mij [s path])))
                     (dissoc news mink) eg)))))

(defn solve [txt]
  (let [grid (prepare txt)
        ij [(- (count grid) 2) 1]
        ee [1 (- (count (grid 0)) 2)]
        dijk (dijkstra grid {} {(cons 0 ij) [0 #{ij}]})
        endm (apply hash-map (mapcat #(dijk (cons % ee)) [0 3])) 
        part1 (apply min (keys endm))
        part2 (count (endm part1))]
    [part1 part2]))

(solve sample)
(solve sample2)

(def input (slurp "16/input.txt"))
(solve input)
