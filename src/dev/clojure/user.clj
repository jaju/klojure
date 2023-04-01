(ns user
  (:require [nrepl.server :as nrepl]))

(println "I get auto loaded when the dev property of the klojure library gradle project is set to `true` at run/build time.")
(println "If you see this in the published library, it is an ERROR. Do not use this build.")