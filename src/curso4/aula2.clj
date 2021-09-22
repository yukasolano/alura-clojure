(ns curso4.aula2
  (:use clojure.pprint))

(defrecord PacienteParticular [id nome nascimento])
(defrecord PacientePlanoDeSaude [id nome nascimento plano])

(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (>= valor 50)))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (not (some #(= % procedimento) plano)))))

; alternativa seria implementar diretamente

;(defrecord PacientePlanoDeSaude
;  [id, nome, nascimento, plano]
;  Cobravel
;  (deve-assinar-pre-autorizacao? [paciente, procedimento, valor]
;    (let [plano (:plano paciente)]
;      (not (some #(= % procedimento) plano)))))

(let [particular (->PacienteParticular 15, "Guilherme", "18/9/1981")
      plano (->PacientePlanoDeSaude 15, "Guilherme", "18/9/1981", [:raio-x, :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 500))
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 40))
  (pprint (deve-assinar-pre-autorizacao? plano, :raio-x, 499990))
  (pprint (deve-assinar-pre-autorizacao? plano, :coleta-de-sangue, 499990)))

; Uma variacao baseada na palestra a seguir, mas com extend-type e com gregoriancalendar
; From Sean Devlin's talk on protocols at Clojure Conj

(defprotocol Dateable
  (to-ms [this]))

(extend-type java.lang.Number
  Dateable
  (to-ms [this] this))

(pprint (to-ms 56))

(extend-type java.util.Date
  Dateable
  (to-ms [this] (.getTime this)))

(pprint (to-ms (java.util.Date.)))

(extend-type java.util.Calendar
  Dateable
  (to-ms [this] (to-ms (.getTime this))))

(pprint (to-ms (java.util.GregorianCalendar.)))

;(pprint (to-ms "guilherme"))
