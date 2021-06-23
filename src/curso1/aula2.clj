(ns curso1.aula2)

;definicao de simbolo
(def valor-mochila 15)
(println valor-mochila)

;definicao e manipulacao de vetor
(def estoques ["Mochila" "Caderno"])
(println (conj estoques "Lapis"))
(println (estoques 0))

;definicao de funcao
(defn valor-descontado
  "Aplica desconto de 10%"
  [valor-bruto]
  (if (> valor-bruto 100)
    (let [taxa-de-desconto 0.10
          valor-bruto-descontado (* valor-bruto taxa-de-desconto)]
      (- valor-bruto valor-bruto-descontado))
    valor-bruto))

(println (valor-descontado 10))
(println (valor-descontado 1000))