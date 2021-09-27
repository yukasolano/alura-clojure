(ns curso7.aula1
  (:require [clojure.test.check.generators :as gen]))

(println (gen/sample gen/boolean 100))
(println (gen/sample gen/small-integer 100))
(println (gen/sample gen/string))
(println (gen/sample gen/string-alphanumeric 100))
(println (gen/sample (gen/vector gen/small-integer 15) 100))
(println (gen/sample (gen/vector gen/small-integer 1 5) 100))
(println (gen/sample (gen/vector gen/small-integer) 100))
