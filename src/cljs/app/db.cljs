(ns app.db
  (:require
   [cljs-time.core :as ct]
   [cljs-time.format :as ctf]
   [datascript.core :as d]
   [transparency.db]
   [transparency.utils.dates]))

(defn deep-merge
  [a b]
  (if (map? a)
    (into a (for [[k v] b] [k (deep-merge (a k) v)]))
    b))

(defn iso-date [dt]
  (when (instance? goog.date.Date dt)
    (ctf/unparse (ctf/formatters :date) dt)))

(defn previous-weekday-iso []
  (->> (ct/minus (ct/now) (ct/days 1))
       transparency.utils.dates/weekend->weekday
       (ctf/unparse (ctf/formatters :date))))

(defn following-weekday-iso []
  (->> (ct/plus (ct/now) (ct/days 1))
       transparency.utils.dates/weekend->weekday
       (ctf/unparse (ctf/formatters :date))))

(def schema
  (merge #:pipeline.node{:id        {:db/unique :db.unique/identity}
                         :namespace {:db/index true}
                         :name      {}
                         :group     {}
                         :ops       {}
                         :parents   {:db/valueType   :db.type/ref
                                     :db/cardinality :db.cardinality/many}}
         #:pipeline{:id       {:db/unique :db.unique/identity}
                    :modified {}
                    :aliases  {:db/cardinality :db.cardinality/many}}
         #:ops{:id {:db/unique :db.unique/identity}}))

(def empty-ds
  "Value for resetting database"
  (d/empty-db schema))


(def db
  (->>
   {:project-key   "app"
    :project-title "Hackathon 2020"
    :ds
    {:models         (d/empty-db schema)
     :pipelines-data (d/empty-db schema)
     :xhrio          (d/empty-db {:request/id {:db/unique :db.unique/value}})}

    :user-input
    {:selected-user   "Hackathon"
     :start-date
     (iso-date (transparency.utils.dates/weekend->weekday
                (ct/minus (ct/now) (ct/years 1) (ct/days 1))))

     :end-date              (previous-weekday-iso)
     :frequency             :monthly
     :performance-frequency :monthly}}
   (deep-merge transparency.db/default-db)))
