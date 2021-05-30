(ns company.core)

(defn find-by-name
  "Search for an employee by name (unique result)"
  [employees by-name]
  (first (filter #(= (:name %1) by-name) employees)))

(defn find-by-position
  "Search for an employee by job (multiple results)"
  [employees by-job]
  (filter #(= (:job %1) by-job) employees))

(defn sum-salaries
  "Sum up the salaries of all given employees"
  [employees]
  (reduce #(+ %1 %2) (map #(:salary %1) employees)))
