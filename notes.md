# Clojure

Notes loosely based on [Getting Clojure](https://pragprog.com/titles/roclojure/getting-clojure/) by Russ Olsen.

## Hello, Clojure

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

## Vectors and Lists

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

## Maps, Keywords, and Sets

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

## More Capable Functions

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
