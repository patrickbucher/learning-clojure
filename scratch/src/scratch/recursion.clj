(ns scratch.recursion)

(defn fact [n]
  {:pre [(>= n 0)]}
  (if (= n 0)
    1
    (* n (fact (dec n)))))

(defn factiter [n]
  {:pre [(>= n 0)]}
  (loop [i n acc 1]
    (if (= i 0)
      acc
      (recur (dec i) (* i acc)))))

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
