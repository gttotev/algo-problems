(def simple "12345")
(def sample "2333133121414131402")
(def input (clojure.string/trim (slurp "09/input.txt")))
(count input)

(defn prepare [txt]
  (mapv #(Character/digit % 10) txt))

(defn asum [a n]
  (/ (* n (+ a a n -1)) 2))

(defn solve [txt]
  (let [disk (prepare txt)
        idisk (map-indexed vector disk)
        upacc (fn [acc m n edisk]
                (-> acc
                    (update :sum + (* m (asum (:ii acc) n)))
                    (update :ii + n)
                    (assoc :edisk edisk)))]
    (reduce
      (fn [acc [i len]]
        (if (even? i)
          (let [edisk (:edisk acc)
                h (first edisk)]
            (upacc acc (/ i 2) (if (= (first h) i) (second h) len) edisk))
          (loop [left len
                 res acc]
            (let [[[j avail] & t] (:edisk res)]
              (cond
                (< j i) (reduced res)
                (zero? left) res
                (>= left avail) (recur (- left avail) (upacc res (/ j 2) avail t))
                (<  left avail) (upacc res (/ j 2) left (cons [j (- avail left)] t)))))))
      {:edisk (reverse (take-nth 2 idisk)) :ii 0 :sum 0} 
      idisk)))

(solve sample)
(:sum (solve input))
