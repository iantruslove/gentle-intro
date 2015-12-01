(ns gentle.snippets
  (:require [compojure.core :refer [GET PUT POST DELETE defroutes]]
            [compojure.route :as route]
            [gentle.util :as util]
            [ring.util.response :as resp]))

(defn url-root [req]
  (str (name (:scheme req))
       "://"
       (:server-name req)
       ":"
       (:server-port req)))

(defonce storage (atom {}))

(defn get-snippet-handler [req]
  (if-let [snippet (get @storage (util/uuid (get-in req [:params :snippet-id])))]
    (-> (resp/response (:body snippet))
        (resp/content-type (:content-type snippet)))
    (resp/not-found)))

(defn create-snippet-handler [req]
  (let [uuid (util/uuid)
        url (str (url-root req) "/snippets/" uuid "\n")]
    (swap! storage assoc uuid {:content-type (get-in req [:headers "content-type"])
                               :body (slurp (:body req))})
    (-> (resp/response "")
        (resp/status 302)
        (resp/header "Location" url))))

(defroutes snippets-api
  (GET "/snippets/:snippet-id" [snippet-id] #'get-snippet-handler)
  (POST "/snippets/" [] #'create-snippet-handler)
  (route/not-found "no such snippet"))
