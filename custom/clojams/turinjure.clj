; 4 4 2 _ system forever
(def RULES
  {
 ; state
   :a {
     ; read: print, move, new-state
       nil [4 :right :b]
       }
   :b {
       nil [4 :right :c]
       }
   :c {
       nil [2 :right :d]
       }
   :d {
       nil [nil :right :a]
       }
   }
  )

(def five-star-div (->> (repeat 13 "*****") (interpose \|) (apply str)))
(defn five-str [v]
  (->> v
       (map #(let [nstr (str %)
                   nlen (-> (count nstr) - (+ 5) (/ 2))]
               (str
                 (apply str (repeat nlen \space))
                 nstr
                 (apply str (repeat (+ nlen 1/2) \space)))
               ))
       (interpose \|)
       (apply str)))

(defn run-machine ([timeout rules strip state pos]
  (if (or (not (integer? pos)) (neg? pos) (>= pos (count strip))) strip
    (let [r (strip pos)
          [p move nstate] (get-in rules [state r])
          nstrip (assoc strip pos p)
          npos (case move :left (dec pos) :right (inc pos) move)
          [ppstart ppend] [(- pos 6) (+ pos 7)]
          ppover (- ppend (count strip))
          pstart (max 0 (if (pos? ppover) (- ppstart ppover) ppstart))
          pend (min (count strip) (if (neg? ppstart) (- ppend ppstart) ppend))]

      (print "\033[2J\033[0;0H") ; clear term, go to 0,0
      (println (five-str ["STATE" state "READ" r "print" p
                          "next" (case move :right ">>>" :left "<<<" move) "state" nstate]))
      (println five-star-div)
      (println (five-str (map #(if (= % pos) "VVV" %) (range pstart pend))))
      (apply println (repeat 13 "-----"))
      (println (five-str (subvec strip pstart pend)))
      (println five-star-div)
      (Thread/sleep timeout)

      (print "\033[5;0H") ; go to term line 5
      (println (five-str (subvec nstrip pstart pend)))
      (println five-star-div)
      (Thread/sleep timeout)

      (recur timeout rules nstrip nstate npos))
    ))
  ([] (run-machine 600 RULES (vec (repeat 23 nil)) :a 0))
  )

(case (count *command-line-args*)
  4 (apply run-machine (-> *command-line-args* first read-string) RULES
           (map (comp eval read-string) (rest *command-line-args*)))
  1 (run-machine)
  (println "Usage:  clj turinjure.clj <timeout-ms> <strip-vector> <initial-state> <initial-position>"
           "\n\tclj turninjure.clj demo"))
