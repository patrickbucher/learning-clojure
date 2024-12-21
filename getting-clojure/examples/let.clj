(defn compute-bonus [employee bonus-rate max-bonus]
  (let [bonus (* (:salary employee) bonus-rate)]
    (if (<= bonus max-bonus)
      bonus
      max-bonus)))

(let [a 1 b 2 c 3] (println (+ a b c)))

(def employee-bonus-rates
  {"Dilbert" 0.05 "Dogbert" 0.25 "Pointy-Haired Boss" 1.0})

(defn compute-bonus [employee-name bonus-rates min-bonus]
  (let [bonus-rate (bonus-rates employee-name)]
    (fn [salary]
      (let [bonus (* bonus-rate salary)]
        (if (< bonus min-bonus)
          min-bonus
          bonus)))))

(defn mk-compute-bonus-func [employee-name bonus-rates min-bonus]
  (let [bonus-rate (bonus-rates employee-name)]
    (fn [salary]
      (let [bonus (* bonus-rate salary)]
        (if (< bonus min-bonus)
          min-bonus
          bonus)))))

(def calc-dilbert-bonus
  (mk-compute-bonus-func "Dilbert" employee-bonus-rates 1000))

(def calc-dogbert-bonus
  (mk-compute-bonus-func "Dogbert" employee-bonus-rates 10000))

(def books [{:title "War and Peace" :author "Lev Tolstoy"}
            {:title "Beowulf"}
            {:title "The Name of the Rose" :author "Umberto Eco"}
            {:title "Till Eulenspiegel"}])

(defn output [book]
  (let [author (:author book)]
    (if author
      (str (:title book) " by " author)
      (:title book))))

(defn output [book]
  (if-let [author (:author book)]
    (str (:title book) " by " author)
    (:title book)))

(defn writtey-by [book]
  (when-let [author (:author book)]
    (str (:title book) " was written by " author)))
