(ns app.router
  (:require
   [re-frame.core :refer (dispatch)]
   [reitit.coercion.spec :as rss]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [app.views :refer (routes)]
   [transparency.components.reitit :as tcr]))

(defn on-navigate [new-match]
  (when new-match
    (dispatch [::tcr/navigated new-match])))

(defn router []
  (rf/router routes {:data {:coercion rss/coercion}}))

(defn init-routes!
  ([] (init-routes! (router)))
  ([router]
   (rfe/start! router on-navigate {:use-fragment true})))
