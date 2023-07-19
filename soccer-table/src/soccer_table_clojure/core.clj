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
  (let [team (:team result) goals+ (:goals+ result) goals- (:goals- result)
        result (assoc result :diff (- goals+ goals-))]
    (cond (> goals+ goals-) (assoc result :won 1 :tied 0 :lost 0 :points 3)
          (< goals+ goals-) (assoc result :won 0 :tied 0 :lost 0 :points 0)
          :else (assoc result :won 0 :tied 1 :lost 0 :points 1))))

(defn -main
  [& args]
  (->>
   (clojure.string/split-lines (slurp (first args)))
   (map parse-result)
   (map convert-to-team-results)
   (flatten)
   (map convert-to-table-row)))
