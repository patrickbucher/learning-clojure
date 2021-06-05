;; simple counter using an atom
(def counter (atom 0))

(defn increase-counter! [amount]
  (swap! counter + amount))

(defn get-count []
  (deref counter))

;; first version: atom
(def employees (atom []))

(defn hire! [employee]
  (swap! employees #(conj % employee)))

;; second version: ref
(def employees (ref []))

(def total-payroll (ref 0))

(def total-staff (ref 0))

(defn hire! [employee]
  (dosync
    (alter employees #(conj % employee))
    (alter total-payroll #(+ % (:salary employee)))
    (alter total-staff inc)))

;; third version: agent
(def employees (agent []))

(defn notify-new-hire [employee]
  (println "Watch out for" (:name employee)))

(defn hire! [employee]
  (send employees
        (fn [old-employees]
          (notify-new-hire employee)
          (conj old-employees employee))))

;; fourth version: super data structure wrapped in an atom
(def payroll (atom {:total-staff 0
                    :total-payroll 0
                    :employees []}))

(defn hire! [employee]
  (swap! payroll
         (fn [old]
           (assoc old
                  :total-staff (inc (:total-staff old))
                  :total-payroll (+ (:total-payroll old) (:salary employee))
                  :employees (conj (:employees old) employee)))))

;; memoize example
(defn fib [n]
  (println "Computing Fibonacci number for" n)
  (cond
    (= n 0) 1
    (= n 1) 1
    (> n 1) (+ (fib (- n 1)) (fib (- n 2)))
    :else (throw (ex-info "fib(n) only defined for n >= 0"))))

;; agent example causing errors
(def donations (agent 0))

(defn praise-donor [amount donor]
  (println "Praise" donor "for donating" amount "coins!"))

(defn donate! [amount donor]
  (send donations
        (fn [old-donations]
          (praise-donor amount donor)
          (+ old-donations amount))))
