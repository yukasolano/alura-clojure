(ns curso5.aula2
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def Paciente
  "Schema de um paciente"
  {:id s/Num, :nome s/Str})

(pprint (s/explain Paciente))
(pprint (s/validate Paciente {:id 15, :nome "guilherme"}))

(s/defn novo-paciente :- Paciente
  [id :- s/Num, nome :- s/Str]
  {:id id, :nome nome})

(pprint (novo-paciente 15 "guilherme"))

(defn estritamente-positivo? [x]
  (> x 0))

(def EstritamentePositivo (s/pred estritamente-positivo? 'estritamente-positivo)) ;coloca a descricao, se nao nao da pra entender o erro

(pprint (s/validate EstritamentePositivo 15))
;(pprint (s/validate EstritamentePositivo 0))
;(pprint (s/validate EstritamentePositivo -15))

(def Paciente2
  "Schema de um paciente com constraint"
  {:id (s/constrained s/Int estritamente-positivo?), :nome s/Str})

(pprint (s/validate Paciente2 {:id 15 :nome "Guilherme"}))
;(pprint (s/validate Paciente2 {:id 0 :nome "Guilherme"}))
;(pprint (s/validate Paciente2 {:id -15 :nome "Guilherme"}))