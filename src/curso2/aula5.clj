(ns curso2.aula5
  (:require [curso2.db :as c2.db]
            [curso2.logic :as c2.logic]))


(defn gastou-bastante?
  [info-do-usuario]
  (> (:preco-total info-do-usuario) 500))

(let [pedidos (c2.db/todos-os-pedidos)
      resumo (c2.logic/resumo-por-usuario pedidos)]
  (println "keep" (keep gastou-bastante? resumo))           ;mantem o q for <> de nil
  (println "filter" (filter gastou-bastante? resumo)))
