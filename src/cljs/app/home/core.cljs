(ns app.home.core
  (:require
   ["@material-ui/core/Grid" :default mui-grid]
   ["@material-ui/core/Divider" :default mui-divider]
   ["@material-ui/core/Typography" :default mui-typography]
   [app.components.colors :as colors]
   [app.components.mui-utils :refer (card left-right)]
   [transparency.components.charts.indicator
    :refer (indicator-chart score->color)]
   [transparency.components.charts.line :refer (line-chart-raw)]))

(defn indicators []
  (let [data (repeatedly 5 #(rand-int 100))
        plot-colors (mapv #(-> (* / % 100) score->color) data)
        cols ["Speak Time" "Rotation" "Emotions" "Reactions" "Ratings"]]
    [:> mui-grid {:container :true :spacing 4 :align-items :stretch
                  :style {:padding-top 10}}
     (for [[v title color] (map vector data cols plot-colors)
           :let [card-title (str "Signal of "  title)]]
       ^{:key (gensym "emotion-id")}
       [:> mui-grid {:item :true :xs 12 :md 4 :lg 4}
        [indicator-chart
         {:value v
          :range [0 100]
          :color color
          :title title
          :layout {:height 220
                   :margin {:l 0 :t 0 :b 0 :r 0}}}]])]))

(defn summary-card []
  (let [bc-color (colors/colors-rgb :green-light)]
    [card
     {:style {:height "97%" :background-color bc-color :color :white}}
     {:header {:title "Total Growth Points"}
      :content {:style {:height "80%"}
                :children
                [:div {:style {:display :flex :justify-content :center :align-items :center
                               :height "100%"}}
                 [:> mui-typography {:variant :h4} "1250 Growth Points"]]}
      :actions {:children
                [:<>
                 [:> mui-divider]
                 [left-right"Last session" "+120"]]}
      :style {:height "100%"}}]))

(defn score-over-time []
  [:div {:style {:margin-top 10}}
   [line-chart-raw
    [{:x ["2020-01-01" "2020-02-01" "2020-03-01" "2020-04-01" "2020-05-01" "2020-06-01"]
      :y [10 60 30 90 70 80 100]
      :name "Team Spirit"
      :fill :tozeroy
      :fillcolor (colors/colors-hex :green-light-bright)
      :line {:shape :spline
             :color (colors/colors-hex :green-light-bright)}}]]])


(defn root []
  [:> mui-grid {:container true :style {:padding 20} :spacing 2
                :align-items :stretch}
   [:> mui-grid {:item true :xs 12 :lg 8}
    [:img {:src "img/jasper.png" :style {:width "100%"}
           :title "This is probably the only fake picture of the website."}]]
   [:> mui-grid {:item true :xs 12 :md 12 :lg 4}
    [summary-card]]
   [:> mui-grid {:item true :xs 12  :lg 8}
    [indicators]]
   [:> mui-grid {:item true :xs 12  :lg 4}
    [score-over-time]]])
