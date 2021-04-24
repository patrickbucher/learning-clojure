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
    > (first []_
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
