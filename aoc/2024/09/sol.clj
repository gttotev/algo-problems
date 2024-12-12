(def simple "12345")
(def sample "2333133121414131402")
(def input (clojure.string/trim (slurp "09/input.txt")))
(count input)

(defn prepare [txt]
  (mapv #(Character/digit % 10) txt))

(defn asum [a n]
  (/ (* n (+ a a n -1)) 2))
(defn jsum [m j n]
  (* (/ m 2) (asum j n)))

(defn solve1 [txt]
  (let [disk (prepare txt)
        idisk (map-indexed vector disk)
        upacc (fn [acc m n edisk]
                (-> acc
                    (update :sum + (jsum m (:ii acc) n))
                    (update :ii + n)
                    (assoc :edisk edisk)))]
    (:sum
      (reduce
       (fn [acc [i len]]
         (if (even? i)
           (let [edisk (:edisk acc)
                 [j jlen] (first edisk)]
             (upacc acc i (if (= j i) jlen len) edisk))
           (loop [left len
                  res acc]
             (let [[[j avail] & t] (:edisk res)]
               (cond
                 (< j i) (reduced res)
                 (zero? left) res
                 (>= left avail) (recur (- left avail) (upacc res j avail t))
                 (<  left avail) (upacc res j left (cons [j (- avail left)] t)))))))
       {:edisk (reverse (take-nth 2 idisk)) :ii 0 :sum 0}
       idisk))))

(solve1 sample)
(solve1 input)

(defn solve2 [txt]
  (let [trips (second
                (reduce (fn [[sum res] [i x]]
                          [(+ sum x) (conj res [i sum x])])
                     [0 []] (map-indexed vector (prepare txt))))
        blanks (take-nth 2 (rest trips))
        files (reverse (take-nth 2 trips))]
    (second
      (reduce
        (fn [[blanks sum] [fi fj flen]]
          (if-let [[i [bi bj blen]]
                   (some (fn [ib]
                           (let [[bi bj blen] (second ib)]
                             (and (< bi fi) (>= blen flen) ib)))
                         (map-indexed vector blanks))]
            [(assoc blanks i [bi (+ bj flen) (- blen flen)]) (+ (jsum fi bj flen) sum)]
            [blanks (+ (jsum fi fj flen) sum)]))
        [(vec blanks) 0] files))))

(solve2 sample)
(solve2 input)
