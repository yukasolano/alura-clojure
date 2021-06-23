(ns curso2.aula4
  (:require [curso2.db :as c2.db]
            [curso2.logic :as c2.logic]))

(println (c2.db/todos-os-pedidos))

(let [pedidos (c2.db/todos-os-pedidos)
      resumo (c2.logic/resumo-por-usuario pedidos)]
  (println "Resumo" resumo)
  (println "Ordenado" (sort-by :preco-total resumo))
  (println "Ordenando ao contrario" (reverse (sort-by :preco-total resumo)))
  (println (get-in pedidos [0 :itens :mochila :quantidade])))

(defn resumo-por-usario-ordenado
  [pedidos]
  (->> pedidos
       c2.logic/resumo-por-usuario
       (sort-by :preco-total)
       reverse))


(let [pedidos (c2.db/todos-os-pedidos)
      resumo (resumo-por-usario-ordenado pedidos)]
  (println "Resumo ordenado" resumo)
  (println "Primeiro" (first resumo))
  (println "Second" (second resumo))
  (println "Resto" (rest resumo))
  (println "Total" (count resumo))
  (println "Class" (class resumo))
  (println "nth 1" (nth resumo 1))
  (println "take" (take 2 resumo))
  (println "filter > 500" (filter #(> (:preco-total %) 500) resumo))
  (println "some > 500" (some #(> (:preco-total %) 500) resumo)))
