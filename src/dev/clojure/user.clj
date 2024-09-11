(ns user
  (:import [org.slf4j LoggerFactory]))

(defonce logger (LoggerFactory/getLogger klojure.Dev))

(.error logger "[Dev] I get auto loaded when the dev property of the klojure library gradle project is set to `true` at run/build time.")
(.error logger "[Dev] If you see this in the published library, it is an ERROR. Do not use this build.")
