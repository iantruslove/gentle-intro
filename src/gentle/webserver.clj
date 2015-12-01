(ns gentle.webserver
  (require [ring.adapter.jetty :refer [run-jetty]]))

(defonce webserver (atom nil))

(defn start-webserver
  "Returns a running webserver."
  [handler]
  (run-jetty handler {:join? false
                      :port 8080}))

(defn stop-webserver [webserver]
  (.stop webserver))

(defn start! [handler]
  (reset! webserver (start-webserver handler)))

(defn stop! []
  (swap! webserver stop-webserver))
