(ns curso2.logic)

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

(defn resumo-por-usuario
  [pedidos]
  (->> pedidos
       (group-by :usuario)
       (map conta-total-por-usuario)))
