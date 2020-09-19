(ns app.home.core
  (:require
   ["@material-ui/core/Grid" :default mui-grid]
   [app.components.mui-utils :refer (card)]
   [transparency.components.charts.indicator
    :refer (indicator-chart score->color)]))

;; (defn standardize-counts [x]
;;   (-> (+ x 12) (/ 24)))

(defn indicators []
  (let [data [65 35 60 15 90]
        plot-colors (mapv #(-> (* / % 100) score->color) data)
        cols ["Speak Time" "Rotation" "Emotions" "Reactions" "Ratings"]]
    [:> mui-grid {:container :true :spacing 4 :align-items :stretch
                  :style {:padding-top 10}}
     (for [[v title color] (map vector data cols plot-colors)
           :let [card-title (str "Signal of "  title)]]
       ^{:key (gensym "emotion-id")}
       [:> mui-grid {:item :true :xs 12 :md 6 :lg 4}
        [indicator-chart
         {:value v
          :range [0 100]
          :color color
          :title title
          :layout {:height 200
                   :margin {:l 0 :t 0 :b 0 :r 0}}}]])]))


(defn root []
  [:> mui-grid {:container true :style {:padding 20}}
   [:> mui-grid {:item true :xs 12  :lg 8}
    [indicators]]])
