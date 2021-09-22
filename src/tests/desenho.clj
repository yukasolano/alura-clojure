(ns tests.desenho
  (:use clojure.pprint))

(defprotocol DesenhoGeometrico
  (area [desenho])
  (perimetro [desenho]))

(defrecord Quadrado [lado])

(extend-type Quadrado
  DesenhoGeometrico
  (area [desenho] (* (:lado desenho) (.lado desenho)))
  (perimetro [desenho] (* 4 (get desenho :lado))))

(def quad (Quadrado. 5))
(pprint quad)
(println "Area" (area quad))
(println "Perimetro" (perimetro quad))

(defrecord Retangulo [lado-x lado-y]
  DesenhoGeometrico
  (area [desenho] (* lado-x lado-y))
  (perimetro [desenho] (+ (* lado-x 2) (* lado-y 2))))

(def ret (->Retangulo 5 2))
(pprint ret)
(println "Area" (area ret))
(println "Perimetro" (perimetro ret))


(println "Multimethod")
(defmulti area2 (fn [desenho] (:tipo desenho)))
(defmethod area2 :quadrado
  [desenho]
  (* (:lado desenho) (:lado desenho)))
(defmethod area2 :retangulo
  [desenho]
  (* (:lado-x desenho) (:lado-y desenho)))

(println "Area quadrado" (area2 {:tipo :quadrado :lado 5}))
(println "Area retangulo" (area2 {:tipo :retangulo :lado-x 5 :lado-y 2}))
