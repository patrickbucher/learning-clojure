(ns scratch.core
  (:gen-class))

(defn fib [n]
  {:pre [(>= n 0)]}
  (cond
    (= n 0) 1
    (= n 1) 1
    :else (+ (fib (- n 1)) (fib (- n 2)))))
