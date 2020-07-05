(defn rand-time [[st ed]] (->> (- ed st) rand (+ st)))
(defn make-before [plus-ms]
  (let [end (.plusNanos (java.time.LocalTime/now) (* plus-ms 1e6))]
    (fn -before
      ([] (-before 0))
      ([ms] (.isBefore (.plusNanos (java.time.LocalTime/now) (* ms 1e6)) end))
    )))

(def OPEN_TIME 10000)
(def CHAIR_COUNT 3)
(def WALKIN_TIMES [10 30])
(def CUT_TIME 20)

(defn haircut [cuts room timer cut-time]
  (if (timer cut-time)
    (do
      (swap! room dec)
      (Thread/sleep cut-time)
      (inc cuts))
    cuts)
  )

(defn barber-shop ([open-time chair-count walkin-times cut-time]
  (let [before (make-before open-time)
        waiting (atom 0)
        barber (agent 0)]

    (future
      (Thread/sleep (rand-time walkin-times))
      (while (before)
              (when (< @waiting chair-count)
                (swap! waiting inc)
                (send barber haircut waiting before cut-time))
              (Thread/sleep (rand-time walkin-times)))
      @barber)
    )
  )
  ([] (barber-shop OPEN_TIME CHAIR_COUNT WALKIN_TIMES CUT_TIME)))
