(ns curso3.aula3
  (:use [clojure pprint])
  (:require [curso3.model :as h.model]
            [curso3.logic :as h.logic]))

(defn testa-atomao []
  (let [hospital-yu (atom {:espera h.model/fila_vazia})]
    (println hospital-yu)
    (pprint hospital-yu)
    (pprint (deref hospital-yu))
    (pprint @hospital-yu)

    ;nao eh assim que altera conteudo dentro de um atomo
    (println "assoc")
    (pprint (assoc @hospital-yu :laboratorio1 h.model/fila_vazia))
    (pprint @hospital-yu)

    ;essa eh uma das formas de alterar conteudo dentro de um atomo
    (swap! hospital-yu assoc :laboratorio1 h.model/fila_vazia)
    (pprint @hospital-yu)

    (println "update")
    ;update tradicional sem efeito no atomo
    (pprint (update @hospital-yu :laboratorio1 conj "111"))

    ;indo para swap
    (swap! hospital-yu update :laboratorio1 conj "111")
    (pprint @hospital-yu)

    ))

(testa-atomao)


(defn chega-em-malvado [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado-logado :espera pessoa)
  (println "apos inserir" pessoa))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvado hospital "222"))))
    (.start (Thread. (fn [] (chega-em-malvado hospital "333"))))
    (.start (Thread. (fn [] (chega-em-malvado hospital "444"))))
    (.start (Thread. (fn [] (chega-em-malvado hospital "555"))))
    (.start (Thread. (fn [] (chega-em-malvado hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))


(simula-um-dia-em-paralelo)