(ns curso1.aula4)



(def precos [30 700 1000])

; pega primeiro elemento do vector
(println "primeiro elemento:" (precos 0))
(println "primeiro elemento:" (get precos 0))

; pega elemento que nao existe
;(println "lança excecao" (precos 17))
(println "valor padrao nil:" (get precos 17))
(println "valor padrao 0:" (get precos 0))


; atualiza um elemento do vector
(println "incrementa 1 no primeiro elemento:" (update precos 0 inc))

;codigo da aula anterior
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


; aplicando map
(println "map:" (map valor-descontado precos))

; range e filter
(println "range:" (range 10))
(println "filter even:" (filter even? (range 10)))
(println "filter:" (filter aplica-desconto? precos))


;aplicar reduce
(println "soma: " (reduce + precos))
(println "soma com valor inicial: " (reduce + 10 precos))