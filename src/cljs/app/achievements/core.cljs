(ns app.achievements.core
  (:require
   ["@material-ui/core/Card" :default mui-card]
   ["@material-ui/core/CardMedia" :default mui-card-media]
   ["@material-ui/core/Grid" :default mui-grid]
   [app.components.mui-utils :refer (card-content left-right)]))

(def achievements-data
  [{:image 1019
    :title "Fertilizer Boost"}
   {:image 102
    :title "Golden Watering"}])

(defn achievement-card [{:keys [image title]}]
  [:> mui-card
   [:> mui-grid {:container true}
    [:> mui-grid {:item true :xs 3}
     [:> mui-card-media
      {:style {:height "100%"}
       :image (str "https://picsum.photos/id/" image "/150")}]]
    [:> mui-grid {:item true :xs 9}
    [card-content {:children title}]]]])

(defn root []
  [:<>
   [:div {:style {:margin-top 80}}]
   [:> mui-grid {:container true :spacing 4 :style {:padding 20}}
    (for [m achievements-data]
      [:> mui-grid {:item true :lg 6 :xs 12}
       [achievement-card m]])]])
