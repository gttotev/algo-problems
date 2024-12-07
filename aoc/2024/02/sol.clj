(def sample
 "7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9")

(def input (slurp "02/input.txt"))

(defn good [lvls]
  (let [mx (apply max lvls)
        mn (apply min lvls)]
    (and
      (<= mx 3)
      (>= mn -3)
      (> (* mx mn) 0))))

(defn allm [ls]
  (if (empty? ls) nil
      (cons (rest ls) (map #(cons (first ls) %1) (allm (rest ls))))))
(defn allml [ls]
  (cons ls (allm ls)))
(allml (list 1 2 3 4 5))

(->> input
  (clojure.string/split-lines)
  (map #(->> (map parse-long (clojure.string/split %1 #" "))
             (partition 2 1)
             (map (fn [x] (apply - x)))
             good))
  (filter boolean)
  count)

(->> input
  (clojure.string/split-lines)
  (map #(->> (map parse-long (clojure.string/split %1 #" "))
             allml
             (map (fn [x] (->> (partition 2 1 x)
                               (map (fn [x] (apply - x)))
                               good)))
             (some identity)))
  (keep identity)
  count)
