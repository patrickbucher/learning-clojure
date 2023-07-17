(ns scratch.core
  (:gen-class))

(defn sieve
  "Sieve of Eratosthenes"
  [xs]
  (reduce
   (fn [acc x]
     (if (some zero? (map (fn [p] (mod x p)) acc))
       acc
       (conj acc x)))
   [] xs))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
