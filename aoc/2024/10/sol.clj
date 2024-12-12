(def simple
  "0123
1234
8765
9876")

(def sample
  "89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732")

(defn prepare [txt]
  (->> txt
       clojure.string/split-lines
       (mapv (partial mapv #(Character/digit % 10)))))

(def mapiv (partial map-indexed vector))
(defn find-trailheads [grid]
  (reduce
    (fn [res [i row]]
      (reduce
        (fn [res [j c]]
          (if (zero? c) (conj res [i j]) res))
        res (mapiv row)))
    [] (mapiv grid)))

(defn find9s [grid prev ij]
  (let [[i j] ij
        cur (get-in grid ij -1)]
    (cond
      (not= (inc prev) cur) #{}
      (= cur 9) #{ij}
      :else (reduce
              clojure.set/union
              (map
                #(find9s grid cur (map + ij %))
                [[0 1] [0 -1] [1 0] [-1 0]])))))

(defn solve [txt]
  (let [grid (prepare txt)]
    (->> grid
         find-trailheads
         (map (comp
                count
                (partial find9s grid -1)))
         (reduce +))))

(solve sample)

(def input (slurp "10/input.txt"))
(solve input)
