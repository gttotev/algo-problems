(def rules
  "47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13")

(def updates
  "75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47")

(def rules (slurp "05/rules.txt"))
(def updates (slurp "05/updates.txt"))

(->> rules
     clojure.string/split-lines
     (map #(vec (map parse-long (clojure.string/split % #"\|"))))
     (reduce (fn [res [x v]] (assoc res x (conj (res x #{}) v))) {})
     (def before))

(defn valid? [up]
  (reduce (fn [seen x]
            (if (empty? (clojure.set/intersection (before x) seen))
                (conj seen x) (reduced false)))
          #{} up))

(valid? [75 47 61 53 29])
(rulesort [75 47 61 53 29])
(valid? [61 13 29])
(rulesort [61 13 29])

(defn rulesort [up]
  (->> up
    (sort (fn [a b]
            (cond
              (contains? (before a) b) -1
              (contains? (before b) a) 1
              :else 0)))
    vec))

(->> updates
     clojure.string/split-lines
     (map #(vec (map parse-long (clojure.string/split % #","))))
     (map #(if (valid? %) (% (quot (count %) 2)) 0))
     (reduce +))

(->> updates
     clojure.string/split-lines
     (map #(vec (map parse-long (clojure.string/split % #","))))
     (map #(let [rs (rulesort %)] (if (= rs %) 0 (rs (quot (count rs) 2)))))
     (reduce +))
