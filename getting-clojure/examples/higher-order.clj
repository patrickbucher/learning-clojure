(def employees [{:name "Dilbert" :salary 120000}
                {:name "Wally" :salary 130000}
                {:name "Alice" :salary 11000}
                {:name "Dogbert" :salary 180000}
                {:name "Topper" :salary 150000}])

(defn top-earners [n employees]
  (apply
    str
    (interpose
      " >= "
      (map :name (take n (reverse (sort-by :salary employees)))))))

(defn top-earners [n employees]
  (->>
    employees
    (sort-by :salary)
    reverse
    (take n)
    (map :name)
    (interpose " >= ")
    (apply str)))
