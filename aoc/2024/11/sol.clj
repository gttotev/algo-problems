(def sample "125 17")
(def input "2 54 992917 5270417 2514 28561 0 990")

(defn prepare [txt]
  (->> (clojure.string/split txt #" ")
       (map parse-long)
       frequencies))

(defn solve [times txt]
  (->>
    (range times)
    (reduce
      (fn [freq i]
        (reduce-kv
          (fn [new k v]
            (let [sk (str k) kl (count sk) kl2 (/ kl 2)
                  assoc+ (fn [m k] (assoc m k (+ (m k 0) v)))]
              (cond
                (zero? k)  (assoc+ new 1)
                (even? kl) (-> new
                               (assoc+ (parse-long (subs sk 0 kl2)))
                               (assoc+ (parse-long (subs sk kl2))))
                :else (assoc+ new (* k 2024)))))
          {} freq))
      (prepare txt))
    vals
    (reduce +)))

(solve 25 sample)
(solve 25 input)

(solve 75 input)
