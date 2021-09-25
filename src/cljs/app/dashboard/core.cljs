(ns app.dashboard.core
  (:require
   ["@material-ui/core/Button" :default mui-button]
   ["@material-ui/core/Divider" :default mui-divider]
   ["@material-ui/core/Grid" :default mui-grid]
   ["@material-ui/core/Typography" :default mui-typography]
   ["@material-ui/icons/Restaurant" :default ic-restaurant]
   ["@material-ui/icons/School" :default ic-school]
   [app.components.colors :as colors]
   [app.components.mui-utils :refer (card left-right)]
   [reagent.core :as reagent]
   [transparency.components.charts.indicator
    :refer (indicator-chart score->color)]
   [transparency.components.charts.line :refer (line-chart-raw)]))

(defn indicators []
  (let [data (repeatedly 5 #(rand-int 100))
        plot-colors (mapv #(-> (* / % 100) score->color) data)
        cols ["Speak Time" "Rotation" "Emotions" "Reactions" "Ratings"]]
    [:> mui-grid {:container :true :spacing 4 :align-items :stretch}
     (for [[v title color] (map vector data cols plot-colors)]
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
     {:square true
      :style {:height "100%" :background-color bc-color :color :white
              :display :flex :flex-direction :column}}
     {:header {:title "Total Growth Points"}
      :content {:style {:flex 1}
                :children
                [:div {:style {:display :flex :justify-content :center :align-items :center
                               :height "100%"}}
                 [:> mui-typography {:variant :h4} "1250 Growth Points"]]}
      :actions {:children
                [:<> [:> mui-divider] [left-right"Last session" "+120"]]}
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
   [:> mui-grid {:item true :xs 12}
    [card {:style {:background-color "rgb(206, 238, 253)"}}
     {:actions
      {:children [:div {:style {:display :flex :justify-content :flex-end
                                :gap 20
                                :width "100%"}}
                  [:> mui-button {:variant :contained :color :primary :start-icon
                                  (reagent/as-element [:> ic-restaurant])}
                   "Feed me"]
                  [:> mui-button {:variant :contained :color :primary
                                  :start-icon (reagent/as-element [:> ic-school])}
                   "Educate me"]]}
      :header
      {:title (reagent/as-element
               [left-right "Loan"
                [:> mui-button {:variant :contained :color :secondary} "Customize"]])
       :subheader "Zetetician Hacker"}
      :content
      {:children
       [:> mui-grid {:container true :justify-content :center}
        [:> mui-grid {:item true :xs 6 :lg 4}
         [:img {:src "img/3600_3_10.webp"
                :style {:width "100%" :border-radius "100%"}}]]]}}]]
   [:> mui-grid {:item true :xs 12 :md 12 :lg 4}
    [summary-card]]
   [:> mui-grid {:item true :xs 12  :lg 8}
    [indicators]]
   [:> mui-grid {:item true :xs 12  :lg 4}
    [score-over-time]]])
