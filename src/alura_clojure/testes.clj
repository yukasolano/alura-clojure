(ns alura-clojure.testes)

(let [result (future (println "Start future") (Thread/sleep 3000) (println "End future") (+ 1 1))]
  (println "Realized?" (realized? result))
  (println "The result is:" @result)
  (println "It will be at least 3 secons before it prints")
  (println "Realized?" (realized? result)))
