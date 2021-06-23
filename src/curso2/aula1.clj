(ns curso2.aula1)


(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do
        (funcao primeiro)
        (recur funcao (rest sequencia))))))
;recur precisa a ultima coisa antes de retorna a funcao
; clojure transforma em um laço para evitar o stackoverflow
; TAIL RECURSION

(meu-mapa println ["daniela" "ana" "joao" "fabio" "maria" "jose"])
(meu-mapa println ["daniela" false "joao" "fabio" "maria" "jose"])
(meu-mapa println [])
(meu-mapa println nil)