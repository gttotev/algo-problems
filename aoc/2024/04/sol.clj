(def sample
  "MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX")

(def input (slurp "./04/input.txt"))
(->> input
     clojure.string/split-lines
     (map #(vec (str "..." %1 "...")))
     (def ss))

(def dotlines (repeat 3 (vec (apply str (repeat (count (first ss)) ".")))))
(def padded (apply conj (vec (apply conj ss dotlines)) 
                        dotlines))

(take-last 4 padded)
([1 2 3] 0)

(defn findxmas [m i j]
  (let [h (str ((m i) j) ((m i) (+ j 1)) ((m i) (+ j 2)) ((m i) (+ j 3)))
        v (str ((m i) j) ((m (+ i 1)) j) ((m (+ i 2)) j) ((m (+ i 3)) j))
        d (str ((m i) j) ((m (+ i 1)) (+ j 1)) ((m (+ i 2)) (+ j 2))
               ((m (+ i 3)) (+ j 3)))
        s (str ((m i) j) ((m (- i 1)) (+ j 1)) ((m (- i 2)) (+ j 2))
               ((m (- i 3)) (+ j 3)))
        xmas {"XMAS" 1, "SAMX" 1}]
    (+ (xmas h 0)
       (xmas v 0)
       (xmas d 0)
       (xmas s 0))))

(defn findcross [m i j]
  (let [one (str ((m (- i 1)) (- j 1)) ((m i) j) ((m (+ i 1)) (+ j 1)))
        two (str ((m (- i 1)) (+ j 1)) ((m i) j) ((m (+ i 1)) (- j 1)))
        cross {"SAM" 1, "MAS" 1}]
    (* (cross one 0)
       (cross two 0))))

(reduce (fn [osum [i row]]
          (reduce (fn [isum [j c]]
                    (if (= c \.) isum
                        (+ isum (findcross padded i j))))
                  osum (map-indexed vector row)))
        0 (map-indexed vector padded))
