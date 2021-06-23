(ns curso2.db)

(def pedido1 {:usuario 15
              :itens {:mochila { :id :mochila, :quantidade 4, :preco-unitario 80}
                       :camiseta { :id :camiseta, :quantidade 3, :preco-unitario 40}
                       :tenis    { :id :tenis, :quantidade 1}}})

(def pedido2 {:usuario 1
              :itens {:mochila { :id :mochila, :quantidade 2, :preco-unitario 80}
                      :camiseta { :id :camiseta, :quantidade 7, :preco-unitario 40}
                      :chaveiro    { :id :chaveiro, :quantidade 3}}})

(def pedido3 {:usuario 2
              :itens {:mochila { :id :mochila, :quantidade 2, :preco-unitario 80}
                      :camiseta { :id :camiseta, :quantidade 3, :preco-unitario 40}
                      :tenis    { :id :tenis, :quantidade 1}}})

(def pedido4 {:usuario 3
              :itens {:mochila { :id :mochila, :quantidade 10, :preco-unitario 80}
                      :camiseta { :id :camiseta, :quantidade 3, :preco-unitario 40}
                      :tenis    { :id :tenis, :quantidade 1}}})

(def pedido5 {:usuario 2
              :itens {:mochila { :id :mochila, :quantidade 2, :preco-unitario 80}
                      :camiseta { :id :camiseta, :quantidade 3, :preco-unitario 40}
                      :tenis    { :id :tenis, :quantidade 1}}})

(def pedido6 {:usuario 15
              :itens {:mochila { :id :mochila, :quantidade 1, :preco-unitario 80}
                      :camiseta { :id :camiseta, :quantidade 15, :preco-unitario 40}
                      :tenis    { :id :tenis, :quantidade 1}}})

(defn todos-os-pedidos []
  [pedido1, pedido2, pedido3, pedido4, pedido5, pedido6])