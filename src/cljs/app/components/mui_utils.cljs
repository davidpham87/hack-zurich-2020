(ns app.components.mui-utils
  (:require
   [transparency.components.colors :as colors]
   ["@material-ui/core/Card" :default mui-card]
   ["@material-ui/core/CardActions" :default mui-card-actions]
   ["@material-ui/core/CardContent" :default mui-card-content]
   ["@material-ui/core/CardHeader" :default mui-card-header]
   ["@material-ui/core/Grid" :default mui-grid]
   ["@material-ui/core/styles" :refer (createTheme)]))

(defn card-header [m]
  [:> mui-card-header (dissoc m :children) (:children m)])

(defn card-content [m]
  [:> mui-card-content (dissoc m :children) (:children m)])

(defn card-actions [m]
  [:> mui-card-actions (dissoc m :children) (:children m)])

(defn card
  ([m] [card {} m])
  ([props {:keys [header content actions] :as m}]
   [:> mui-card (or props {})
    [card-header header]
    [card-content content]
    [card-actions actions]]))

(def custom-theme
  (let [headline-fonts #js {:fontFamily #js ["Roboto" "Helvetica"]}]
    (createTheme
     #js {:palette #js
          {:primary #js {:main (colors/colors-rgb :graphite)}
           :secondary #js {:main (colors/colors-rgb :citrin)}
           :type "light"}
          :typography #js
          {:fontFamily #js ["Helvetica"]
           :p #js {:fontFamily #js ["Helvetica"]}
           :h1 headline-fonts
           :h2 headline-fonts
           :h3 headline-fonts
           :h4 headline-fonts
           :h5 headline-fonts
           :h6 headline-fonts}})))

(defn left-right [left right]
  [:> mui-grid {:container true :justify :space-between :align-items :center}
   [:> mui-grid {:item true} left]
   [:> mui-grid {:item true} right]])
