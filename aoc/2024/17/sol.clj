(ns aoc2024.day17
  (:require [clojure.string :as s]))

(def program-flat
  (->> (s/split "2,4,1,3,7,5,1,5,0,3,4,1,5,5,3,0" #",")
       (map parse-long)))
(def program
  (->> program-flat
       (partition 2)
       vec))

(defn run [program a0 b0 c0]
  (loop [ip 0 a a0 b b0 c c0 out []]
    (if-let [[op x] (get program ip)]
      (let [nip (inc ip)
            combo (case x 4 a 5 b 6 c x)
            dv (quot a (bit-shift-left 1 combo))]
        (case op
          0 (recur nip dv)
          1 (recur nip a (bit-xor b x))
          2 (recur nip a (mod combo 8))
          3 (recur (if (zero? a) nip x))
          4 (recur nip a (bit-xor b c))
          5 (recur nip a b c (conj out (mod combo 8)))
          6 (recur nip a dv)
          7 (recur nip a b dv)))
      out)))

(s/join "," (run program 21539243 0 0))  ; part 1

(defn guess [as o]
  (mapcat (fn [a] (filter #(= o (first (run program % 0 0)))
                          (map + (repeat (* a 8)) (range 8))))
          as))

(apply min (reduce guess [0] (reverse program-flat)))  ; part 2
