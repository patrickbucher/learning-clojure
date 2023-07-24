(ns grid.core)

(defn create
  "Creates a new grid with the given shape (rows*cols) and initializes
  all the fields with the return value of init. Both rows and cols are
  vectors."
  [rows cols init]
  (vec (for [r (range rows)]
         (vec (for [c (range cols)]
                (init))))))

(defn n-rows
  "Returns the number of rows in grid."
  [grid]
  (count grid))

(defn n-cols
  "Returns the number of cols in grid."
  [grid]
  (count (first grid)))

(defn get-row
  "Returns the row of grid."
  [grid row]
  (get grid row))

(defn get-col
  "Returns the column of grid."
  [grid col]
  (map #(get % col) grid))

(defn get-field
  "Returns the field at row/col of grid."
  [grid row col]
  (println (str row ":" col))
  (nth (nth grid row) col))

(defn get-diag-rising
  "Returns the rising diagonal through row/col."
  [grid row col]
  (let [rows (n-rows grid)
        cols (n-cols grid)
        start-field (get-field grid row col)
        bottom-left-indices (partition 2 (interleave (range (inc row) rows)
                                                     (range (dec col) -1 -1)))
        top-right-indices (partition 2 (interleave (range (dec row) -1 -1)
                                                   (range (inc col) cols)))]
    (flatten (conj (reverse (map #(get-field grid (first %) (second %)) bottom-left-indices))
                   start-field
                   (map #(get-field grid (first %) (second %)) top-right-indices)))))
