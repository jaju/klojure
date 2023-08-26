(ns klojure.reflection-utils
  (:require [clojure.string :as string])
  (:import [java.lang.reflect Method]))

(defn- param-types [^Method m]
  (into [] (.getParameterTypes m)))

#_(defn- find-methods-by-prefix [o name-prefix]
  (let [clazz (class o)
        methods (.getMethods clazz)
        match-fn (fn [^Method m] (string/starts-with? (.getName m) name-prefix))
        matching-methods (filter match-fn methods)]
    matching-methods))

(defn- find-methods-by-prefix [o prefix]
  (->> o
       class
       .getMethods
       (filter #(.startsWith (.getName %) prefix))))


#_(defn match-method [^Method m signature]
  (let [params-types (param-types m)]
    (if (nil? signature)
      (empty? params-types)
      (= params-types signature))))

(defn match-method [^Method m signature]
  (let [param-types (param-types m)]
    (if signature
      (= param-types signature)
      (empty? param-types))))


(defn- call [^Object o ^Method m args-coll]
  (.invoke m o (into-array Object args-coll)))

#_(defn search-wrap-by-signature [^Object o ^String prefix & signature]
  (let [methods (find-methods-by-prefix o prefix)
        method (filter (fn [^Method m] (match-method m signature)) methods)
        method (first method)]
    (if (nil? method)
      (throw (Exception. (str "Could not find method matching prefix " prefix " and with signature " signature)))
      (fn [& args]
        (call o method (or args []))))))

(defn wrap-method
  [^Object o signature ^Method m]
  (fn [& args]
    (.invoke m o (into-array Object (or args [])))))

(defn search-wrap-by-signature
  [^Object o ^String prefix & signature]
  (->> o
       (.getClass)
       (.getMethods)
       (filter #(.startsWith (.getName %) prefix))
       (filter #(match-method % signature))
       first
       (wrap-method o signature)))

