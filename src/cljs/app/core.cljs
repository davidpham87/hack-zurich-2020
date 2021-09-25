(ns app.core
  (:require
   [app.db]
   [app.events :as events]
   [app.router]
   [app.views :refer (app)]
   [re-frame.core :as rf]
   [reagent.dom :as dom]
   [transparency.components.screen-size :refer (window-event-listeners!)]
   [transparency.components.scroll]
   [transparency.events.core]
   [transparency.subs]))

(defn mount-app []
  (dom/render [app] (.getElementById js/document "app")))

(defn main []
  (rf/dispatch [::events/init])
  (transparency.components.scroll/window-event-listeners!)
  (window-event-listeners!)
  (app.router/init-routes!)
  (mount-app))

(defn ^:dev/after-load restart []
  (app.router/init-routes!)
  (mount-app))
