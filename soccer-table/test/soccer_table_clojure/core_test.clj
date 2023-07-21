(ns soccer-table-clojure.core-test
  (:require [clojure.string :as str]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :as ctest]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [soccer-table-clojure.core :refer :all]))

(deftest test-parsing-results
  (is (= (parse-result "Atlantis 4:1 Wakanda")
         {:home-team "Atlantis"
          :away-team "Wakanda"
          :home-goals 4
          :away-goals 1})))

(deftest test-parsing-results-multiple
  (are [actual expected] (= actual expected)
    (parse-result "Foo 0:0 Bar")
    {:home-team "Foo" :away-team "Bar" :home-goals 0 :away-goals 0}
    (parse-result "FCL 3:2 FCB")
    {:home-team "FCL" :away-team "FCB" :home-goals 3 :away-goals 2}
    (parse-result "Schalke 04 1:4 1. FC Köln")
    {:home-team "Schalke 04" :away-team "1. FC Köln" :home-goals 1 :away-goals 4}))

(def team-name-gen (gen/such-that not-empty gen/string-alphanumeric))

(def result-gen
  (gen/let [home-team team-name-gen
            away-team team-name-gen
            home-goals gen/pos-int
            away-goals gen/pos-int]
    (format "%s %d:%d %s" home-team home-goals away-goals away-team)))

(ctest/defspec test-parsing-results-gen
 100
 (prop/for-all [result result-gen]
               (let [{:keys [home-team away-team home-goals away-goals]} (parse-result result)]
                 (and
                  (str/starts-with? result home-team)
                  (str/ends-with? result away-team)
                  (str/includes? result (format "%d:%d" home-goals away-goals))))))

(deftest test-converting-to-team-results
  (is (= (convert-to-team-results
          {:home-team "Atlantis"
           :away-team "Wakanda"
           :home-goals 4
           :away-goals 1})
         [{:team "Atlantis" :goals+ 4 :goals- 1}
          {:team "Wakanda" :goals+ 1 :goals- 4}])))

(deftest test-converting-to-table-row
  (testing "win"
    (is (= (convert-to-table-row {:team "Atlantis" :goals+ 4 :goals- 1})
           {:team "Atlantis" :won 1 :tied 0 :lost 0 :points 3 :goals+ 4 :goals- 1 :diff 3})))
  (testing "tie"
    (is (= (convert-to-table-row {:team "Atlantis" :goals+ 3 :goals- 3})
           {:team "Atlantis" :won 0 :tied 1 :lost 0 :points 1 :goals+ 3 :goals- 3 :diff 0})))
  (testing "defeat"
    (is (= (convert-to-table-row {:team "Wakanda" :goals+ 1 :goals- 4})
           {:team "Wakanda" :won 0 :tied 0 :lost 1 :points 0 :goals+ 1 :goals- 4 :diff -3}))))

(deftest test-merging-table-rows
  (is (= (merge-table-rows
          {:team "Atlantis" :won 1 :tied 0 :lost 0 :points 3 :goals+ 4 :goals- 1 :diff 3}
          {:team "Atlantis" :won 0 :tied 0 :lost 1 :points 0 :goals+ 2 :goals- 3 :diff -1})
         {:team "Atlantis" :won 1 :tied 0 :lost 1 :points 3 :goals+ 6 :goals- 4 :diff 2})))

(deftest test-accumulating-by-team
  (testing "initial"
    (is (= (accumulate-by-team
            {}
            {:team "Atlantis" :won 1 :tied 0 :lost 0 :points 3 :goals+ 4 :goals- 1 :diff 3})
           {"Atlantis"
            {:team "Atlantis" :won 1 :tied 0 :lost 0 :points 3 :goals+ 4 :goals- 1 :diff 3}})))
  (testing "addition"
    (is (= (get
            (accumulate-by-team
             {"Atlantis"
              {:team "Atlantis" :won 13 :tied 11 :lost 3 :points 50 :goals+ 72 :goals- 17 :diff 55}
              "Wakanda"
              {:team "Wakanda" :won 4 :tied 8 :lost 15 :points 20 :goals+ 25 :goals- 69 :diff -44}}
             {:team "Atlantis" :won 0 :tied 1 :lost 0 :points 1 :goals+ 2 :goals- 2 :diff 0})
            "Atlantis")
           {:team "Atlantis" :won 13 :tied 12 :lost 3 :points 51 :goals+ 74 :goals- 19 :diff 55}))))

(deftest test-ranking
  (let [ranked
        (rank [{:team "A" :won 13 :tied 11 :lost 3 :points 50 :goals+ 72 :goals- 17 :diff 55}
               {:team "W" :won 4 :tied 8 :lost 15 :points 20 :goals+ 25 :goals- 69 :diff -44}])]
    (is (and (= (:rank (nth ranked 0)) 1)
             (= (:rank (nth ranked 1)) 2)))))

