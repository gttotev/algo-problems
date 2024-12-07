(def sample "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
(def sample2 "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

(defonce input (slurp "./03/input.txt"))

(->> input
  (re-seq #"mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)")
  (map #(case (first %1)
          "do()" :do
          "don't()" :dont
          (* (parse-long (second %1)) (parse-long (last %1)))))
  (reduce (fn [[m sum] x] (case x
                            :do [1 sum]
                            :dont [0 sum]
                            [m (+ sum (* m x))])) [1 0])
  second)
