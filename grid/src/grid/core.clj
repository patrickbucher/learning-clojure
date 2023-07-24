(ns grid.core)

(defn from-func
  "Creates a new grid with the given shape (rows*cols) and initializes
  all the fields with the return value of init."
  [rows cols init]
  (for [r (range rows)]
    (for [c (range cols)]
      (init))))

(defn n-rows
  "Returns the number of rows in grid."
  [grid]
  (count grid))

(defn n-cols
  "Returns the number of cols in grid."
  [grid]
  (count (first grid)))

(defn get-field
  "Returns the field at row/col of grid."
  [grid row col]
  (nth (nth grid row) col))

(defn get-row
  "Returns the row of grid."
  [grid row]
  (get grid row))

(defn get-col
  "Returns the column of grid."
  [grid col]
  (map #(get % col) grid))

(defn get-diag
  "Returns the diagonal through row/col in :up/:down direction."
  [grid row col dir]
  (def segfuncs
    {:up
     {:left [(fn [r n] (range (inc r) n))
             (fn [c _] (range (dec c) -1 -1))]
      :right [(fn [r _] (range (dec r) -1 -1))
              (fn [c n] (range (inc c) n))]}
     :down
     {:left [(fn [r _] (range (dec r) -1 -1))
             (fn [c _] (range (dec c) -1 -1))]
      :right [(fn [r n] (range (inc r) n))
              (fn [c n] (range (inc c) n))]}})
  (defn get-diag-idxs [row col segfuncs]
    (let [rows (n-rows grid)
          cols (n-cols grid)]
      (partition 2
                 (interleave
                  (apply (first segfuncs) [row rows])
                  (apply (second segfuncs) [col cols])))))
  (let [middle (get-field grid row col)
        left (get-diag-idxs row col (get-in segfuncs [dir :left]))
        right (get-diag-idxs row col (get-in segfuncs [dir :right]))]
    (flatten [(reverse (map (fn [[r c]] (get-field grid r c)) left))
              middle
              (map (fn [[r c]] (get-field grid r c)) right)])))

