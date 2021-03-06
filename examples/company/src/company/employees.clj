(ns company.employees
  (:require [clojure.spec.alpha :as s]))

(s/def ::name string?)

(s/def ::age int?)

(s/def ::job string?)

(s/def ::salary int?)

(s/def ::employee
  (s/keys :req-un [::name
                   ::age
                   ::job
                   ::salary]))

(s/def ::employees (s/coll-of ::employee))
 
(def employees [{:name "Dilbert" :age 42 :job "Engineer" :salary 120000}
                {:name "Alice" :age 37 :job "Engineer" :salary 115000}
                {:name "Wally" :age 47 :job "Engineer" :salary 130000}
                {:name "Pointy Haired Boss" :age 57 :job "Manager" :salary 250000}
                {:name "Ashok" :age 22 :job "Intern" :salary 18000}
                {:name "Dogbert" :age 7 :job "Consultant" :salary 470000}
                {:name "Catbert" :age 9 :job "Head of HR" :salary 190000}])
