(ns curso2.aula2)


;(meu-mapa println ["daniela" "ana" "joao" "fabio" "maria" "jose"])

; mesma funcao com diferente qtd de parametros

(defn minha-funcao
  ([parametro1] (println "p1" parametro1))
  ([parametro1 parametro2] (println "p2" parametro1 parametro2)))

(minha-funcao 1)
(minha-funcao 1 2)


; conta

(defn conta
  ([elementos] (conta 0 elementos))
  ([total-ate-agora elementos]
   (if (seq elementos)
     (recur (inc total-ate-agora) (next elementos))
     total-ate-agora)))

(println (conta ["daniela" "ana" "joao" "fabio" "maria" "jose"]))
(println (conta []))
(println (conta ["batata"]))

(defn conta-loop
  [elementos]
  (loop [total-ate-agora 0
         elementos-restantes elementos]
    (if (seq elementos-restantes)
      (recur (inc total-ate-agora) (next elementos-restantes))
      total-ate-agora)))

(println (conta-loop ["daniela" "ana" "joao" "fabio" "maria" "jose"]))
(println (conta-loop []))
(println (conta-loop ["batata"]))