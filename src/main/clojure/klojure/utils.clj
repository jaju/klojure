(ns klojure.utils
  (:require [clojure.edn :as edn]
            [clojure.java.io :as jio]))

(defn- resource-slurp [file]
  (-> file jio/resource slurp))

(defn read-edn-file [file]
  (-> file slurp edn/read-string))

(defn read-edn-resource [file]
  (-> file resource-slurp edn/read-string))

(defn kmap->smap [m]
  (clojure.walk/stringify-keys m))