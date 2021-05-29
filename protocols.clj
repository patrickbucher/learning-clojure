(defrecord Employee [name age job salary])

(defrecord Poet [name century works]
  Person
  (describe [this]
    (str
      (:name this)
      ", the author of " (clojure.string/join ", " (:works this))
      " who lived in " (:century this)))
  (greet [this msg]
    (str
      msg " " (:name this)
      ", author of " (clojure.string/join ", " (:works this)))))

(defrecord FictionalCharacter [name show traits]
  Person
  (describe [this]
    (str
      (:name this)
      ", the " (clojure.string/join ", " (:traits this))
      " character from " (:show this)))
  (greet [this msg]
    (str
      msg " " (:name this)
      ", you " (clojure.string/join ", " (:traits this))
      " character from " (:show this))))

(def dilbert
  (->Employee "Dilbert" 42 "Engineer" 120000))

(def homer-1
  (->Poet "Homer" "8th/7th B.C." ["Iliad" "Odyssey"]))

(def homer-2
  (->FictionalCharacter "Homer" "The Simpsons" ["lazy" "stupid" "impulsive"]))

(defprotocol Person
  (describe [this])
  (greet [this msg]))

(defprotocol Greetable
  (say-hi [this]))

(extend-protocol Greetable
  Employee
  (say-hi [employee]
    (str "Hello, I'm " (:name employee) ". I work as a " (:job employee) "."))
  Poet
  (say-hi [poet]
    (str "Greetings, I'm " (:name poet) ", author of "
         (clojure.string/join ", " (:works poet)) "."))
  FictionalCharacter
  (say-hi [character]
    (str "Hi, I'm " (:name character) " from " (:show character) ".")))

(extend-protocol Greetable
  String
  (say-hi [string]
    (str "Hi, I'm the String '" string "'."))
  Boolean
  (say-hi [bool]
    (str "Hi, I'm the Boolean '" bool "'.")))

(def dirty-harry
  (reify Person
    (describe [_] "Lieutenant Harry Callahan, San Francisco Police Department")
    (greet [_ msg] (str msg ", punk. Feeling lucky today?"))))
