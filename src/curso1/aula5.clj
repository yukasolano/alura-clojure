(ns curso1.aula5)



(def estoque {:mochila 10 :camiseta 5})

(println estoque)
(println "Temos" (count estoque) "elementos")
(println "Chaves sã0:" (keys estoque))
(println "Valores sã0:" (vals estoque))
(println "Adiciona elemento no mapa:" (assoc estoque :cadeira 5))
(println (update estoque :mochila inc))
(println (dissoc estoque :camiseta))



;mapas aninhados
(def pedido {:mochila {:quantidade 2, :preco 30}
             :cadeira {:quantidade 3, :preco 20}})

(println pedido)
(println (pedido :mochila))
(println (get pedido :camiseta {}))                         ;com valor default
(println (:mochila pedido))                                 ;mais comum utilziar

(println (:quantidade (:mochila pedido)))
(println (update-in pedido [:mochila :quantidade] inc))

;THREADING
(println (-> pedido :mochila :quantidade))