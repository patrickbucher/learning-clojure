(ns matchmaking.core
  (:require [clojure.math :refer [pow]])
  (:require [clojure.set :refer [intersection difference union]]))

(defn bitcount
  "Counts the number of 1 bits in the given number."
  ([x]
   (bitcount x 0))
  ([x acc]
   (if (= x 0)
     acc
     (bitcount (bit-shift-right x 1)
               (if (= (bit-and x 1) 1)
                 (inc acc)
                 acc)))))

(defn select-by-bitmask
  "Selects the elements for which the selector bit mask is 1."
  ([coll bitmask]
   (select-by-bitmask (vec coll) bitmask #{}))
  ([v bitmask acc]
   (cond (= bitmask 0) acc
         (= (bit-and bitmask 1) 1) (select-by-bitmask (pop v)
                                                      (bit-shift-right bitmask 1)
                                                      (conj acc (peek v)))
         :else (select-by-bitmask (pop v)
                                  (bit-shift-right bitmask 1)
                                  acc))))

(defn n-choose-k
  "Selects all possible combinations of k elements from coll."
  [coll k]
  (let [n (count coll)
        max (pow 2 n)
        nums (range max)
        bitcounts (partition 2 (interleave nums (map bitcount nums)))
        bitcounts-k (filter (fn [[x b]] (= b k)) bitcounts)
        masks (sort > (map (fn [[x b]] x) bitcounts-k))
        pairs (set (map #(select-by-bitmask coll %) masks))]
    pairs))

(defn make-matchday
  "Creates a matchday from pairs, each featuring two elements of coll."
  ([coll pairs]
   (make-matchday coll pairs #{}))
  ([coll pairs acc]
   (if (empty? coll)
     {:pairs pairs :matches acc}
     (let [used-coll (set (flatten (map vec acc)))
           usable-pairs (filter #(empty? (intersection used-coll %)) pairs)]
       (if (empty? usable-pairs)
         {:pairs pairs :matches acc}
         (let [p (first usable-pairs)
               rest-pairs (difference pairs #{p})
               rest-coll (flatten (filter (complement used-coll) coll))
               acc (conj acc p)]
           (make-matchday rest-coll rest-pairs acc)))))))

(defn make-matchdays
  "Creates matchdays for coll (even number of elements)."
  ([coll]
   (let [pairs (n-choose-k coll 2)]
     (make-matchdays coll pairs [])))
  ([coll pairs acc]
   (if (empty? pairs)
     acc
     (let [{rest-pairs :pairs day-acc :matches} (make-matchday coll pairs)
           acc (conj acc day-acc)]
       (make-matchdays coll rest-pairs acc)))))
