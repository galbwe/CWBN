(ns cwbn.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [ajax.core :refer [GET POST]]
            [cwbn.ajax :refer [load-interceptors!]]
            [cwbn.events]
            [cwbn.routes :as routes]
            [cwbn.pages.about :refer [about-page]]
            [cwbn.pages.contact :refer [contact-page]]
            [cwbn.pages.home :refer [home-page]]
            [cwbn.pages.category :refer [category-page]]
            [cwbn.pages.not-found :refer [not-found-page]]
            [cwbn.components.footer :refer [footer-component]]
            [cwbn.components.top-bar :refer [top-bar-component]]
            [re-frisk.core :refer [enable-re-frisk!]]))

(defn- show-page [page-name]
  (case page-name
        :home [home-page]
        :about [about-page]
        :contact [contact-page]
        :category [category-page]
        :not-found [not-found-page]
        [:div [:h1 "404"]]))

(defn page []
  [:div.page-wrapper
   [top-bar-component]
   [:div {:class "content pv4-ns pv3 ph4-ns ph3"}
    (show-page @(rf/subscribe [:active-page]))]
   [footer-component]])

;; -------------------------
;; Initialize app

(defn mount-components []
  (rf/clear-subscription-cache!)
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:initialize-db])
  (enable-re-frisk!)
  (rf/dispatch [:get-api-data :Organizations "/Organizations"])
  (rf/dispatch [:get-api-data :Categories "/Categories"])
  (rf/dispatch [:get-api-data :Services "/Services"])
  (rf/dispatch [:get-api-data :Types "/Types"])
  (rf/dispatch [:get-api-data :Tags "/Tags"])
  (load-interceptors!)
  (routes/app-routes)
  (mount-components))
