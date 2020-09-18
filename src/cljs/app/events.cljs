(ns app.events
  (:require
   [ajax.core :as ajax :refer (json-request-format json-response-format)]
   [app.db]
   [camel-snake-kebab.extras :as cske]
   [cljs-bean.core :refer (->js ->clj)]
   [clojure.edn]
   [clojure.string :as str]
   [clojure.walk :refer (postwalk postwalk-replace)]
   [datascript.core :as d]
   [day8.re-frame.http-fx]
   [goog.object :as gobj]
   [re-frame.core :as rf :refer (reg-event-fx reg-event-db)]
   [transparency.components.feedback :as tcf]
   [transparency.components.history :as tch]))

(reg-event-db
 :no-ops
 (fn [db] db))

(reg-event-fx
 ::init
 (fn [_ _]
   {:db app.db/db
    :fx [[:dispatch [:transparency.components.drawer/close]]]}))
