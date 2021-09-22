(ns curso3.aula4
  (:use [clojure pprint])
  (:require [curso3.model :as h.model]
            [curso3.logic :as h.logic]))


(defn chega-em! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "apos inserir" pessoa))

;usando funcao com varias ARIDADES
(defn start-thread-chega-em-hospital
  ([hospital]
   (fn [pessoa] (start-thread-chega-em-hospital hospital pessoa)))
  ([hospital pessoa]
   (.start (Thread. (fn [] (chega-em! hospital pessoa))))))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]
        starta-thread (start-thread-chega-em-hospital hospital)]
    (mapv starta-thread pessoas)
    (.start (Thread. (fn [] (Thread/sleep 2000)
                       (pprint hospital))))))

;(simula-um-dia-em-paralelo)


;usando PARTIAL
(defn start-thread-chega-em-hospital2
  ([hospital pessoa]
   (.start (Thread. (fn [] (chega-em! hospital pessoa))))))

(defn simula-um-dia-em-paralelo-com-partial
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]
        starta-thread (partial start-thread-chega-em-hospital2 hospital)]
    (mapv starta-thread pessoas)
    (.start (Thread. (fn [] (Thread/sleep 2000)
                       (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-partial)


;usando DOSEQ
(defn simula-um-dia-em-paralelo-com-doseq
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (doseq [pessoa ["111" "222" "333" "444" "555" "666"]]
      (start-thread-chega-em-hospital2 hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 2000)
                       (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-doseq)

;usando DOTIMES
(defn simula-um-dia-em-paralelo-com-dotimes
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (dotimes [pessoa 6]
      (start-thread-chega-em-hospital2 hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 2000)
                       (pprint hospital))))))

(simula-um-dia-em-paralelo-com-dotimes)

