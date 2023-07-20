(defn sieve-primes
  "Filters the given numbers (>= 2); only returns those that are prime numbers."
  [numbers]
  (reverse
   (reduce
    (fn [acc i]
      (if (some #(= (mod i %) 0) acc)
        acc
        (cons i acc)))
    []
    numbers)))

(defn find-next-prime
  "Returns the next prime number >n, where primes-up-to-n contains the prime numbers <=n."
  [n primes-up-to-n]
  (defn is-prime? [i]
    (not (some #(= (mod i %) 0) primes-up-to-n)))
  (let [n+ (iterate inc (inc n))]
    (first (filter is-prime? n+))))

(defn find-primes
  "Returns a lazy sequence of prime numbers."
  ([] (find-primes 1 []))
  ([n acc]
   (let [next-prime (find-next-prime n acc)
         acc (cons next-prime acc)]
     (cons next-prime (lazy-seq (find-primes next-prime acc))))))
