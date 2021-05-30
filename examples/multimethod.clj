;; relevant fields: name, position
(def alice {:name "Alice" :position "Engineer" })
(def wally {:name "Wally" :position "Engineer" })

;; relevant fields: first-name, job
(def catbert {:first-name "Catbert" :job "HR Manager" :department "HR"})
(def dilbert {:first-name "Dilbert" :job "Engineer" :department "IT"})

;; implicit fields: first name, job
(def dogbert ["Dogbert" "Head of Abuse"])
(def ashok ["Ashok" "Technical Intern"])

(defn dispatch-employee-format [employee]
  (cond
    (vector? employee) :vector-employee
    (and (contains? employee :name)
         (contains? employee :position)) :min-employee
    (and (contains? employee :first-name)
         (contains? employee :job)) :max-employee))

(defmulti normalize-employee dispatch-employee-format)

(defmethod normalize-employee :vector-employee [employee]
  {:first-name (nth employee 0) :role (nth employee 1)})

(defmethod normalize-employee :min-employee [employee]
  {:first-name (:name employee) :role (:position employee)})

(defmethod normalize-employee :max-employee [employee]
  {:first-name (:first-name employee) :role (:job employee)})
