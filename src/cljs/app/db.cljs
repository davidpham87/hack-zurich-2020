(ns app.db
  (:require
   [cljs-time.core :as ct]
   [cljs-time.format :as ctf]
   [datascript.core :as d]
   [re-frame.core :refer (reg-cofx)]
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

    :ui-states {:transparency.components.drawer/open? false}

    :user-input
    {:selected-user   "Hackathon"
     :start-date
     (iso-date (transparency.utils.dates/weekend->weekday
                (ct/minus (ct/now) (ct/years 1) (ct/days 1))))

     :end-date              (previous-weekday-iso)
     :frequency             :monthly
     :performance-frequency :monthly}}
   (deep-merge transparency.db/default-db)))

(def fcw-user-key "finance-clash-web-user")  ;; localstore key

(defn set-user-ls
  "Puts user into localStorage"
  [user]
  (.setItem js/localStorage fcw-user-key (str user))) ;; sorted-map written as an EDN map

;; Removes user information from localStorge when a user logs out.
(defn remove-user-ls
  "Removes user from localStorage"
  []
  (.removeItem js/localStorage fcw-user-key))

(defn set-ls
  "Puts a key into localStorage"
  [k v]
  (.setItem js/localStorage k (clj->js v)))

(defn remove-ls
  [k]
  (.removeItem js/localStorage k ))

;; -- cofx Registrations  -----------------------------------------------------
;;
;; To see it used, look in `events.cljs` at the event handler for `:initialise-db`.
;; That event handler has the interceptor `(inject-cofx :local-store-user)`
;; The function registered below will be used to fulfill that request.
;;
;; We must supply a `sorted-map` but in localStorage it is stored as a `map`.
(reg-cofx
 :local-store-user
 (fn [cofx _]
   (assoc cofx :local-store-user
          (into (sorted-map)
                (some->> (.getItem js/localStorage fcw-user-key)
                         (cljs.reader/read-string))))))


(reg-cofx
 :local-store
 (fn [cofx local-store-key]
   (assoc cofx :local-store
          (into (sorted-map)
                (some->> (.getItem js/localStorage local-store-key)
                         (cljs.reader/read-string))))))
