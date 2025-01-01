# Notes on Clojure

The functions `first` and `rest` are Clojure's `car` and `cdr`:

```clojure
=> (first [1 2 3])
1
=> (rest [1 2 3])
(2 3)
```

Use `cons` and `conj` to add an item at the beginning or to the end of a vector,
respectively.

```clojure
=> (conj [1 2 3] 4)
[1 2 3 4]
=> (cons 0 [1 2 3])
(0 1 2 3)
```

For lists, prepending is much more efficient than appending, so both `cons`
_and_ `conj` add the new element to the front:

```clojure
=> (cons 0 '(1 2 3))
(0 1 2 3)
=> (conj '(1 2 3) 0)
(0 1 2 3)
```

The naming of the predicates `every?`, `some`, `some?`, and `any?` is a bit
confusing. Both `every?` and `some` expect a predicate function and a collection
of items on which the predicate function will be applied. The function `any?`
returns `true` for _any_ argument, the function `some?` returns `true` for any
argument other than `nil`.

