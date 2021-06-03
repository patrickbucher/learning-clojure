(ns company.core-property-test
  (:require [clojure.test :refer :all])
  (:require [company.core :as cc])
  (:require [clojure.test.check :as tc])
  (:require [clojure.test.check.clojure-test :as ctest])
  (:require [clojure.test.check.generators :as gen])
  (:require [clojure.test.check.properties :as prop]))

(def text-gen
  (gen/such-that not-empty gen/string-alphanumeric))

(def num-gen
  (gen/such-that (complement zero?) gen/pos-int))

(def employee-gen
  (gen/hash-map :name text-gen
                :age num-gen
                :job text-gen
                :salary num-gen))

(def payroll-gen
  (gen/not-empty (gen/vector employee-gen)))

(def payroll-and-employee-gen
  (gen/let [payroll payroll-gen
            employee (gen/elements payroll)]
    {:payroll payroll :employee employee}))

(ctest/defspec find-by-name-finds-employee 50
  (prop/for-all [p-and-e payroll-and-employee-gen]
    (= (cc/find-by-name (:payroll p-and-e) (-> p-and-e :employee :name))
       (:employee p-and-e))))
