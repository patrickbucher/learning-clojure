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

# Arithmetic

- `mod`
    - `(mod 5 2)`: `1`

# Data Structures

## Construction

- `vector`
    - `(vector 1 2 3)`: `[1 2 3]`
- `list`
    - `(list 1 2 3)`: `(1 2 3)`
- `conj`
    - `(conj [0 1 2] 3)`: `[0 1 2 3]`
    - `(conj '(1 2 3) 0)`: `(0 1 2 3)`
- `cons`
    - `(cons 0 '(1 2 3))`: `(0 1 2 3)`

## Properties

- `count`
    - `(count [1 2 3])`: `3`

## Accessors

- `first`
    - `(first [1 2 3])`: `1`
- `rest`
    - `(rest [1 2 3])`: `(2 3)`
- `nth`
    - `(nth [1 2 3] 2)`: `3`

# Input/Output

- `println`
    - `(println "hello")`: `[outputs "hello"]`
