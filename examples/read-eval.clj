'(dilbert pointy-haired-boss [alice]
          (wally ratbert
                 (dogbert "some characters from dilbert...")))

'(defn say-hello [friendly]
   (if friendly
     (println "Hello, my dear!")))

(defn say-hello [friendly]
  (if friendly
    (println "Hello, my dear!")))

(defn my-repl []
  (loop []
    (println (eval (read)))
    (recur)))

(defn my-eval-symbol [expr]
  (.get (ns-resolve *ns* expr)))

(defn my-eval-vector [expr]
  (vec (map my-eval expr)))

(defn my-eval-list [expr]
  (let [evaled-items (map my-eval expr)
        f (first evaled-items)
        args (rest evaled-items)]
    (apply f args)))

(defn my-eval [expr]
  (cond
    (string? expr) expr
    (keyword? expr) expr
    (number? expr) expr
    (symbol? expr) (my-eval-symbol expr)
    (vector? expr) (my-eval-vector expr)
    (list? expr) (my-eval-list expr)
    :else :unknown-expression))
