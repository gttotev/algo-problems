(require '[clojure.string :as str])

(def input (slurp "01/input.txt"))

input

(def two (->> input
             (str/split-lines) 
             (map #(str/split %1 #"   "))
             (reduce (fn [[left right] [l r]]
                       [(conj left (parse-long l))
                        (conj right (parse-long r))])
                     [[] []])))
             
(->> two
             (map sort)
             (apply mapv #(abs (- %1 %2)))
             (reduce +))

(def freq (frequencies (first two)))
(->> (second two)
     (map #(* %1 (get freq %1 0)))
     (reduce +))
