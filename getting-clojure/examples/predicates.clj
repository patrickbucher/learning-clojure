(def dilbert {:name "Dilbert" :job "Engineer" :salary 120000})
(def ashok {:name "Ashok" :job "Intern" :salary 45000})

(defn well-paid? [employee]
  (> (:salary employee) 100000))

(defn nerd? [employee]
  (= (:job employee) "Engineer"))

(defn both? [employee pf1 pf2]
  (and (pf1 employee)
       (pf2 employee)))

(defn cheaper-func [max-salary]
  (fn [employee]
    (< (:salary employee) max-salary)))

(def working-poor? (cheaper-func 50000))
(def cheap-hire? (cheaper-func 100000))
