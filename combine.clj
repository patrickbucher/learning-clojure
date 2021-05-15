(def names ["Alice" "Dilbert" "Wally" "Ashok" "Dogbert"])
(def adjectives ["great" "lazy" "nerdy" "evil"])
(def professions ["engineer" "manager" "consultant"])

(defn combine-employees [name adjective profession]
  (str name " the " adjective " " profession))

(def employees
  (map combine-employees
    (cycle names)
    (cycle adjectives)
    (cycle professions)))
