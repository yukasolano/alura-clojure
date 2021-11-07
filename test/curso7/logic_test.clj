(ns curso7.logic-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [curso7.logic :refer :all]
            [schema.core :as s]
            [curso7.model :as h.model]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [schema-generators.generators :as g]))

(s/set-fn-validation! true)

(deftest cabe-na-fila?-test

  (testing "Que cabe numa fila vazia"
    (is (cabe-na-fila? {:espera []} :espera)))

  (testing "Que cabe pessoas em filas de tamanho até 4 inclusive"
    (doseq [fila (gen/sample (gen/vector gen/string-alphanumeric 0 4))]
      (is (cabe-na-fila? {:espera fila} :espera))))

  (testing "Que não cabe na fila quando a fila está cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "Que não cabe na fila quando tem mais do que uma fila cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]} :espera))))

  (testing "Que cabe na fila quando tem gente mas não está cheia"
    (is (cabe-na-fila? {:espera [1 2 3 4]} :espera))
    (is (cabe-na-fila? {:espera [1]} :espera)))

  (testing "Que não cabe na fila quando a fila não existe"
    (is (not (cabe-na-fila? {:espera [1 2 3 4]} :raio-x)))))


(defspec coloca-uma-pessoa-na-fila-menor-que-5 10
         (prop/for-all [fila (gen/vector gen/string-alphanumeric 4)
                        pessoa gen/string-alphanumeric]
                       (is (= {:espera (conj fila pessoa)}
                              (chega-em {:espera fila} :espera pessoa)))))
(defn transforma-vector-em-fila [vector]
  (reduce conj h.model/fila-vazia vector))

(def nome-aleatorio-gen
  (gen/fmap clojure.string/join (gen/vector gen/char-alpha 5 10)))

(def fila-nao-cheia-gen
  (gen/fmap transforma-vector-em-fila
            (gen/vector nome-aleatorio-gen 1 4)))
(defn total-de-pacientes
  [hospital]
  (->> hospital
       vals
       (map count)
       (reduce +)))

(defn transfere-ignorando-erro
  [hospital para]
  (try (transfere hospital :espera para)
       (catch IllegalStateException e
         hospital)))

;testa a propriedade do nosso codigo de manter o numero de pessoas no transfere
(defspec transfere-tem-que-manter-a-quantidade-de-pessoas 5
         (prop/for-all
          [espera fila-nao-cheia-gen
           raio-x fila-nao-cheia-gen
           ultrasom fila-nao-cheia-gen
           vai-para (gen/vector (gen/elements [:raio-x :ultrasom]) 10 50)]
          (let [hospital-inicial {:espera espera :raio-x raio-x :ultrasom ultrasom}
                hospital-final   (reduce transfere-ignorando-erro hospital-inicial vai-para)]
            (= (total-de-pacientes hospital-inicial)
               (total-de-pacientes hospital-final)))))

(defn adiciona-fila-de-espera [[hospital fila]]
  (assoc hospital :espera fila))

(def hospital-gen
  (gen/fmap
   adiciona-fila-de-espera
   (gen/tuple (gen/not-empty (g/generator h.model/Hospital))
              fila-nao-cheia-gen)))

(def chega-em-gen
  (gen/tuple (gen/return chega-em)
             (gen/return :espera)
             nome-aleatorio-gen
             (gen/return 1)))

(defn transfere-gen [hospital]
  (let [departamentos (keys hospital)]
    (gen/tuple (gen/return transfere)
               (gen/elements departamentos)
               (gen/elements departamentos)
               (gen/return 0))))

(defn acao-gen [hospital]
  (gen/one-of [chega-em-gen (transfere-gen hospital)]))

(defn acoes-gen [hospital]
  (gen/not-empty (gen/vector (acao-gen hospital) 1 100)))

(defn executa-uma-acao [{:keys [hospital diferenca] :as situacao} [funcao param1 param2 diferenca-se-sucesso]]
  (try
    (let [hopital-novo (funcao hospital param1 param2)]
      {:hospital hopital-novo :diferenca (+ diferenca diferenca-se-sucesso)})
    (catch IllegalStateException e
      situacao)))

(defspec simula-um-dia-do-hospital-nao-perde-pessoas 10
         (prop/for-all [hospital-inicial hospital-gen]
                       (let [acoes (gen/generate (acoes-gen hospital-inicial))
                             situacao-inicial {:hospital hospital-inicial :diferenca 0}
                             total-pacientes-inicial (total-de-pacientes hospital-inicial)
                             situacao-final (reduce executa-uma-acao situacao-inicial acoes)
                             total-pacientes-final (total-de-pacientes (:hospital situacao-final))]
                         (is (>= (- total-pacientes-final (:diferenca situacao-final))
                                 total-pacientes-inicial)))))
