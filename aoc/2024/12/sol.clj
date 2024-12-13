(ns aoc2024.day12)

(def sample1
  "AAAA
   BBCD
   BBCC
   EEEC")
(def sample2
  "OOOOO
   OXOXO
   OOOOO
   OXOXO
   OOOOO")
(def sample3
  "RRRRIICCFF
   RRRRIICCCF
   VVRRRCCFFF
   VVRCCCJFFF
   VVVVCJJCFE
   VVIVCCJJEE
   VVIIICJJEE
   MIIIIIJJEE
   MIIISIJEEE
   MMMISSJEEE")
 
(defn prepare [txt]
  (->> txt
       clojure.string/split-lines
       (mapv (comp vec clojure.string/trim))))

(defn get-tovisit [grid]
  (set (for [i (range (count grid)) j (range (count (grid 0)))] [i j])))

(comment
  (do
    (def grid (prepare sample3))
    (def tovisit (get-tovisit grid))
    (def igrid (to-igrid grid tovisit)))
  ,) 

(defn neighbors [ij]
  (map #(map + ij %) [[0 1] [0 -1] [1 0] [-1 0]]))
(defn visit [i c [igrid tovisit] ij]
  (if-not (and (tovisit ij) (= (get-in igrid ij) c)) [igrid tovisit]
    (reduce
      (partial visit i c)
      [(assoc-in igrid ij i) (disj tovisit ij)]
      (neighbors ij))))

(defn to-igrid [grid tovisit]
  (reduce
    (fn [[igrid tovisit] i]
      (if (empty? tovisit) (reduced igrid)
        (let [h (first tovisit)]
          (visit i (get-in igrid h) [igrid tovisit] h))))
    [grid tovisit] (range)))

(defn calc-area-perim [igrid]
  (reduce-kv
    (fn [regions i row]
      (reduce-kv
        (fn [regions j c]
          (assoc regions c
                 (map + (regions c [0 0])
                      [1 (count (remove #(= c %) (map #(get-in igrid %) (neighbors [i j]))))])))
        regions row))
    {} igrid))

(defn solve1 [txt]
  (let [grid (prepare txt)
        tovisit (get-tovisit grid)]
    (->> (to-igrid grid tovisit)
         calc-area-perim
         vals
         (map #(apply * %))
         (reduce +))))

(solve1 sample1)
(solve1 sample2)
(solve1 sample3)

(def input (slurp "12/input.txt"))
(solve1 input)
