(ns curso1.aula3)



(println "Valor descontado")
(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))

(defn valor-descontado
  "Aplica desconto de 10%"
  [valor-bruto]
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto 0.10
          valor-bruto-descontado (* valor-bruto taxa-de-desconto)]
      (- valor-bruto valor-bruto-descontado))
    valor-bruto))

(println (valor-descontado 10))
(println (valor-descontado 1000))

(println "Valor descontado passando funcao como parametro")
(defn valor-descontado-passando-condicao
  "Aplica desconto de 10%"
  [aplica? valor-bruto]
  (if (aplica? valor-bruto)
    (let [taxa-de-desconto 0.10
          valor-bruto-descontado (* valor-bruto taxa-de-desconto)]
      (- valor-bruto valor-bruto-descontado))
    valor-bruto))

(defn maior-que-100? [valor] (> valor 100))
(println (valor-descontado-passando-condicao maior-que-100? 10))
(println (valor-descontado-passando-condicao maior-que-100? 1000))

(println "Valor descontado com funcao anonima")

(println (valor-descontado-passando-condicao (fn [v] (> v 100)) 10))
(println (valor-descontado-passando-condicao (fn [v] (> v 100)) 1000))
(println (valor-descontado-passando-condicao #(> % 100) 10))
(println (valor-descontado-passando-condicao #(> % 100) 10))