(ns klojure.reflection-utils
  (:require [clojure.string :as string])
  (:import [java.lang.reflect Method]))

(defn- call [^Object o ^Method m args-coll]
  (.invoke m o (into-array Object args-coll)))

(defn- param-types [^Method m]
  (.getParameterTypes m))

(defn- find-methods [o name-prefix]
  (let [clazz (class o)
        methods (.getMethods clazz)
        match-fn (fn [^Method m] (string/starts-with? (.getName m) name-prefix))
        matching-methods (filter match-fn methods)]
    matching-methods))

(defn match-method [^Method m & signature]
  (let [params-types (param-types m)
        params-types (into [] params-types)]
    (println params-types)
    (println signature)
    (= params-types signature)))

(comment
  (def ms (.getMethods String))
  (def m (first ms))
  (into [] (param-types m))
  (-> ms first #_(map class)  (match-method Object)))

#_(defn- meth-handle? [^Object o ^String maybe-method-name]
  (let [max-arity 4
        methods? (find-methods o maybe-method-name)
        _ (assert (not-empty methods?))
        _ (assert (< (count methods?) (inc max-arity)))
        sorted-methods-by-param-count (get-methods-by-sorted-param-count methods?)
        safe-call (fn [o m args]
                    (if (nil? m) (throw (Exception. (str "Unsupported signature")))
                                 (call o m args)))]
    (fn
      ([]
       (let [m (get sorted-methods-by-param-count 0)]
         (println "Calling method without args")
         (safe-call o m [])))
      ([a1]
       (let [m (get sorted-methods-by-param-count 1)]
         (println "Calling method with 1 arg")
         (safe-call o m [a1])))
      ([a1 a2]
       (let [m (get sorted-methods-by-param-count 2)]
         (println "Calling method with 2 args")
         (safe-call o m [a1 a2])))
      ([a1 a2 a3]
       (let [m (get sorted-methods-by-param-count 3)]
         (println "Calling method with 3 args")
         (safe-call o m [a1 a2 a3])))
      ([a1 a2 a3 a4]
       (let [m (get sorted-methods-by-param-count 4)]
         (println "Calling method with 3 args")
         (safe-call o m [a1 a2 a3 a4]))))))