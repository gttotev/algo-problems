(def sample
  "190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20")

(defn prepare [txt]
  (->> txt
       clojure.string/split-lines
       (map #(map parse-long (clojure.string/split % #":? ")))))

(defn gencalc [[h & t]]
  (if (nil? t) [h]
    (let [rec (gencalc t)]
      (lazy-cat
        (map (partial + h) rec)
        (map (partial * h) rec)))))

(defn valid? [[sum & xs]]
  (some (partial = sum) (gencalc (reverse xs))))

(defn solve [txt]
  (->> txt
       prepare
       (filter valid?)
       (map first)
       (reduce +)))

(comment
  (filter valid?
       (prepare sample))
  (gencalc [2 3 2])

  ,)

(solve sample)

(def input (slurp "07/input.txt"))
(solve input)
