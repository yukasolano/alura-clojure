(ns curso4.aula5
  (:use clojure.pprint))

;;usar multimethod sem record

(println "tipo-de-autorizador")
; pedido { :paciente paciente, :valor valor, :procedimento procedimento }

(defn tipo-de-autorizador [pedido]
  (let [paciente (:paciente pedido)
        situacao (:situacao paciente)]
    (cond (= :urgente situacao) :sempre-autorizado
          (contains? pedido :plano) :plano-de-saude
          :else :credito-minimo)))

(defmulti deve-assinar-autorizacao-do-pedido? tipo-de-autorizador)

(defmethod deve-assinar-autorizacao-do-pedido? :sempre-autorizado [pedido]
  false)

(defmethod deve-assinar-autorizacao-do-pedido? :credito-minimo [pedido]
  (>= (:valor pedido 0) 50))

(defmethod deve-assinar-autorizacao-do-pedido? :plano-de-saude [pedido]
  (not (some #(= % (:procedimento pedido)) (:plano (:paciente pedido)))))

(let [particular {:id 15, :nome "Guilherme", :nascimento "18/9/1981", :situacao :urgente}
      plano {:id 15, :nome "Guilherme", :nascimento "18/9/1981", :situacao :urgente, :plano [:raio-x, :ultrasom]}]
  (pprint (deve-assinar-autorizacao-do-pedido? {:paciente particular, :valor 1000, :procedimento :coleta-de-sangue}))
  (pprint (deve-assinar-autorizacao-do-pedido? {:paciente plano, :valor 1000, :procedimento :coleta-de-sangue})))


(let [particular {:id 15, :nome "Guilherme", :nascimento "18/9/1981", :situacao :normal}
      plano {:id 15, :nome "Guilherme", :nascimento "18/9/1981", :situacao :normal, :plano [:raio-x, :ultrasom]}]
  (pprint (deve-assinar-autorizacao-do-pedido? {:paciente particular, :valor 1000, :procedimento :coleta-de-sangue}))
  (pprint (deve-assinar-autorizacao-do-pedido? {:paciente plano, :valor 1000, :procedimento :coleta-de-sangue}))
  (pprint (deve-assinar-autorizacao-do-pedido? {:paciente particular, :valor 10, :procedimento :coleta-de-sangue}))
  (pprint (deve-assinar-autorizacao-do-pedido? {:paciente plano, :valor 10, :procedimento :raio-x})))