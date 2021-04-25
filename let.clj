(defn compute-bonus [employee bonus-rate max-bonus]
  (let [bonus (* (:salary employee) bonus-rate)]
    (if (<= bonus max-bonus)
      bonus
      max-bonus)))

(let [a 1 b 2 c 3] (println (+ a b c)))
