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

## TODO

functions left out so far:

- str
- count
