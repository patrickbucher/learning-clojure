(defn print-rating [rating]
  (cond
    (pos? rating) (println "good")
    (zero? rating) (println "indifferent")
    :else (println "bad")))

(defn evaluate-rating [rating]
  (cond
    (pos? rating) :good
    (zero? rating) :indifferent
    :else :bad))

(defn arithmetic-if [n pos zero neg]
  (cond
    (pos? n) pos
    (zero? n) zero
    (neg? n) neg))

(defn evaluate-rating [rating]
  (arithmetic-if rating :good :indifferent :bad))

(defn print-rating [rating]
  (arithmetic-if rating
                 (println "good")
                 (println "indifferent")
                 (println "bad")))

(defn arithmetic-if [n pos-f zero-f neg-f]
  (cond
    (pos? n) (pos-f)
    (zero? n) (zero-f)
    (neg? n) (neg-f)))

(defn print-rating [rating]
  (arithmetic-if rating
                 #(println "good")
                 #(println "indifferent")
                 #(println "bad")))

(defn evaluate-rating [rating]
  (arithmetic-if rating
                 #(identity :good)
                 #(identity :indifferent)
                 #(identity :bad)))

(defn arithmetic-if-to-cond [n pos zero neg]
  (list 'cond (list 'pos? n) pos
        (list 'zero? n) zero
        :else neg))

(defmacro arithmetic-if [n pos zero neg]
  (list 'cond (list 'pos? n) pos
        (list 'zero? n) zero
        :else neg))

(defmacro arithmetic-if [n pos zero neg]
  `(cond
     (pos? ~n) ~pos
     (zero? ~n) ~zero
     :else ~neg))

(defn print-rating [rating]
  (arithmetic-if rating
                 (println "good")
                 (println "indifferent")
                 (println "bad")))

(defn evaluate-rating [rating]
  (arithmetic-if rating
                 :good
                 :indifferent
                 :bad))

(defmacro conjunction
  ([] true)
  ([x] x)
  ([x & next]
   `(let [current# ~x]
      (if current# (conjunction ~@next) current#))))

(defmacro my-defn [name args & body]
  `(def ~name (fn ~args ~body))) ; wrong

(defmacro my-defn [name args & body]
  `(def ~name (fn ~args ~@body))) ; right

(my-defn add-two-numbers [a b] (+ a b))

(defmacro two-step-process []
  (println "This code is run upon macro expansion.")
  `(fn [] (println "This code is run with the generated code.")))

