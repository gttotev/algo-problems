(def sample
  "....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...")

(defn togrid [txt]
  (->> txt
       clojure.string/split-lines
       (map vec)
       vec))

(defn findg [grid]
  (reduce (fn [_ [i row]]
            (let [j (.indexOf row \^)]
              (if (>= j 0) (reduced [i j]) nil)))
          nil
          (map-indexed vector grid)))

(def turns [[-1 0] [0 1] [1 0] [0 -1]])
(defn gget [grid [i j]] (get (get grid i []) j nil))
(defn walk [grid ij t path segs loops]
  (let [tt (rem (inc t) 4)
        nij (map + ij (turns t))]
    (case (gget grid ij)
      nil [path loops]
      \#  (recur grid (map - ij (turns t)) tt
                 path (conj segs {:pos (map (comp #(if (zero? %) nil %) abs *) ij (turns tt)) :dir t}) loops)
      (recur grid nij t (conj path ij) segs
             (if (or (contains? segs {:pos [(first ij) nil]  :dir tt})
                     (contains? segs {:pos [nil (second ij)] :dir tt}))
               (conj loops nij)
               loops)))))

(defn solve [txt]
  (let [grid (togrid txt)
        ij (findg grid)
        res (walk grid ij 0 #{} #{} #{})]
    (map count res)))

(solve sample)
(solve (slurp "06/input.txt"))
