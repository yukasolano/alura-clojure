(ns curso7.logic-test
  (:require [clojure.test :refer :all]
            [curso7.logic :refer :all]
            [schema.core :as s]
            [curso7.model :as h.model]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]))

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

;testa a propriedade do nosso codigo de manter o numero de pessoas no transfere
(defspec transfere-tem-que-manter-a-quantidade-de-pessoas 5
         (prop/for-all
          [espera fila-nao-cheia-gen
           raio-x fila-nao-cheia-gen
           ultrasom fila-nao-cheia-gen
           vai-para (gen/elements [:raio-x :ultrasom])
           ]
          (let [hospital-inicial {:espera espera :raio-x raio-x :ultrasom ultrasom}
                hospital-final (transfere hospital-inicial :espera vai-para)]
            (= (total-de-pacientes hospital-inicial)
               (total-de-pacientes hospital-final)))))
