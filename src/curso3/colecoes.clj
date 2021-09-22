(ns curso3.colecoes
  (:import (clojure.lang PersistentQueue))
  (:use [clojure pprint]))

(defn testa-vetor []
  (let [espera [111 222]]
    (println "vetor")
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    (println (pop espera))))

(testa-vetor)

(defn testa-lista []
  (let [espera '(111 222)]
    (println "lista")
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    (println (pop espera))))

(testa-lista)

(defn testa-conjunto []
  (let [espera #{111 222}]
    (println "conjunto")
    (println espera)
    (println (conj espera 111))
    (println (conj espera 333))
    (println (conj espera 444))
    ;(println (pop espera))
    ))

(testa-conjunto)

(defn testa-fila []
  (let [espera (conj PersistentQueue/EMPTY "111" "222")]
    (println "fila")
    (println (seq espera))
    (pprint espera)
    (pprint (conj espera "333"))
    (pprint (pop espera))
    (pprint (peek espera))))

(testa-fila)
