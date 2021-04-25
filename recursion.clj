(def employees [{:name "Dilbert" :salary 120000}
                {:name "Wally" :salary 130000}
                {:name "Alice" :salary 110000}
                {:name "Boss" :salary 380000}
                {:name "Ashok" :salary 54000}])

(defn sum-payroll [employees]
  (loop [employees employees total 0]
    (if (empty? employees)
      total
      (recur
        (rest employees)
        (+ total (:salary (first employees)))))))
