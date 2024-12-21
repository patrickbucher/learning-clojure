(ns company.core
  (:require [company.employees])
  (:require [clojure.spec.alpha :as s]))

(defn find-by-name
  "Search for an employee by name (unique result)"
  [employees by-name]
  {:pre [(s/valid? :company.employees/employees employees)
         (s/valid? :company.employees/name by-name)]}
  (first (filter #(= (:name %1) by-name) employees)))

(s/fdef find-by-name :args (s/cat :employees :company.employees/employees
                                  :by-name :company.employees/name))

(defn find-by-position
  "Search for an employee by job (multiple results)"
  [employees by-job]
  (filter #(= (:job %1) by-job) employees))

(defn sum-salaries
  "Sum up the salaries of all given employees"
  [employees]
  (reduce #(+ %1 %2) (map #(:salary %1) employees)))

(defn introduce [employee]
  (str "Hello, my name is "
       (:name employee)
       ", I'm "
       (:age employee)
       " years old."))

(defn employee-exists [{:keys [args ret]}]
  (let [employee (-> args :employee :name)]
    (not (neg? (.indexOf ret employee)))))

(s/fdef introduce
        :args (s/cat :employee :company.employees/employee)
        :ret (s/and string?
                    (partial re-find #"Hello, my name is ")
                    (partial re-find #"I'm ")
                    (partial re-find #" years old."))
        :fn employee-exists)
