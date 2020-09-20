(ns app.team.core
  (:require
   ["@material-ui/core/Card" :default mui-card]
   ["@material-ui/core/CardMedia" :default mui-card-media]
   ["@material-ui/core/Grid" :default mui-grid]
   [re-frame.core :refer (subscribe)]
   [app.components.mui-utils :refer (card-content card-header)]
   [transparency.components.screen-size :as tcs]))

(def team-data
  [{:image 1041
    :title "Dominique"
    :subtitle "Team Representative "
    :details "Againt trash food."}
   {:image 1048
    :title "Severin"
    :subtitle "Designer"
    :details "I eat and design plants."}
   {:image 1056
    :title "Chris"
    :subtitle "Engineer"
    :details "The curse of backend and frontend."}
   {:image 1067
    :title "David A."
    :subtitle "Engineer"
    :details "Mr. PhD."}
   {:image 108
    :title "David P."
    :subtitle "Engineer"
    :details "Clojure for the win."}])

(defn member-card [{:keys [image title subtitle details]}]
  (let [screen-size @(subscribe [::tcs/screen-size])]
    [:> mui-card {:style {:padding (if (= screen-size :xs) 1 10)}
                  :elevation 4}
     [:> mui-grid {:container true}
      [:> mui-grid {:item true :xs 5 :sm 4 :md 3}
       [:> mui-card-media
        {:style {:height "100%" :border-radius "100%"
                 :padding 10}
         :image (str "https://picsum.photos/id/" image "/150")}]]
      [:> mui-grid {:item true :xs 7 :sm 8 :md 9}
       [card-header {:title title :subheader subtitle}]
       [card-content {:children details}]]]]))

(defn root []
  (let [screen-size @(subscribe [::tcs/screen-size])]
    [:<>
     [:div {:style {:margin-top 80}}]
     [:> mui-grid {:container true :spacing 2
                   :style {:padding (if (= screen-size :xs) 0 20)}
                   :justify :center}
      (for [m team-data]
        [:> mui-grid {:item true :lg 6 :md 8 :sm 10 :xs 12}
         [member-card m]])]])
  )
