(ns scratch.core
  (:require [scratch.math :as math])
  (:require [scratch.recursion :as recursion])
  (:gen-class))

(defn -main [& args]
  (println "rounding 10/3:" (math/round (/ 10 3) 0.05))
  (println "40th fib num:" (recursion/fibiter 40)))
