(ns curso2.aula3
  (:require [curso2.db :as c2.db]))

(println (c2.db/todos-os-pedidos))

;group-by

(println (group-by :usuario (c2.db/todos-os-pedidos)))

(defn total-do-item
  [[_ detalhes]]
  (* (get detalhes :quantidade 0) (get detalhes :preco-unitario 0)))

(defn total-do-pedido
  [pedido]
  (reduce + (map total-do-item pedido)))

(defn total-dos-pedidos
  [pedidos]
  (->> pedidos
       (map :itens)
       (map total-do-pedido)
       (reduce +)))


(defn conta-total-por-usuario
  [[usuario pedidos]]
  {:usuario usuario
   :total-de-pedidos (count pedidos)
   :preco-total (total-dos-pedidos pedidos)})

(->> (c2.db/todos-os-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     println)
