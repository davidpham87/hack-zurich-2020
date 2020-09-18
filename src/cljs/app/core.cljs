(ns app.core
  (:require
   [app.db]
   [app.router]
   [app.events :as events]
   [app.views :refer (app)]
   [re-frame.core :as rf]
   [reagent.dom :as dom]
   [transparency.events.core]
   [transparency.subs]))

(defn mount-app []
  (dom/render [app] (.getElementById js/document "app")))

(defn main []
  (rf/dispatch [::events/init])
  (app.router/init-routes!)
  (mount-app))

(defn ^:dev/after-load restart []
  (app.router/init-routes!)
  (mount-app))
