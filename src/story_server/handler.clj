(ns story-server.handler
  (:require [compojure.core :refer :all]
            [ring.util.response :refer [response file-response]]
            [ring.middleware.json :as middleware]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            ;; [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [story-server.datastore :refer :all]))

(defroutes handler
  (GET "/" [] "Story Server")
  (GET "/tropes/" [] (get-tropes))
  (POST "/tropes/new" [& data] (new-trope data))
  ;; (POST "/tropes/edit/" [& data] (edit-trope data))
  (POST "/tropes/edit" [& data] (edit-trope data))
  (POST "/tropes/delete" [id] (delete-trope id))
  (GET "/stories/" [] (get-stories))
  (POST "/stories/new" [& data] (response (new-story data)))
  (POST "/stories/event" [& data] (response (update-story data)))
  (POST "/stories/edit/:id" [id data] (edit-story id data))
  (POST "/stories/delete/:id" [id] (delete-story id))
  (GET "/characters/" [] (get-characters))
  ;; (GET "/characters/:role/" [role] (get-characters-by-role role))
  (POST "/characters/new/" [& data] (response (new-character data)))
  (POST "/characters/edit/:id" [id data] (edit-character id data))
  (POST "/characters/delete/:id" [id] (delete-character id))
  (GET "/objects/" [] (get-objects))
  (POST "/objects/new/" [data] (new-object data))
  (POST "/objects/edit/:id" [id data] (edit-object id data))
  (POST "/objects/delete/:id" [id] (delete-object id))
  (GET "/places/" [] (get-places))
  (POST "/places/new/" [data] (new-place data))
  (POST "/places/edit/:id" [id data] (edit-place id data))
  (POST "/places/delete/:id" [id] (delete-place id))
  (GET "/hello" [] "Hello Wold"))


(def cors-headers
  "Generic CORS headers"
  {"Access-Control-Allow-Origin"  "http://mthompson.org"
   "Access-Control-Allow-Headers" "X-Requested-With,Content-Type,Cache-Control,range,Content-Range,If-None-Match"
   "Access-Control-Expose-Headers" "X-Requested-With,Content-Type,Cache-Control,range,Content-Range,If-None-Match"
   "Access-Control-Allow-Methods" "GET, POST"})

(defn preflight?
  "Returns true if the request is a preflight request"
  [request]
  (= (request :request-method) :options))

(defn all-cors
  "Allow requests from all origins - also check preflight"
  [handler]
  (fn [request]
    (if (preflight? request)
      {:status 200
       :headers cors-headers
       :body "preflight complete"}
      (let [response (handler request)]
        (update-in response [:headers]
                   merge cors-headers )))))


(def app
  (-> handler
      all-cors
      wrap-keyword-params
      middleware/wrap-json-params
      middleware/wrap-json-response))
