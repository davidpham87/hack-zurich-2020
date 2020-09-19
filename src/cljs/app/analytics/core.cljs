(ns app.analytics.core
  (:require
   [app.components.colors :as colors]
   [app.components.mui-utils :refer (card)]
   [reagent.core :as reagent]
   [re-frame.core :as rf]
   [transparency.components.charts.scatter :refer (scatter-chart-raw)]
   [transparency.components.tabs :as tct]))


(defn card-tab-style [_]
  #js {:root     #js {:color       (colors/colors-rgb :emerald)
                      :font-weight 600 :opacity 1.0 :background-color "rgba(0,0,0,0)"
                      "&:hover" #js {:color (colors/colors-rgb :green)
                                     :opacity 1}}
       :selected #js {:border-radius 5 :color (colors/colors-rgb :emerald-dark)}})

(defn tabs []
  [tct/tabs-global
   {:id :performance
    :choices
    [{:label "Speak time" :value :speak-time}
     {:label "Interaction" :value :interaction}
     {:label "Emotions" :value :emotions}]
    :style card-tab-style}])

(defn random [n]
  (vec (repeatedly n (fn [] (+ 5 (rand-int 10))))))

(defn plot [tab]
  ^{:key tab}
  [scatter-chart-raw
   [{:x (random 5)
     :y ["Dominique" "Severin" "Chris" "David A." "David P."]
     :type :bar
     :orientation :h
     :name "Score"}]
   {:layout {:margin {:l 80}
             :legend {:reversemode true}
             :yaxis {:dtick 1
                     :autorange :reversed
                     :type :category}}}])

(defn root []
  (let [tab (rf/subscribe [::tct/tab-global :performance])]
    (fn []
      [card {:header
             {:title (reagent/as-element [tabs])}
             :content {:children [plot @tab]}}])))
