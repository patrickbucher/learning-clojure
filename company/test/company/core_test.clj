(ns company.core-test
  (:require [clojure.test :refer :all])
  (:require [company.core :as cc])
  (:require [company.employees :as ce]))

(deftest test-finding-employee-by-name
  (testing "Finding employees"
    (is (not (nil? (cc/find-by-name ce/employees "Dilbert"))))
    (is (not (nil? (cc/find-by-name ce/employees "Catbert")))))
  (testing "Not finding employees"
    (is (nil? (cc/find-by-name ce/employees "Sharkbert")))
    (is (nil? (cc/find-by-name ce/employees "Competent Boss")))))
