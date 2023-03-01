(ns klojure.utils
  (:require [clojure.edn :as edn]
            [clojure.java.io :as jio]))

(defn read-edn-file [file]
  (edn/read-string (-> file jio/resource slurp)))