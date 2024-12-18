(ns aoc2024.day18
  (:require [clojure.string :as s]))

(def dirs [[0 1] [0 -1] [1 0] [-1 0]])
(defn walk [dim blocked]
  (loop [seen (set (cons [0 0] blocked)) 
         q (conj clojure.lang.PersistentQueue/EMPTY [0 0 0])]
    (if-let [[d & ij] (peek q)]
      (let [nijs (->> dirs
                      (map #(map + ij %))
                      (filter (fn [[i j]] (and (<= 0 i dim) (<= 0 j dim))))
                      (remove seen))]
        (if (= ij [dim dim]) d
          (recur (into seen nijs) (into (pop q) (map #(cons (inc d) %) nijs))))))))

(defn prepare [txt]
  (->> txt s/split-lines (map #(map parse-long (s/split % #",")))))
(defn solve1 [txt dim fall]
  (walk dim (take fall (prepare txt)))) 

(def sample (slurp "18/sample.txt"))
(solve1 sample 6 12)

(def input (slurp "18/input.txt"))
(solve1 input 70 1024)

(defn solve2 [txt dim fall]
  (let [blocks (prepare txt)
        [open more] (split-at fall blocks)]
    (reduce (fn [bb b]
              (let [bb (conj bb b)] (if (walk dim bb) bb (reduced b))))
            open more)))

(solve2 sample 6 12)
(solve2 input 70 1024)
