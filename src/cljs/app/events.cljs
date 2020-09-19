(ns app.events
  (:require
   [app.db]
   [clojure.edn]
   [day8.re-frame.http-fx]
   [re-frame.core :as rf :refer (reg-event-fx reg-event-db)]))

(reg-event-db
 :no-ops
 (fn [db] db))

(reg-event-fx
 ::init
 (fn [_ _]
   {:db app.db/db
    :fx [[:dispatch [:transparency.components.drawer/close]]]}))
