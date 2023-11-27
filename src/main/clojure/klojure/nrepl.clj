(ns klojure.nrepl
  (:require [nrepl.server])
  (:import [org.slf4j Logger LoggerFactory]))

(defonce ^Logger logger (LoggerFactory/getLogger (str *ns*)))
(defonce nrepl-server (atom nil))

(defn start-nrepl! [& {:keys [port]}]
  (if-not (nil? @nrepl-server)
    (do
      (.info logger (str "NREPL server already running on port " (:port @nrepl-server)))
      (assoc @nrepl-server :status "already-running"))
    (do
      (.info logger (str "Starting NREPL on port " port))
      (reset! nrepl-server (nrepl.server/start-server :port port))
      (assoc @nrepl-server :status "running"))))

(defn stop-nrepl! []
  (if (nil? @nrepl-server)
    (do
      (.warn logger "NREPL server NOT running!")
      {:status "already-stopped"})
    (do
      (.info logger "Stopping NREPL.")
      (nrepl.server/stop-server @nrepl-server)
      (reset! nrepl-server nil)
      {:status "stopped"})))
