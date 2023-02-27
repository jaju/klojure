(ns klojure.json
  (:require [cheshire.core :as j]
            [clojure.edn :as edn]
            [clojure.java.io :as jio]))

(defn read-edn [file]
  (edn/read-string (-> file jio/resource slurp)))

(defn to-json [m]
        (j/generate-string m {:pretty true}))

(defn from-json [j]
  (j/parse-string j keyword))
