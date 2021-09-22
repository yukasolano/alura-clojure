(ns curso3.logic)

(defn cabe-na-fila? [hospital departamento]
  (-> hospital
      (get ,,, departamento)
      count ,,,
      (< ,,, 5)))

(defn chega-em [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn chega-em-pausado
  [hospital departamento pessoa]
  (Thread/sleep (* (rand) 2000))
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn chega-em-pausado-logado
  [hospital departamento pessoa]
  (println "tentando entrar" pessoa)
  (Thread/sleep (* (rand) 2000))
  (if (cabe-na-fila? hospital departamento)
    (do
      (println "entrou" pessoa)
      (update hospital departamento conj pessoa))
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn atende
  [hospital departamento]
  (update hospital departamento pop))

(defn proxima
  [hospital departamento]
  (peek (get hospital departamento)))

(defn transfere
  [hospital de para]
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende ,,, de)
        (chega-em ,,, para pessoa))))