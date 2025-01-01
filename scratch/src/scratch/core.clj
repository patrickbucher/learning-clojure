(ns scratch.core
  (:gen-class))

(defn fib [n]
  {:pre [(>= n 0)]}
  (cond
    (= n 0) 1
    (= n 1) 1
    :else (+ (fib (- n 1)) (fib (- n 2)))))

(defn fibiter [n]
  {:pre [(>= n 0)]}
  (loop [i 0 a 1 b 1]
    (if (= i n)
      a
      (recur (inc i) b (+ a b)))))

(defn round [value granularity]
  {:pre [(> granularity 0)]
   :post [(<= % (+ value (/ granularity 2)))
          (>= % (- value (/ granularity 2)))]}
  (let [factor (/ 1 granularity)]
    (/ (Math/round (* value factor)) factor)))
