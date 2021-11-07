(ns curso7.aula6
  (:use clojure.pprint)
  (:require [curso7.model :as model]
            [schema-generators.generators :as g]))

(println (g/sample 10  model/PacienteID))
(pprint (g/sample 10 model/Departamento))
(pprint (g/sample 10 model/Hospital))
(pprint (g/generate model/Hospital))

