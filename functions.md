# Higher-Order Functions

- `filter`
    - `(filter (fn [x] (= (mod x 2) 0)) [1 2 3 4 5])`: `(2 4)`
- `map`
    - `(map (fn [x] (* x 2)) [1 2 3])`: `(2 4 6)`
- `reduce`
    - `(reduce + [1 2 3 4])`: `10`
    - `(reduce (fn [acc x] (+ acc x)) [1 2 3 4])`: `10`
    - `(reduce (fn [acc x] (+ acc x)) 0 [1 2 3 4])`: `10`

# Predicates

- `some`
    - `(some (fn [x] (> x 0)) [-2 -1 0 1 2])`: `true`
- `zero?`
    - `(zero? 0)`: `true`

TODO: `=`, `not=`, `>`, `<`, `number?`, `string?`, `keyword?`, `map?`, `vector?`

# Arithmetic

- `mod`
    - `(mod 5 2)`: `1`

# Data Structures

## Construction

- `vector`
    - `(vector 1 2 3)`: `[1 2 3]`
    - `[1 2 3]`: `[1 2 3]`
- `list`
    - `(list 1 2 3)`: `(1 2 3)`
    - `'(1 2 3)`: `(1 2 3)`
- `hash-map`
    - `(hash-map :name "Dilbert" :age 42)`: `{:age 42, :name "Dilbert"}`
    - `{:name "Dilbert" :age 42}`: `{:age 42, :name "Dilbert"}`
- `sorted-map`
    - `(sorted-map :b 1 :c 2 :a 0 :d 3)`: `{:a 0, :b 1, :c 2, :d 3}`
- `conj`
    - `(conj [0 1 2] 3)`: `[0 1 2 3]`
    - `(conj '(1 2 3) 0)`: `(0 1 2 3)`
    - `(conj #{1 2 3} 4)`: `#{1 4 3 2}`
- `cons`
    - `(cons 0 '(1 2 3))`: `(0 1 2 3)`

## Properties/Predicates

- `count`
    - `(count [1 2 3])`: `3`
    - `(count {:page 53, :direction "up", :speed 3.1, :ready true})`: `4`
- `empty?`
    - `(empty? [1 2 3])`: `false`
    - `(empty? [])`: `true`

## Accessors/Modifiers

- `first`
    - `(first [1 2 3])`: `1`
- `rest`
    - `(rest [1 2 3])`: `(2 3)`
- `nth`
    - `(nth [1 2 3] 2)`: `3`
- `assoc`
    - `(assoc {:page 53, :direction "up"} :speed 3.1 :ready true)`:
      `{:page 53, :direction "up", :speed 3.1, :ready true}`
- `dissoc`
    - `(dissoc {:page 53, :direction "up", :speed 3.1, :ready true} :direction :speed)`:
      `{:page 53, :ready true}`
- `get`
    - `(get {:page 53, :direction "up", :speed 3.1, :ready true} :page)`: `53`
- `keys`
    - `(keys {:page 53, :direction "up", :speed 3.1, :ready true})`:
      `(:page :direction :speed :ready)`
- `vals`
    - `(vals {:page 53, :direction "up", :speed 3.1, :ready true})`: `(53 "up" 3.1 true)`
- `contains?`
    - `(contains? #{"Alice" "Bob" "Charlene"} "Alice")`: `true`
- `disj`
    - `(disj #{"Alice" "Bob" "Charlene"} "Bob")`: `#{"Alice" "Charlene"}`

# Input/Output

- `println`
    - `(println "hello")`: `[outputs "hello"]`

# Conditionals

TODO: `if`, `do`, `when`. `cond`, `case`, `try`/`catch`, `throw`/`ex-info`,
`loop`/`recur`
