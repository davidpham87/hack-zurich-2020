(ns app.team.core
  (:require
   ["@material-ui/core/Card" :default mui-card]
   ["@material-ui/core/CardMedia" :default mui-card-media]
   ["@material-ui/core/Grid" :default mui-grid]
   [app.components.mui-utils :refer (card-content left-right)]))

(def team-data
  [{:image 1041
    :title "Dominique"}
   {:image 1048
    :title "Severin"}
   {:image 1056
    :title "Chris"}
   {:image 1067
    :title "David A."}
   {:image 108
    :title "David P."}])

(defn member-card [{:keys [image title]}]
  [:> mui-card
   [:> mui-grid {:container true}
    [:> mui-grid {:item true :xs 3}
     [:> mui-card-media
      {:style {:width 150 :height 150}
       :image (str "https://picsum.photos/id/" image "/150")}]]
    [:> mui-grid {:item true :xs 9}
     [card-content {:children title}]]]])

(defn root []
  [:<>
   [:div {:style {:margin-top 80}}]
   [:> mui-grid {:container true :spacing 4 :style {:padding 20}}
    (for [m team-data]
      [:> mui-grid {:item true :lg 6 :xs 12}
       [member-card m]])]])
