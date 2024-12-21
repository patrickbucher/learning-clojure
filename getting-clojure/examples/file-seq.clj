(require '[clojure.java.io :as io])
(require '[clojure.string :as str])

(defn split-by [sep]
  (fn [line]
    (str/split line sep)))

(defn to-employee [values]
  (zipmap [:name :job :age :salary] values))

(defn read-employee-db [filename]
  (with-open [r (io/reader filename)]
    (map
      (comp to-employee (split-by #";"))
      (doall (line-seq r)))))
