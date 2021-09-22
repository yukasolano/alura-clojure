(ns curso6.logic-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [curso6.logic :refer :all]
            [curso6.model :as h.model]
            [schema.core :as s]))

(s/set-fn-validation! true)

(deftest cabe-na-fila?-test

  (testing "Que cabe numa fila vazia"
    (is (cabe-na-fila? {:espera []} :espera)))

  (testing "Que não cabe na fila quando a fila está cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "Que não cabe na fila quando tem mais do que uma fila cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]} :espera))))

  (testing "Que cabe na fila quando tem gente mas não está cheia"
    (is (cabe-na-fila? {:espera [1 2 3 4]} :espera))
    (is (cabe-na-fila? {:espera [1]} :espera)))

  (testing "Que não cabe na fila quando a fila não existe"
    (is (not (cabe-na-fila? {:espera [1 2 3 4]} :raio-x)))))

(deftest chega-em-com-exception-test
  (testing "Aceita pessoas enquanto cabem pessoas na fila"
    (is (= {:espera [1 2 3 4 5]}
           (chega-em-com-exception {:espera [1 2 3 4]} :espera 5)))
    (is (= {:espera [1 2 5]}
           (chega-em-com-exception {:espera [1 2]} :espera 5))))

  (testing "Não aceita pessoas quando não cabe na fila"
    (is (thrown? IllegalStateException
                 (chega-em-com-exception {:espera [1 2 3 4 5]} :espera 6)))))

(deftest chega-em-test
  (testing "Aceita pessoas enquanto cabem pessoas na fila"
    (is (= {:hospital {:espera [1 2 3 4 5]} :resultado :sucesso}
           (chega-em {:espera [1 2 3 4]} :espera 5)))
    (is (= {:hospital {:espera [1 2 5]} :resultado :sucesso}
           (chega-em {:espera [1 2]} :espera 5))))

  (testing "Não aceita pessoas quando não cabe na fila"
    (is (= {:hospital {:espera [1 2 3 4 5]} :resultado :impossivel-colocar-pessoa-na-fila}
           (chega-em {:espera [1 2 3 4 5]} :espera 6)))))


(deftest transfere-test
  (testing "Aceita pessoas se cabe"
    (let [hospital-original {:espera (conj h.model/fila-vazia "5") :raio-x h.model/fila-vazia}]
      (is (= {:espera [] :raio-x ["5"]}
             (transfere hospital-original :espera :raio-x))))

    (let [hospital-original {:espera (conj h.model/fila-vazia "51" "5") :raio-x (conj h.model/fila-vazia "13")}]
      (is (= {:espera ["5"] :raio-x ["13" "51"]}
             (transfere hospital-original :espera :raio-x)))))

  (testing "Recuso pessoas se não cabe"
    (let [hospital-cheio {:espera (conj h.model/fila-vazia "5"), :raio-x (conj h.model/fila-vazia "1" "51" "5" "33" "86")}]
      (is (thrown? clojure.lang.ExceptionInfo
                   (transfere hospital-cheio :espera :raio-x)))))

  (testing "Não pode invocar transferência sem hospital"
    (is (thrown? clojure.lang.ExceptionInfo
                 (transfere nil :espera :raio-x))))

  (testing "Condições obrigatórias"
    (let [hospital {:espera (conj h.model/fila-vazia "5"), :raio-x (conj h.model/fila-vazia "1" "54" "43" "12")}]
      (is (thrown? AssertionError
                   (transfere hospital :nao-existe :raio-x)))
      (is (thrown? AssertionError
                   (transfere hospital :raio-x :nao-existe))))))