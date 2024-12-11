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
(defn walk [grid ij t path top loops hh]
  (let [tt (rem (inc t) 4)
        nij (map + ij (turns t))
        npath (conj path [ij t])]
    (if (contains? path [ij t]) true
      (case (get-in grid ij)
        nil (if top [path loops] false)
        \#  (recur grid (map - ij (turns t)) tt path top loops)
        (recur grid nij t npath top
               (if (and top (get-in grid nij) (not= nij hh)
                        (walk (assoc-in grid nij \#) hh 0 #{} false 0 hh))
                 (conj loops nij)
                 loops))))))

(defn solve [txt]
  (let [grid (togrid txt)
        ij (findg grid)
        [path loops] (walk grid ij 0 #{} true #{} ij)
        upath (set (map first path))]
    (map count [upath loops])))

(solve sample)

(def input (slurp "06/input.txt"))
(solve input)
