(ns curso1.aula6)


(def pedido {:mochila {:quantidade 2, :preco 50}
             :cadeira {:quantidade 3, :preco 20}})


(defn imprime
  [map-entry]
  (println "map-entry" (class map-entry) map-entry))

(defn imprime-desestruturado
  [[chave valor]]
  (println "chave" chave "e valor" valor))

(println (map imprime pedido))
(println (map imprime-desestruturado pedido))

(defn preco-dos-produtos [[chave valor]]                    ;pode substituir a chave que nao vai usar por _
  (* (:quantidade valor) (:preco valor)))

(println "preco dos produtos" (map preco-dos-produtos pedido))
(println "soma total" (reduce + (map preco-dos-produtos pedido)))

;THREAD LAST
(defn total-do-pedido
  [pedido]
  (->> pedido
       (map preco-dos-produtos)
       (reduce +)))

(println (total-do-pedido pedido))


(defn preco-do-produto [produto]
  (* (:quantidade produto) (:preco produto)))

(defn total-do-pedido-melhor
  [pedido]
  (->> pedido
       vals
       (map preco-do-produto)
       (reduce +)))

(println (total-do-pedido-melhor pedido))



;filter
(println "Filtrado gratuitos")
(def pedido-2 {:mochila {:quantidade 2, :preco 50}
               :cadeira {:quantidade 3, :preco 20}
               :chaveiro {:quantidade 1}})


(defn gratuito?
  [item]
  (<= (get item :preco 0) 0))

(println (filter gratuito? (vals pedido-2)))

;usando lambda para pegar o valor usa a funcao second
(println (filter #(gratuito? (second %)) pedido-2))


;inverte predicate
(defn pago?
  [item]
  (not (gratuito? item)))

(println (pago? {:preco 50}))
(println (pago? {:preco 0}))

;compoe funcoes
(println ((comp not gratuito?) {:preco 0}))

(def pago-2? (comp not gratuito?))
(println (pago-2? {:preco 50}))
(println (pago-2? {:preco 0}))
