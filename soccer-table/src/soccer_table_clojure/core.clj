(ns soccer-table-clojure.core
  (:gen-class))

(def pattern (re-pattern #"^(.+) (\d)+:(\d)+ (.+)$"))

(defn parse-result
  "Parses a line of the form 'Home Team 3:2 Away Team' to a hash map."
  [line]
  (->
   (->>
    line
    (re-matches pattern)
    (rest)
    (zipmap [:home-team :home-goals :away-goals :away-team]))
   (update :home-goals #(Integer/parseInt %))
   (update :away-goals #(Integer/parseInt %))))

(defn convert-to-team-results
  "Converts a single match result to two team result hash maps."
  [result]
  [{:team (:home-team result) :goals+ (:home-goals result) :goals- (:away-goals result)}
   {:team (:away-team result) :goals+ (:away-goals result) :goals- (:home-goals result)}])

(defn convert-to-table-row
  "Converts a team's result hash map to a table row representing a single result."
  [result]
  (let [team (:team result)
        goals+ (:goals+ result)
        goals- (:goals- result)
        result (assoc result :diff (- goals+ goals-))]
    (cond (> goals+ goals-) (assoc result :won 1 :tied 0 :lost 0 :points 3)
          (< goals+ goals-) (assoc result :won 0 :tied 0 :lost 1 :points 0)
          :else (assoc result :won 0 :tied 1 :lost 0 :points 1))))

(defn merge-table-rows
  [acc row]
  (let [team (:team row)
        acc (dissoc acc :team)
        row (dissoc row :team)]
    (assoc (merge-with + acc row) :team team)))

(defn accumulate-by-team
  [acc row]
  (let [team (:team row)]
    (if (contains? acc team)
      (update acc team #(merge-table-rows % row))
      (assoc acc team row))))

(defn rank
  [rows]
  (let [n (count rows)
        ranks (range 1 (inc n))]
    (map #(assoc (second %) :rank (first %)) (partition 2 (interleave ranks rows)))))

(defn -main
  [& args]
  (->>
   (clojure.string/split-lines (slurp (first args)))
   (map parse-result)
   (map convert-to-team-results)
   (flatten)
   (map convert-to-table-row)
   (reduce accumulate-by-team {})
   (vals)
   (sort-by :team)
   (sort-by :won >)
   (sort-by :goals+ >)
   (sort-by :points >)
   (rank)
   (map #(format "%2d %-30s %3d %2d %2d %2d %3d %3d %3d"
                 (:rank %) (:team %) (:points %)
                 (:won %) (:tied %) (:lost %)
                 (:goals+ %) (:goals- %) (:diff %)))
   (cons (format "%2s %-30s %3s %2s %2s %2s %3s %3s %3s"
                 "#" "Team" "P" "W" "T" "L" "+" "-" "="))
   (interpose "\n")
   (apply println)))
