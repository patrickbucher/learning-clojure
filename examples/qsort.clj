(defn qsort [coll]
  (if (or (empty? coll) (= (count coll) 1))
    coll
    (let [x (first coll)
          xs (rest coll)
          smaller (filter #(<= % x) xs)
          bigger (filter #(> % x) xs)]
      (flatten [(qsort smaller) x (qsort bigger)]))))
