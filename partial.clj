(def dilbert {:name "Dilbert" :salary 120000 :job "Engineer"})

(defn give-raise [amount employee]
  (assoc employee :salary (+ amount (:salary employee))))

(def small-raise (partial give-raise 1000))

(def is-expensive? (complement is-cheap?))

(defn cheap? [employee]
  (<= (:salary employee) 100000))

(defn engineer? [employee]
  (= "Engineer" (:job employee)))

(defn smelly? [employee]
  (= "Dilbert" (:name employee)))

(def fire? (every-pred (complement cheap?) engineer? smelly?))

(defn promote [employee raise-func]
  (update employee :salary raise-func))

(promote {:name "Dilbert" :salary 120000 :job "Engineer"} #(+ % 1000))

(def dogbertix {:name "Dogbertix" :ceo {:name "Dogbert" :salary 250000}})

(defn give-bonus [company]
  (update-in company [:ceo :salary] #(* 2 %)))
