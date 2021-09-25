(ns app.achievements.core
  (:require
   ["@material-ui/core/Typography" :default mui-typography]
   ["@material-ui/core/Card" :default mui-card]
   ["@material-ui/core/CardMedia" :default mui-card-media]
   ["@material-ui/core/Grid" :default mui-grid]
   [app.components.mui-utils :refer (card-content left-right)]))

(def achievements-data
  [{:image 1019
    :title "Reading Skill Booster"}
   {:image 102
    :title "Crazy Doubt Stars"}
   {:image 110
    :title "Hidden Biases Lighter"}])

(defn achievement-card [{:keys [image title]}]
  [:> mui-card {:style {:display :flex :align-items :center}}
   [:> mui-card-media
    {:style {:height 100
             :width 100
             :margin 5
             :border-radius "100%"}
     :image (str "https://picsum.photos/id/" image "/100")}]
   [card-content {:children [:> mui-typography {:variant :h6} title]}]])

(defn root []
  [:<>
   [:div {:style {:margin-top 80}}]
   [:> mui-grid {:container true :spacing 4 :style {:padding 20}}
    (for [m achievements-data]
      [:> mui-grid {:item true :lg 6 :xs 12}
       [achievement-card m]])]])
