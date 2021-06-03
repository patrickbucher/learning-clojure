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

(deftest test-finding-employee-by-name-parametrized
  (testing "Finding employees by their name, checking their roles"
    (are [actual expected] (= (:job actual) expected)
         (cc/find-by-name ce/employees "Dilbert") "Engineer"
         (cc/find-by-name ce/employees "Ashok") "Intern"
         (cc/find-by-name ce/employees "Dogbert") "Consultant")))

(deftest test-sum-up-employee-salaries
  (is (= 1293000 (cc/sum-salaries ce/employees))))
