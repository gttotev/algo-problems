(ns aoc2024.day14)

(def sample
  "p=0,4 v=3,-3
  p=6,3 v=-1,-3
  p=10,3 v=-1,2
  p=2,0 v=2,-1
  p=0,0 v=1,3
  p=3,0 v=-2,-2
  p=7,6 v=-1,-3
  p=3,0 v=-1,-2
  p=9,3 v=2,3
  p=7,3 v=-1,2
  p=2,4 v=2,-3
  p=9,5 v=-3,-3")
(def input (slurp "14/input.txt"))

(defn walk [i v t w]
  (mod (+ i (* v t)) w))

(defn solve1 [w h txt]
  (->> txt
       (re-seq #"p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)")
       (map (comp (partial map parse-long) rest))
       (map (fn [[x y vx vy]] [(walk x vx 100 w) (walk y vy 100 h)]))
       (reduce (fn [quad [x y]]
                 (let [ww (quot w 2) hh (quot h 2)]
                   (update quad (cond (and (< x ww) (< y hh)) 1
                                      (and (> x ww) (< y hh)) 2
                                      (and (< x ww) (> y hh)) 3
                                      (and (> x ww) (> y hh)) 4
                                      :else 0)
                           inc)))
               [0 0 0 0 0]) 
       rest
       (reduce *)))

(solve1 11 7 sample)
(solve1 101 103 input)

(def w 101)
(def h 103)
(defn botsi [bots i]
  (map (fn [[x y vx vy]] [(walk x vx i w) (walk y vy i h)]) bots))

(defn botgrid [grid bots]
  (reduce (fn [grid xy] (assoc-in grid xy \#)) grid bots))

(defn chaos [grid bots n]
  (/ (->> bots
       (map (fn [ij]
              (/ (dec (count
                        (filter #(= % \#)
                          (for [x [0 1 -1] y [0 1 -1]] (get-in grid (map + ij [x y]))))))
                 8)))
       (reduce +))
     n))

(def join clojure.string/join)
(defn solve2 [txt]
 (let [botsv (->> txt
                 (re-seq #"p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)")
                 (map (comp (partial map parse-long) rest)))
       n (count botsv)
       grid (vec (repeat w (vec (repeat h \space))))]
   (reduce
     (fn [_ i]
       (let [bots (botsi botsv i)
             grid (botgrid grid bots)
             ch (chaos grid bots n)]
         (when (> ch 2/10) (println (join "\n" (map join grid)) i (float ch)) (reduced i))))
     (range))))

(solve2 input)
