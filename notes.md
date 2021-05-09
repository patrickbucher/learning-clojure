---
title: Notes on Clojure
author: Patrick Bucher
---

Notes loosely based on [Getting Clojure](https://pragprog.com/titles/roclojure/getting-clojure/) by Russ Olsen.

# Hello, Clojure

Install Clojure and Leiningen on Arch Linux:

    # pacman -S clojure leiningen

Start a REPL:

    $ lein repl

Write a "Hello World" program on the REPL:

    > (println "Hello, World!")
    Hello, World!
    nil

Write the same program with comments to `hello.clj`:

    ;; Hello World program in Clojure.

    (println "Hello, World!") ; Say hi.

Comments start with a semicolon and end with the line. Comments that take up a
whole line start with two semicolons by convention.

Run the program:

    $ clojure hello.clj
    Hello, World!

Use basic arithmetic functions:

    > (+ 3 2)
    5
    > (- 100 3 7)
    90
    > (+ (* 3 6) (/ 12 4))
    21
    > (/ 8 3) ; produces a ratio
    8/3
    > (quot 8 3) ; truncates
    2

Bind a _symbol_ to a value:

    > (def result (* 13 12))
    > (println result)
    156

Concatenate multiple values as a string:

    > (str 1 "to" 2)
    "1to2"

Write and call the "Hello World" program as a function:

    > (defn hello-world [] (println "Hello, World!"))
    > (hello-world)
    Hello, World!

Use a single function parameter:

    (defn greet [whom]
      (println "Hello," whom))

    > (greet "John")
    Hello, John

Use multiple function parameters:

    (defn average [a b]
      (/ (+ a b) 2))

    > (average 10 4)
    7

Use multiple expressions in the function body:

    (defn average [a b]
      (def a-plus-b (+ a b))
      (def half-the-sum (/ a-plus-b 2))
      half-the-sum)

    > (average 24 6)
    15

The last expression of the function body (here: `half-the-sum`) is returned.

Create and run a proper application using Leiningen:

    $ lein new app hello-world
    $ cd hello-world
    $ lein run
    Hello, World!

Extend the example (`src/hello_world/core.clj`) as follows:

    (ns hello-world.core
      (:gen-class))

    (defn greet [app whom]
      (println app "greets" whom))

    (defn -main
      [& args]
      (greet "Hello World" "the user"))

And run it again:

    $ lein run
    Hello World greets the user

# Vectors and Lists

Create a vector of numbers:

    > [1 2 3 4]
    [1 2 3 4]

A vector can hold values of different types:

    > [1 "two" 3.0 "four"]
    [1 "two" 3.0 "four"]

Vectors can be nested:

    > [1 ["foo" "bar"] 2 ["qux" "baz"]]
    [1 ["foo" "bar"] 2 ["qux" "baz"]]

Vectors can also be created using the `vector` function:

    > (vector 1 2 3 4)
    [1 2 3 4]
    > (vector (vector "one" "two") "three" (vector "four" "five"))
    [["one" "two"] "three" ["four" "five"]]

`count` returns the number of elements in a vector:

    > (def numbers [1 3 9 27])
    > (count numbers)
    4

`first` returns the first element of a vector:

    > (first [1 2 3 4])
    1
    > (first [])
    nil

`rest` returns all the elements of a vector but the first as a _sequence_:

    > (rest [1 2 3 4])
    > (rest [1])
    ()
    > (rest [])
    ()

`nth` returns the element at position `n` (zero-based index):

    > (nth [1 2 3 4] 2)
    3

The nth element can also be accessed using the vector's name and an index:

    > (def numbers [1 2 3 4 5])
    > (numbers 3)
    4

`conj` adds an element _to the end_ of a vector:

    > (def cities ["London", "New York", "Berlin"])
    > (conj cities "Moscow")
    ["London" "New York" "Berlin" "Moscow"]

`cons` adds an element _to the front_ of a vector:

    > (def countries ["USA", "Germany", "Turkey"])
    > (cons "Russia" countries)
    ("Russia" "USA" "Germany" "Turkey")

Actually, a new vector or sequence, respectively, is created, holding the
additional element.

A list can be created as follows:

    > '("New York" "London" "Berlin")
    ("New York" "London" "Berlin")

Or using the `list` function:

    > (list "New York" "London" "Berlin")
    ("New York" "London" "Berlin")

The functions `count`, `first`, `rest`, and `nth` can be applied to lists, too:

    > (def countries '("USA" "Russia" "Germany" "France"))
    > (count countries)
    4
    > (first countries)
    "USA"
    > (rest countries)
    ("Russia" "Germany" "France")
    > (nth countries 3)
    "France"

Unlike vectors, a list can _not_ be used like a function:

    > (countries 3)
    Execution error (ClassCastException) at user/eval2092 (REPL:1).
    clojure.lang.PersistentList cannot be cast to clojure.lang.IFn

Vectors are implemented as arrays, lists are implemented as linked lists. This
has some implications:

- Appending to the front is fast for lists and slow for vectors.
- Appending to the end is fast for vectors and slow for lists.

The `conj` function therefore adds elements to the front of lists and to the end
of vectors:

    > (conj [1 2 3] "what")
    [1 2 3 "what"]
    > (conj '(1 2 3) "what")
    ("what" 1 2 3)

# Maps, Keywords, and Sets

Maps are created using pairs within curly braces:

    > {"title" "War and Peace" "author" "Lev Tolstoy" "year" 1869}
    {"title" "War and Peace", "author" "Lev Tolstoy", "year" 1869}

Commas between key-value pairs can be used for better readability, but are
optional:

    > {"title" "War and Peace", "author" "Lev Tolstoy", "year" 1869}
    {"title" "War and Peace", "author" "Lev Tolstoy", "year" 1869}

Maps can also be created using the `hash-map` function:

    > (hash-map "title" "War and Peace" "author" "Lev Tolstoy" "year" 1869)
    {"author" "Lev Tolstoy", "title" "War and Peace", "year" 1869}

The left part of the pair is the _key_, the right part the _value_ of the entry.

`get` looks up the value of an entry by its key:

    > (def book {"title" "War and Peace" "author" "Lev Tolstoy" "year" 1869})
    > (get book "author")
    "Lev Tolstoy"

Like vectors, elements can be accessed without an explicit function call:

    > (book "title")
    "War and Peace"
    > (book "year")
    1869
    > (book "publisher")
    nil

Idiomatically, _keywords_ starting with a colon are used as map keys:

    > (def book {:title "War and Peace" :author "Lev Tolstoy" :year 1869})
    > book
    {:title "War and Peace", :author "Lev Tolstoy", :year 1869}
    > (book :title)
    "War and Peace"

Keywords can also be used for map lookups:

    > (:title book)
    "War and Peace"

`assoc` returns a map with an element either overwritten or added:

    > (def book {:title "War and Peace" :author "Lev Tolstoy" :year 1869})
    {:title "War and Peace", :author "Lev Tolstoy", :year 1869}
    > (assoc book :pages 2000)
    {:title "War and Peace", :author "Lev Tolstoy", :year 1869, :pages 2000}
    > (assoc book :pages 1987)
    {:title "War and Peace", :author "Lev Tolstoy", :year 1869, :pages 1987}

Using `assoc`, it's possible to add/modify multiple key-value pairs at once:

    > (def employee {:name "Dilbert"})
    > (assoc employee :job "Engineer" :salary 120000)
    {:name "Dilbert", :job "Engineer", :salary 120000}

`dissoc` removes a map's entry by its key:

    > (def employee {:name "Dilbert" :note "smelly"})
    > (dissoc employee :note)
    {:name "Dilbert"}

Like `assoc`, multiple keys can be used at once with `dissoc`:

    > (def employee {:name "Dilbert" :note "smelly" :terminate "Jan 2023"})
    > (dissoc employee :note :terminate)
    {:name "Dilbert"}

Keys not found in the map will be ignored silently:

    > (dissoc employee :sex-appeal :girlfriend :hobbies)
    {:name "Dilbert", :note "smelly", :terminate "Jan 2023"}

`keys` returns the map's keys (in unspecified order:

    > (def book {:title "War and Peace" :author "Lev Tolstoy" :year 1869})
    > (keys book)
    (:title :author :year)

Use a `sorted-map` for specified key ordering:

    > (def book (sorted-map :title "War and Peace" :author "Lev Tolstoy" :year 1869))
    > (keys book)
    (:author :title :year)

`keys` returns the map's values in arbitrary, but matching the key's order:

    > (vals book)
    ("War and Peace" "Lev Tolstoy" 1869)
    > (keys book)
    (:title :author :year)

A _set_ can be created as follows (commas being optional):

    > #{"Dilbert", "Alice", "Wally"}
    #{"Alice" "Wally" "Dilbert"}

An element must not occur more than once:

    > #{"Dilbert", "Alice", "Wally", "Dilbert"}
    Syntax error reading source at (REPL:1:42).
    Duplicate key: Dilbert

`contains?` checks if an element is contained in a set:

    > (def employees #{"Dilbert", "Alice", "Wally", "Ashok"})
    > (contains? employees "Dilbert")
    true
    > (contains? employees "Pointy Haired Boss")
    false

This lookup can be done without calling a function, returning the element if it
is contained, or `nil` if the element is missing:

    > (employees "Dilbert")
    "Dilbert"
    > (employees "Ratbert")
    nil

When working with keywords, the order can be switched:

    > (def genres #{:scifi :action :drama :love})
    > (genres :scifi)
    :scifi
    > (:scifi genres)
    :scifi

Functions like `count`, `first`, and `rest` handle map entries as two-element vectors:

    > (def employee {:name "Dilbert" :age 42 :job "Engineer"})
    > (count employee)
    3
    > (first employee)
    [:name "Dilbert"]
    > (rest employee)
    ([:age 42] [:job "Engineer"])

A set can be extendes using `conj`:

    > (conj genres :western)
    #{:western :scifi :drama :action :love}

An element won't be added a second time _without_) error:

    > (conj genres :western)
    #{:western :scifi :drama :action :love}
    > (conj genres :western)
    #{:western :scifi :drama :action :love}

`disj` returns a set without the specified element:

    > (disj genres :western)
    #{:scifi :drama :action :love}

No error occurs if the element is missing:

    > (disj genres :comedy)
    #{:scifi :drama :action :love}

Be aware taht `nil` is a valid set entry and map key:

    > (contains? #{:foo :bar nil} nil)
    true

# Logic

Conditional code can be executed using `if`:

    (if (= guess secret-number)
      (println "You guessed the secret number.")
      (println "Sorry, wrong number guessed..."))

If the boolean expression (first argument) holds true, the second argument is
evaluated; otherwise, the (optional) third argument is evaluated.

Being an expression, `if` returns a value:

    (defn yield-rate [balance]
      (if (>= balance 0) 0.125 12.5))

    > (yield-rate 300)
    0.125
    > (yield-rate -150)
    12.5

`nil` will be returned if the condition evaluates to `false` and if there's no
`else` branch.

Comparison operators like `=`, `not=`, and `>=` are actually functions, which
can take two or more arguments:

    > (= 2 2 2 2 3)
    false
    > (= 2 2 2 2 2 2)
    true

    > (not= 1 1 1 1)
    false
    > (not= 1 1 2 1)
    true

    > (>= 9 6 4 4 1)
    true
    > (>= 9 6 4 5 1)
    false

Predicate functions return whether or not an expression is of some specific type:

    > (number? 1987)
    true
    > (string? "Dilbert")
    true
    > (keyword? :title)
    true
    > (map? {:born 1987})
    true
    > (vector? [1 2 3])
    true

Multiple conditions can be combined using `and`, `or`, and `not`:

    > (or (and (> 5 3) (< 1 6)) (not (= 3 1)))
    true

Both `or` and `and` are _short-circuit_ operations (nothing is printed here):

    > (or (= 1 1) (println "strange"))
    true
    > (and (not= 1 1) (println "strange"))
    false

Every value besides `false` and `nil` is treated as _truthy_ (i.e. will be
evaluated to `true`), even empty collections and the number 0:

    > (if [] (println "[] is truthy"))
    [] is truthy
    nil

    > (if 0 (println "0 is truthy"))
    0 is truthy
    nil

Multiple expressions can be grouped together using `do`:

    (if (= guess secret-number)
      (do
        (println "You guessed the secret number.")
        (println "A winner is you.")
        {:points 100})
      (do
        (println "You guessed the wrong number.")
        (println "Shame on you.")
        {:points 0}))

The `do` expression evaluates to its last argument.

If no `else` branch is required, `when` can be used instead of `if`, which
allows for multiple expressions without using `do`:

    (when (= guess secret-number)
      (println "You guessed the secret number.")
      (println "A winner is you.")
      {:points 100})

If the condition doesn't hold `true`, `nil` is returned (like `if`).

Instead of nesting multiple `if`s, `cond` can be used for handling multiple
conditions:

    (defn check [guess secret-number]
      (cond
        (= guess secret-number) (println "correct")
        (< guess secret-number) (println "too low")
        (> guess secret-number) (println "too high")))

    > (check 3 3)
    correct
    nil

    > (check 4 3)
    too high
    nil

    > (check 3 4)
    too low
    nil

Idiomatically, a catch-all `:else` clause is added to make sure that every
condition is handled:

    (defn check [guess secret-number]
      (cond
        (= guess secret-number) (println "correct")
        (< guess secret-number) (println "too low")
        (> guess secret-number) (println "too high")
        :else (println "You broke the universe")))

Since `:else` is truthy, its branch will be evaluated unless any other branch
was evaluated before. (Any truthy value could be used instead of `:else`.)

For multiple equality comparisons against _constants_, `case` can be used
instead of `cond`:

    (defn color-hex-code [color]
      (case color
        :red "#ff0000"
        :green "#00ff00"
        :blue "#0000ff"
        "unknown"))

    > (color-hex-code :red)
    "#ff0000"
    > (color-hex-code :black)
    "unknown"

Exceptions can be handled using `try`/`catch`:

    (defn safe-divide [dividend divisor]
      (try
        (/ dividend divisor)
        (catch ArithmeticException e
          (println "One does not simply divide by zero."))))

    > (safe-divide 9 3)
    3

    > (safe-divide 9 0)
    One does not simply divide by zero.
    nil.

Exceptions can be thrown using `throw` and `ex-info`:

    (defn publish [book]
      (when (< (:pages book) 50)
        (throw
          (ex-info "A book needs fifty pages or more!" book))))

    > (publish {:title "Hello" :pages 30})
    Execution error (ExceptionInfo) at user/publish (REPL:4).
    A book needs fifty pages or more!

`ex-info` expects a string message and a map argument, and can be caught as
`clojure.lang.ExceptionInfo`.

# More Capable Functions

_Multi-arity_ functions accept different sets of parameters:

    (defn greet
      ([to-whom] (println "Hello" to-whom))
      ([message to-whom] (println message to-whom)))

    > (greet "John")
    Hello John
    > (greet "Hi" "John")
    Hi John

In order to reduce the amount of duplicated code, it's common that lower arity
functions call the function with the highest arity by filling in the missing
parameters with default values:

    (defn greet
      ([to-whom] (greet "Hello" to-whom))
      ([message to-whom] (println message to-whom)))

_Variadic_ functions accept a variable number of arguments:

    (defn output-all [& args]
      (println "args" args))

    > (output-all "one")
    args (one)
    > (output-all "one" 2 "three" 4.0)
    args (one 2 three 4.0)

The arguments left of the ampersand are regular arguments:

    (defn output-all [x y & args]
      (println "x" x "y" y "args" args))

    > (output-all "one" 2 "three" 4.0)
    x one y 2 args (three 4.0)

Multi-arity and variadic functions are good at dealing with a _different number_
of arguments. _Multimethods_ are useful to deal with _different characteristics_
of arguments. They consist of:

1. A dispatch function (`defn`) that assigns a keyword to an argument.
2. A multimethod (`defmulti`) that groups the implementations and refers to the
   dispatch function.
3. Multiple methods (`defmethod`), of which each handles one type of argument.

Consider employees being stored in different formats:

    ;; implicit fields: first name, job
    (def dogbert ["Dogbert" "Head of Abuse"])
    (def ashok ["Ashok" "Technical Intern"])

    ;; relevant fields: name, position
    (def alice {:name "Alice" :position "Engineer" })
    (def wally {:name "Wally" :position "Engineer" })

    ;; relevant fields: first-name, job
    (def catbert {:first-name "Catbert" :job "HR Manager"})
    (def dilbert {:first-name "Dilbert" :job "Engineer" :department "IT"})

The dispatch function figures out which format such an entry has:

    (defn dispatch-employee-format [employee]
      (cond
        (vector? employee) :vector-employee
        (and (contains? employee :name)
             (contains? employee :position)) :min-employee
        (and (contains? employee :first-name)
             (contains? employee :job)) :max-employee))

The multimethod defines a method name and connects it to the dispatcher:

    (defmulti normalize-employee dispatch-employee-format)

The implementations all have the same name, but handle a different keyword, as
mapped by the dispatcher function:

    (defmethod normalize-employee :vector-employee [employee]
      {:first-name (nth employee 0) :role (nth employee 1)})

    (defmethod normalize-employee :min-employee [employee]
      {:first-name (:name employee) :role (:position employee)})

    (defmethod normalize-employee :max-employee [employee]
      {:first-name (:first-name employee) :role (:job employee)})

The different kind of employee data structures are converted to a common format:

    > (normalize-employee dogbert)
    {:first-name "Dogbert", :role "Head of Abuse"}
    > (normalize-employee alice)
    {:first-name "Alice", :role "Engineer"}
    > (normalize-employee dilbert)
    {:first-name "Dilbert", :role "Engineer"}

If the dispatch method cannot match the argument submitted, an exception will be
thrown:

    > (normalize-employee {:first-name "Topper" :position "Head of Annoyance"})
    Execution error (IllegalArgumentException) at user/eval2108 (REPL:1).
    No method in multimethod 'normalize-employee' for dispatch value: null

This condition can be handled properly by defining a `:default` branch in the
dispatcher function.

Implementations for multimethods can be defined in different files, which allows
for extensibility. This allows for polymorphism not just based on type, but also
based on values.

Some functions are best implemented recursively:

    (def employees [{:name "Dilbert" :salary 120000}
                    {:name "Wally" :salary 130000}
                    {:name "Alice" :salary 110000}
                    {:name "Boss" :salary 380000}
                    {:name "Ashok" :salary 54000}])

    (defn sum-payroll
      ([employees] (sum-payroll employees 0))
      ([employees total]
        (if (empty? employees)
          total
          (sum-payroll
            (rest employees)
            (+ total (:salary (first employees)))))))

    > (sum-payroll employees)
    794000

The `sum-payroll` function could run out of stack space if the `employee` vector
gets too big. Therefore, Clojure supports _tail call optimization_, by simply
replacing the function call with `recur`:

    (defn sum-payroll
      ([employees] (sum-payroll employees 0))
      ([employees total]
        (if (empty? employees)
          total
          (recur
            (rest employees)
            (+ total (:salary (first employees)))))))

The multi-arity function can be simplified to a single-arity function using a
`loop` expression:

    (defn sum-payroll [employees]
      (loop [employees employees total 0]
        (if (empty? employees)
          total
          (recur
            (rest employees)
            (+ total (:salary (first employees)))))))

This construct defines and invokes a pseudo-function, where the `employees`
parameter is initialized with the `employees` argument of the `sum-payroll`
function; and `total` is initialized to `0`. This values will be re-initialized
by `recur` (`employees` to `(rest employees)` and `total` to itself plus the
current employees salary).

In practice, higher-ordered functions such as `map` are preferred over
`loop`/`recur` constructs.

Since comments are dropped upon compilation, _docstrings_ provide a way of
documenting code that will be preserved. They are accessible via the `doc`
macro:

    (defn average
      "Computes the average of a and b."
      [a b]
      (/ (+ a b) 2.0))

    > (average 3 2)
    2.5

    > (doc average)
    -------------------------
    user/average
    ([a b])
      Computes the average of a and b.
    nil

Docstrings can also be used for other constructs than functions:

    > (def dilbert "The smelly IT guy..." {:name "Dilbert" :job "Engineer"})
    > (doc dilbert)
    -------------------------
    user/dilbert
      The smelly IT guy...
    nil

A map containing `:pre` and `:post` entries can be used to enforce pre- and
post-conditions:

    (defn give-raise [employee amount]
      {:pre  [(<= amount 10000) (not= (:name employee) "Ashok")]
       :post [(<= (:salary %) 180000)]}
      (assoc employee :salary (+ (:salary employee) amount)))

The `:pre` condition makes sure that a raise must not exceed 100000, and that
an employee named Ashok will never get a raise.

The `:post` condition makes sure that after a raise, no employee will have a
salary of more than 180000. The return value is referred by `%`.

    > (give-raise {:name "Dilbert" :salary 120000} 5000)
    {:name "Dilbert", :salary 125000}

    > (give-raise {:name "Wally" :salary 110000} 15000)
    Execution error (AssertionError) at user/give-raise (REPL:1).
    Assert failed: (<= amount 10000)

    > (give-raise {:name "Ashok" :salary 45000} 1000)
    Execution error (AssertionError) at user/give-raise (REPL:1).
    Assert failed: (not= (:name employee) "Ashok")

    > (give-raise {:name "Ted" :salary 175000} 8000)
    Execution error (AssertionError) at user/give-raise (REPL:1).
    Assert failed: (<= (:salary %) 180000)

# Functional Things

Functions are values, which can be passed to other functions:

    (def dilbert {:name "Dilbert" :job "Engineer" :salary 120000})
    (def ashok {:name "Ashok" :job "Intern" :salary 45000})

    (defn well-paid? [employee]
      (> (:salary employee) 100000))

    (defn nerd? [employee]
      (= (:job employee) "Engineer"))

    (defn both? [employee pf1 pf2]
      (and (pf1 employee)
           (pf2 employee)))

    > (both? dilbert well-paid? nerd?)
    true

    > (both? ashok well-paid? nerd?)
    false

Anonymous functions can be defined using `fn`:

    (both? dilbert
      (fn [e] (> (:salary e) 100000))
      (fn [e] (= (:name e) "Dilbert")))

This can be used to created parametrized functions using a _lexical closure_:

    (defn cheaper-func [max-salary]
      (fn [employee]
        (< (:salary employee) max-salary)))

    (def working-poor? (cheaper-func 50000))
    (def cheap-hire? (cheaper-func 100000))

    > (working-poor? ashok)
    true
    
    > (cheap-hire? dilbert)
    false

The `apply` function applies a function for each argument:

    > (apply + [1 2 3])
    6

`partial` creates a new function by _partially_ filling in the arguments for an
existing function. Here, the plus function is partially applied with a single
number:

    > (def increment (partial + 1))
    > (increment 1)
    2
    > (increment 10)
    11

And here, the `give-raise` function is partially applied to define the `amount`
parameter:

    (def dilbert {:name "Dilbert" :salary 120000 :job "Engineer"})

    (defn give-raise [amount employee]
      (assoc employee :salary (+ amount (:salary employee))))

    (def small-raise (partial give-raise 1000))

    > (small-raise dilbert)
    {:name "Dilbert", :salary 121000, :job "Engineer"}

`complement` produces a new function by wrapping a function with a `not` call:

    (defn is-cheap? [employee]
      (<= (:salary employee) 100000))

    (def is-expensive? (complement is-cheap?))

    > (is-cheap? dilbert)
    false
    > (is-expensive? dilbert)
    true

`every-pred` combines multiple predicate function with `and`:

    (defn cheap? [employee]
      (<= (:salary employee) 100000))

    (defn engineer? [employee]
      (= "Engineer" (:job employee)))

    (defn smelly? [employee]
      (= "Dilbert" (:name employee)))

    (def fire? (every-pred (complement cheap?) engineer? smelly?))

    > (fire? {:name "Dilbert" :salary 120000 :job "Engineer"})
    true

    > (fire? {:name "Ted" :salary 180000 :job "Marketing"})
    false

Function literals or _lambdas_ can be defined using `#`:

    > (apply #(+ %1 %2 %3) [1 2 3])
    6

Since there is no argument list, the arguments are referred to using `%1`, `%2`,
etc. The highest-numbered argument defines the number of arguments:

    > (apply #(+ %5 %6) [1 2 3 4 5 6])
    11

The arguments one to four (i.e. `[1 2 3 4]`) are ignored.

If only a single argument is needed, it can be referred by `%` instead of `%1`:

    > (apply #(* 2 %) [123])
    246

Use lambdas for very short and simple functions. Use `fn` if named arguments are
useful. Use `defn` for lengthy functions with a proper name.

`defn` can be defined in terms of `def` and `fn`:

    (defn hello [to-whom]
      (println "Hello" to-whom))

Has the same effect as:

    (def hello
      (fn [to-whom]
        (println "Hello" to-whom)))

`update` works on a map by applying a function to a map's entry:

    (def dilbert {:name "Dilbert" :salary 120000 :job "Engineer"})

    (defn promote [employee raise-func]
      (update employee :salary raise-func))

    > (promote dilbert #(+ % 1000))
    {:name "Dilbert", :salary 121000, :job "Engineer"}

`update-in` accepts an additional path to locate the field in a nested map to be
updated:

    (def dogbertix {:name "Dogbertix" :ceo {:name "Dogbert" :salary 250000}})

    (defn give-bonus [company]
      (update-in company [:ceo :salary] #(* 2 %)))

    > (give-bonus dogbertix)
    {:name "Dogbertix", :ceo {:name "Dogbert", :salary 500000}}

# Let

`compute-bonus` needs to calculate the same value twice; once for the `if`
condition, and once for the return value of the function:

    (defn compute-bonus [employee bonus-rate max-bonus]
      (if (<= (* (:salary employee) bonus-rate) max-bonus)
        (* (:salary employee) bonus-rate)
        max-bonus))

    (def dilbert {:name "Dilbert" :salary 120000})

    > (compute-bonus dilbert 0.1 5000)
    5000

    > (compute-bonus dilbert 0.1 25000)
    12000.0

`let` defines re-usable local bindings:

    (defn compute-bonus [employee bonus-rate max-bonus]
      (let [bonus (* (:salary employee) bonus-rate)]
        (if (<= bonus max-bonus)
          bonus
          max-bonus)))

The expression on the right-hand side is assigned to the symbol on the left-hand
side of the vector. Multiple local bindings can be created at once:

    > (let [a 1 b 2 c 3] (println (+ a b c)))
    6

Later bindings have access to earlier bindings to their left:

    > (let [a 1 b (* 2 a) c (* 2 b)] (println a b c))
    1 2 4

The function `compute-bonus` calculates the employee's bonus by looking up their
bonus rate in a map:

    (def employee-bonus-rates
      {"Dilbert" 0.05 "Dogbert" 0.25 "Pointy-Haired Boss" 1.0})

    (defn compute-bonus [salary employee-name bonus-rates min-bonus]
      (let [bonus-rate (bonus-rates employee-name)
            bonus (* salary bonus-rate)]
        (if (< bonus min-bonus)
          min-bonus
          bonus)))

    > (compute-bonus 120000 "Dilbert" employee-bonus-rates 1000)
    6000.0

    > (compute-bonus 200000 "Dogbert" employee-bonus-rates 10000)
    50000.0

The map of `employee-bonus-rates` has to be carried away wherever a bonus has to
be calculated. A better approach is to return individual functions by employee
that have their bonus rate parametrized:

    (defn mk-compute-bonus-func [employee-name bonus-rates min-bonus]
      (let [bonus-rate (bonus-rates employee-name)]
        (fn [salary]
          (let [bonus (* bonus-rate salary)]
            (if (< bonus min-bonus)
              min-bonus
              bonus)))))

    (def calc-dilbert-bonus
      (mk-compute-bonus-func "Dilbert" employee-bonus-rates 1000))

    (def calc-dogbert-bonus
      (mk-compute-bonus-func "Dogbert" employee-bonus-rates 10000))

    > (calc-dilbert-bonus 120000)
    6000.0

    > (calc-dogbert-bonus 200000)
    50000.0

`let` is used to bind local variables that are referred to by another function
(lexical closure).

The following function outputs book entries with their authors, if available:

    (def books [{:title "War and Peace" :author "Lev Tolstoy"}
                {:title "Beowulf"}
                {:title "The Name of the Rose" :author "Umberto Eco"}
                {:title "Till Eulenspiegel"}])

    (defn output [book]
      (let [author (:author book)]
        (if author
          (str (:title book) " by " author)
          (:title book))))

    > (map output books)
    ("War and Peace by Lev Tolstoy" "Beowulf" "The Name of the Rose by Umberto Eco" "Till Eulenspiegel")

`if` and `let` can be combined to `if-let`, making the function shorter:

    (defn output [book]
      (if-let [author (:author book)]
        (str (:title book) " by " author)
        (:title book)))

First, the binding with `let` is created; second, the bound value is evaluated
using `if` (yielding `true` for any truthy value).

`when-let` combines `when` with `let` in the same way:

    (defn writtey-by [book]
      (when-let [author (:author book)]
        (str (:title book) " was written by " author)))
    
    > (map writtey-by books)
    ("War and Peace was written by Lev Tolstoy" nil "The Name of the Rose was written by Umberto Eco" nil)

# Def, Symbols, and Vars

Like keywords, symbols are values. Whereas keywords evaluate to themselves,
symbols created with `def` are bound to other values. The symbol itself can be
accessed programmatically using the single quote:

    > (def first-name "Dilbert")
    > first-name
    "Dilbert"
    > 'first-name
    first-name
    > (str 'first-name)
    "first-name"
    > (= 'first-name 'last-name)
    false
    > (= 'first-name 'first-name)
    true

A symbol and a value are bound together using a _var_, which is accessible
through the pound character and the symbol:

    > (def first-name "Dilbert")
    #'user/first-name
    > (def the-name #'first-name)

Symbol and value then can be accessed as follows:

    > (.-sym the-name)
    first-name
    > (.get the-name)
    "Dilbert"

Vars are _mutable_, so that bindings can be re-defined during development, say,
in a REPL session.

_Dynamic bindigs_ can be changed using `binding` and are, by convention,
surrounded by asterisks (`*`) or "earmuffs":

    > (def ^:dynamic *debug-enabled* false)
    > *debug-enabled*
    false

    > (binding [*debug-enabled* true]
        (println *debug-enabled*))
    true

Vars are _not_ supposed to be used like variables in other programming
languages. Use `^:dynamic` vars and `binding` sparingly.

The REPL provides some dynamic vars `*[n]` where `[n]` denotes the n-last
result:

    > (+ 2 1)
    3
    > (+ 5 4)
    9
    (- *1 *2) ; 9 - 3
    6

Dynamic vars can be changed using the `set!` function:

    > (def employees ["Dilbert" "Wally" "Alice" "Ted" "Ashok"])
    > employees
    ["Dilbert" "Wally" "Alice" "Ted" "Ashok"]
    > (set! *print-length* 2)
    > employees
    ["Dilbert" "Wally" ...]

`*e` denotes the last exception thrown:

    > (/ 3 0)
    Execution error (ArithmeticException) at user/eval2038 (REPL:1).
    Divide by zero
    > *e
    #error {
     :cause "Divide by zero"
     ...

# Namespaces

Vars, which represent the binding between a symbol and a value, live in
_namespaces_. There is always one _current_ namespace, affected by calls of
`def`.

The REPL creates and uses a namespace called `user`:

    > (def employee "Dilbert")
    #'user/employee

The `employee` symbol is bound to the value `"Dilbert"` within the `user`
namespace.

A new namespace can be created and made the current namespace using `ns`:

    > (ns dilbertix)
    > (def employees [:dilbert :alice :wally])
    #'dilbertix/employees

Calling `ns` with an existing namespace makes that namespace the current,
without changing it:

    > (ns user)
    > (def today "Sunday")
    #'user/today

After switching back, the bindings are still available:

    > (ns dilbertix)
    > employees
    [:dilbert :alice :wally]

Symbols from other namespaces can be referred to using a _fully qualified
symbol_:

    > (ns user)
    > dilbertix/employees

Namespaces defined in other files need to be loaded before they can be used. The
function `clojure.data/diff` is unavailable by default:

    > (def engineers [:alice :dilbert :wally])
    > (def high-performers [:alice :dilbert :topper])
    > (clojure.data/diff engineers high-performers)
    Execution error (ClassNotFoundException) at java.net.URLClassLoader/findClass (URLClassLoader.java:382).

The `clojure.data` namespace can be made available using `require`:

    > (require 'clojure.data)
    > (clojure.data/diff engineers high-performers)
    [[nil nil :wally] [nil nil :topper] [:alice :dilbert]]

Given a new project skeleton:

    $ lein new app dilbertix

Containing the file `src/dilbertix/core.clj`:

    (ns dilbertix.core
      (:gen-class))

    (defn -main
      "I don't do a whole lot ... yet."
      [& args]
      (println "Hello, World!"))

The namespace `dilbertix.core` and the file's location `dilbertix/core.clj`
match together: A namespace `foo.bar` is to be found in a file `foo/bar.clj`.

Dashes need to be converted to underscores: The namespace `foo-bar.qux` is to be
found in the file `foo_bar/qux.clj`.

Thus, a new namespace `dilbertix.employees` is to be defined within the project
folder in the file `src/dilbertix/employees.clj`:

    (ns dilbertix.employees)

    (def job-satisfaction 0.0021)

    (def employees [:dilbert :alice :wally])

The namespace definition can be supplied with `:require` expressions:

    (ns dilbertix.core
      (:require dilbertix.employees)
      (:gen-class))

    (defn -main
      [& args]
      (println "Our Employees:" dilbertix.employees/employees))

Notice the difference between the stand-alone `require`:

    (require 'dilbertix.employees) ; symbol, quoted

And the expression within the `ns` definition:

    (:require dilbertix.employees) ; keyword, unquoted

Aliases can be defined to make imported names shorter:

    (require '[dilbertix.employees :as employees])

Or within the namespace definition:

    (ns dilbertix.core
        (:require [dilbertix.employees :as employees]))

Which allows for shorter references:

    (println employees/job-satisfaction)

Instead of:

    (println dilbertix.employees/job-satisfaction)

Aliases don't mask ordinary bindings, so an `employees` var would still be
visible.

Using `:refer`, vars from anothe rnamespace are pulled into the current
namespace:

    (require '[dilbertix.employees :refer [employees job-satisfaction])

Which would mask an existing `employees` binding in the current namespace.
Therefore, `:refer` should be used sparingly.

The current namespace is available through the symbol `*ns*`:

    > (println *ns*)
    #object[clojure.lang.Namespace 0x38158523 user]

Existing namespaces can be looked up by their name:

    > (find-ns 'user)
    #object[clojure.lang.Namespace 0x38158523 "user"]

Namespaces can be discovered:

    > (ns-map *ns*)
    {primitives-classnames #'clojure.core/primitives-classnames, +' #'clojure.core/+' ...
    ;; omitted

The namespace is part of the symbol and can be extracted using the `namespace`
function:

    > (def hello "world")
    > (namespace 'user/hello)
    "user"

The namespace `clojure.core` provides functions such as `println` or `first` and
is made ready automatically:

    (require '[clojure.core :refer :all])

There is _no_ hierarchy of namespaces. The dots in `clojure.core.data` are just
part of the name.

Using `require`, a namespace only gets loaded once, which is sensible for code
within files. In a REPL session, reloading modified code is common, and the
`:reload` keyword can be used:

    (require :reload '[dilbertix.employees :as employees])

Symbols no longer needed can be removed using `ns-unmap`:

    (ns-unmap 'dilbertix.employees)

`defonce` makes sure a symbol is only bound to a value _once_, even if the
definition is required using `:reload`:

    (defonce answer-to-everything (summarize-all-books))

If the symbol is supposed to be rebound nonetheless, it can be unmapped:

    (ns-unmap *ns* 'answer-to-everything)

# Sequences

The different collection types (maps, vectors, lists, sets) share a common
wrapper interface called a _sequence_. That's why the `count` function (and many
others) work on different kinds of collections:

    > count [1 2 3]) ; vector: number of items
    3
    > (count {:name "John" :age 29}) ; map: number of key-value pairs
    2

The `seq` function wraps any collection in a sequence:

    > (seq {:age 42 :name "Dilbert"})
    ([:age 42] [:name "Dilbert"]) ; sequence of key-value pairs

    > (seq ["Alice", "Dilbert", "Wally"])
    ("Alice" "Dilbert" "Wally") ; looks like a list, is a sequence

`seq` returns `nil` when invoked on an empty collection:

    > (seq '()) ; empty list
    nil
    > (seq [])  ; empty vector
    nil
    > (seq {})  ; empty map
    nil
    > (seq #{}) ; empty set
    nil

Like `rest`, `next` returns all but the first elements of a sequence:

    > (rest [1 2 3])
    (2 3)
    > (next [1 2 3])
    (2 3)

_Unlike_ `rest`, `next` returns `nil` the remainder is an empty sequence:

    > (rest [1])
    ()
    > (next [1])
    nil

Always rely on `rest` and `next` returning an empty collection or `nil`,
respectively; _never_ compare the result of `first` against `nil` for this
purpuse:

    ;; bad idea!
    (defn is-empty [collection]
      (= (first (seq collection)) nil))

    > (is-empty [1 2 3])   ; correct
    false
    > (is-empty [])        ; correct
    true
    > (is-empty [nil 1 2]) ; wrong!
    true

    ;; better approach
    (defn is-empty [collection]
        (= (next (seq collection)) nil))

    > (is-empty [1 2 3])   ; correct
    false
    > (is-empty [])        ; correct
    true
    > (is-empty [nil 1 2]) ; correct
    false

New elements can be added to the front of a sequence using `cons`:

    > (cons 0 (seq [1 2 3]))
    (0 1 2 3)
    > (cons [:job "Engineer"] (seq {:name "Dilbert" :age 42}))
    ([:job "Engineer"] [:name "Dilbert"] [:age 42])

`rest`, `next`, `cons` (but _not_ `conj`), `sort`, `reverse` all return
sequences.

A _seqable_ is something that `seq` can turn into a sequence.

`partition` chops a sequence (or _seqable_) into a sequence of smaller junks:

    > (partition 2 [1 2 3 4 5])
    ((1 2) (3 4))

`interleave` zips two sequences together:

    > (interleave [1 3 5 7 9] [2 4 6 8])
    (1 2 3 4 5 6 7 8)

`interpose` adds a separator value in between the elements:

    > (interpose "and" ["Dilbert" "Wally" "Alice"])
    ("Dilbert" "and" "Wally" "and" "Alice")
    > (interpose '+ [1 2 3])
    (1 + 2 + 3)

`filter` accepts a predicate function and a sequence, and returns a new sequence
holding the elements for which the predicate holds true:

    > (defn negative? [x] (< x 0))
    > (filter negative? [5 -5 10 -10])
    (-5 -10)

    (defn useful? [employee]
        (not= (:job employee) "Manager"))

    > (filter useful? [{:name "Pointy Haired Boss" :job "Manager"}
                       {:name "Dilbert" :job "Engineer"}])
    ({:name "Dilbert", :job "Engineer"})

Like `filter`, `some` applies a predicate to the elements of a sequence. Unlike
`filter`, it returns the first truthy value returned by the predicate:

    > (some neg? [1 2 3])
    nil
    > (some neg? [1 2 -3])
    true

    (defn useful-name [employee]
      (when
        (not= (:job employee) "Manager")
        (:name employee)))

    > (some useful-name [{:name "Pointy Haired Boss" :job "Manager"}
                         {:name "Dilbert" :job "Engineer"}])
    "Dilbert"

`map` transforms the elements of a sequence using a function:

    (defn raise-salary [employee]
      (assoc employee :salary (* 1.2 (:salary employee))))

    > (map raise-salary [{:name "Dilbert" :salary 120000}
                         {:name "Ashok" :salary 10000}])
    ({:name "Dilbert", :salary 144000.0} {:name "Ashok", :salary 12000.0})

    > (map :name [{:name "Dilbert" :salary 120000}
                  {:name "Ashok" :salary 10000}])
    ("Dilbert" "Ashok")

`comp` (for "compose") produces a function by applying the argument functions
from right to left (first, the salary is raised; second, the `:name` is
extracted):

    > (map (comp :name raise-salary)
        [{:name "Dilbert" :salary 120000}
         {:name "Ashok" :salary 10000}])
    (144000.0 12000.0)

`for` processes a sequence element by element:

    > (def employees [{:name "Dilbert" :salary 120000}
                      {:name "Ashok" :salary 10000}])
    > (for [e employees] (:name e))
    ("Dilbert" "Ashok")

`reduce` combines the elements of a sequence into a single value. It works with
a function that requires _two_ values: an accumulator and the current element:

    > (reduce (fn [acc x] (+ acc x)) [1 2 3 4])
    10
    > (reduce (fn [acc x] (* acc x)) [1 2 3 4])
    24

The starting value of the accumulator can be defined, too:

    > (reduce (fn [acc x] (+ acc x)) 100 [1 2 3 4])
    110

If the start value is left out, the first element will be used for it.

Since arithmetic operators are functions, this can be simplified:

    > (reduce + [1 2 3 4])
    10
    > (reduce * [1 2 3 4])
    24

Those higher-order functions can be used to compose elegant solutions:

    (def employees [{:name "Dilbert" :salary 120000}
                    {:name "Wally" :salary 130000}
                    {:name "Alice" :salary 11000}
                    {:name "Dogbert" :salary 180000}
                    {:name "Topper" :salary 150000}])

    (defn top-earners [n employees]
      (apply
        str
        (interpose
          " >= "
          (map :name (take n (reverse (sort-by :salary employees)))))))

    > (top-earners 3 employees)
    "Dogbert >= Topper >= Wally"

    > (top-earners 5 employees)
    "Dogbert >= Topper >= Wally >= Dilbert >= Alice"

    > (top-earners 1 employees)
    "Dogbert"

The definition of `top-earners` needs to be read from the inside out. The pointy
arrow function `->>` allows for an easier to read syntax without any runtime
performance overhead:

    (defn top-earners [n employees]
      (->>
        employees
        (sort-by :salary)
        reverse
        (take n)
        (map :name)
        (interpose " >= ")
        (apply str)))

The `->>` function uses the result of a function as the _last_ argument for the
next function all; `->` as the _first_ argument for the subsequent function
call.

Since Clojure provides so many ways of processing sequences, turning something
into a sequence can be a big step to solving that problem.

`line-seq` turns the lines of a text file into a (lazy) sequence. Given this CSV
employee data base (`employees.txt`):

    Pointy Haired Boss;Manager;58;250000
    Dilbert;Engineer;42;120000
    Alice;Engineer;39;110000
    Wally;Engineer;52;130000
    Dogbert;Consultant;7;390000
    Topper;Salesman;35;850000
    Ted;Project Manager;45;280000

`read-employee-db` turns it into a sequence of maps:

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

    > (read-employee-db "employees.txt")
    ((:name "Pointy Haired Boss" :job "Manager" :age "58" :salary "250000")
     (:name "Dilbert" :job "Engineer" :age "42" :salary "120000")
     (:name "Alice" :job "Engineer" :age "39" :salary "110000")
     (:name "Wally" :job "Engineer" :age "52" :salary "130000")
     (:name "Dogbert" :job "Consultant" :age "7" :salary "390000")
     (:name "Topper" :job "Salesman" :age "35" :salary "850000")
     (:name "Ted" :job "Project Manager" :age "45" :salary "280000"))

Which then can be processed using the `top-earners` function from before:

    > (top-earners 3 (read-employee-db "employees.txt"))
    "Topper >= Dogbert >= Ted"

Using regular expressions, strings can be turned into sequences (e.g. of words):

    > (re-seq #"\w+" "this is some sentence to be split") ; split by whitespace
    ("this" "is" "some" "sentence" "to" "be" "split")

Even though sequences are extremely useful, data structures like maps and
vectors loose _some_ of their power when wrapped as a sequence.
