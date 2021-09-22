(ns curso6.logic
  (:require [curso6.model :as h.model]
            [schema.core :as s]))

; funciona para o caso de não ter o departamento
;(defn cabe-na-fila?
;  [hospital departamento]
;  (when-let [fila (get hospital departamento)]
;    (-> fila
;        count
;        (< 5))))

(defn cabe-na-fila?
  [hospital departamento]
  (some-> hospital
          departamento
          count
          (< 5)))


(defn chega-em-com-exception
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (IllegalStateException. "Não cabe ninguém neste departamento"))))

(defn- tenta-colocar-na-fila
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)))

(defn chega-em
  [hospital departamento pessoa]
  (if-let [novo-hospital (tenta-colocar-na-fila hospital departamento pessoa)]
    {:hospital novo-hospital, :resultado :sucesso}
    {:hospital hospital, :resultado :impossivel-colocar-pessoa-na-fila}))


(defn chega-em-com-ex-info
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "Não cabe ninguém neste departamento" {:paciente pessoa}))))

(s/defn atende :- h.model/Hospital
  [hospital :- h.model/Hospital departamento :- s/Keyword]
  (update hospital departamento pop))

(s/defn proxima :- h.model/PacienteID
  "Retorna o próximo paciente da fila"
  [hospital :- h.model/Hospital departamento :- s/Keyword]
  (-> hospital
      departamento
      peek))

(defn mesmo-tamanho? [hospital outro-hospital de para]
  (= (+ (count (get outro-hospital de)) (count (get outro-hospital para)))
     (+ (count (get hospital de)) (count (get hospital para)))))


(s/defn transfere :- h.model/Hospital
  "Transfere o próximo paciente da fila de para a fila para"
  [hospital :- h.model/Hospital de :- s/Keyword para :- s/Keyword]
  {:pre [(contains? hospital de), (contains? hospital para)]
   :post [(mesmo-tamanho? hospital % de para)]}
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de)
        (chega-em-com-ex-info para pessoa))))
