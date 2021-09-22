(ns curso3.aula6
  (:use [clojure pprint])
  (:require [curso3.model :as h.model]))

(defn cabe-na-fila? [departamento]
  (-> departamento
      count ,,,
      (< ,,, 5)))

(defn chega-em [fila pessoa]
  (if (cabe-na-fila? fila)
    (conj fila pessoa)
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn chega-em-com-ref-set! [hospital pessoa]
  "Troca de referencia via ref-set"
  (let [fila (get hospital :espera)]
    (ref-set fila (chega-em @fila pessoa))))

(defn chega-em! [hospital pessoa]
  "Troca de referencia via alter"
  (let [fila (get hospital :espera)]
    (alter fila chega-em pessoa)))

(defn simula-um-dia
  []
  (let [hospital {:espera (ref h.model/fila_vazia)
                  :laboratorio1 (ref h.model/fila_vazia)
                  :laboratorio2 (ref h.model/fila_vazia)
                  :laboratorio3 (ref h.model/fila_vazia)}]
    (dosync
      (chega-em! hospital "guilherme")
      (chega-em! hospital "maria")
      (chega-em! hospital "lucia")
      (chega-em! hospital "ana")
      (chega-em! hospital "paulo")
      ;(chega-em! hospital "joao")
      )
    (pprint hospital)))

;(simula-um-dia)


(defn async-chega-em! [hospital pessoa]
  (future
    (Thread/sleep 1000)
    (dosync
      (println "Tentando o codigo sincronizado" pessoa)
      (chega-em! hospital pessoa))))

(defn simula-um-dia-async
  []
  (let [hospital {:espera (ref h.model/fila_vazia)
                  :laboratorio1 (ref h.model/fila_vazia)
                  :laboratorio2 (ref h.model/fila_vazia)
                  :laboratorio3 (ref h.model/fila_vazia)}]
    ; simbolo global para você brincar
    (def futures (mapv #(async-chega-em! hospital %) (range 10)))
    (future
      (dotimes [n 8]
        (Thread/sleep 2000)
        (pprint hospital)
        (pprint futures)))
    ))

(simula-um-dia-async)