(defproject soccer-table-clojure "0.1.0-SNAPSHOT"
  :description "compute soccer tables from match results"
  :url "https://github.com/patrickbucher/soccer-table-clojure"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/test.check "1.1.1"]]
  :main ^:skip-aot soccer-table-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
