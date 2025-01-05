(ns scratch.math)

(defn round [value granularity]
  {:pre [(> granularity 0)]
   :post [(<= % (+ value (/ granularity 2)))
          (>= % (- value (/ granularity 2)))]}
  (let [factor (/ 1 granularity)]
    (/ (Math/round (* value factor)) factor)))
