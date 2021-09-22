(ns curso4.logic
  (:require [curso4.model :as h.model]))

(defn agora []
  (h.model/to-ms (java.util.Date.)))
