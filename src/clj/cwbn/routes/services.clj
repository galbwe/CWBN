(ns cwbn.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [cwbn.routes.services.graphql :as graphql]
            [schema.core :as s]))

(defn handle-graphql [req]
  (prn "ran")
  (ok (graphql/execute-request (slurp (req :body)))))

(defapi service-routes
  (context "/api" []

    ;;for swagger docs
    :tags []

    (POST "/graphql" [:as {body :body}]
      handle-graphql)))

