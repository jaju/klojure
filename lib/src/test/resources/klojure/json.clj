(ns klojure.json
  (:require [cheshire.core :as j]))

(defn to-json [m]
        (j/generate-string m {:pretty true}))

(defn from-json [j]
  (j/parse-string j keyword))
