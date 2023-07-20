(defn fibs
  ([] (fibs 1 1))
  ([a b] (cons a (lazy-seq (fibs b (+ a b))))))
