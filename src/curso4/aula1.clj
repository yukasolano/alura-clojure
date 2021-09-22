(ns curso4.aula1
  (:use clojure.pprint))

(defrecord Paciente [id, nome, nascimento])

(println (->Paciente 15 "Guilherme" "1/1/1990"))
(pprint (->Paciente 15 "Guilherme" "1/1/1990"))
(pprint (Paciente. 15 "Guilherme" "1/1/1990"))
(pprint (map->Paciente {:id 15, :nome "Guilherme", :nascimento "1/1/1990"})) ;pode colocar mais ou menos parametros qndo ccria como mapa

(let [guilherme (->Paciente 15 "Guilherme" "1/1/1990")]
  (println (:id guilherme))
  (println (vals guilherme))
  (println (class guilherme))
  (println (record? guilherme))
  (println (.nome guilherme)))
