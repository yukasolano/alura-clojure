(ns curso3.aula5
  (:use [clojure pprint])
  (:require [curso3.model :as h.model]
            [curso3.logic :as h.logic]))


(defn chega-em! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa))

(defn transfere! [hospital de para]
  (swap! hospital h.logic/transfere de para))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (chega-em! hospital "joão")
    (chega-em! hospital "maria")
    (chega-em! hospital "patricia")
    (chega-em! hospital "jose")
    (pprint hospital)
    (transfere! hospital :espera :laboratorio1)
    (pprint hospital)
    (transfere! hospital :espera :laboratorio2)
    (pprint hospital)
    (transfere! hospital :espera :laboratorio2)
    (pprint hospital)
    (transfere! hospital :laboratorio2 :laboratorio3)
    (pprint hospital)))

(simula-um-dia-em-paralelo)