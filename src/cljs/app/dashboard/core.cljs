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
   [transparency.components.charts.line :refer (line-chart-raw)]
   [transparency.components.charts.dot-plot :as tccd]))

(defn indicators []
  (let [data (repeatedly 5 #(vector (rand-int 100) (rand-int 100)))
        plot-colors (mapv #(-> (* / % 100) score->color) data)
        cols ["Cognitive Bias" "Fallacies" "Interaction Quality" "Tools" "Ratings"]]
    [card {:style {:background-color (colors/colors-rgb :graphite)}
           :elevation 0 :square true}
     {:content
      {:children
       [:div {:style {:display :grid :grid-template-columns "minmax(auto, 20%) 1fr"
                      :align-items :center
                      :gap 20}}
        [:> mui-typography {:style {:color :white}} "Team Scientific Reasoning Score"]
        [tccd/dot-plot
         {:date-range {:start-date "2021-01" :end-date "2021-08"}
          :height 250 :range [0 100]
          :labels cols
          :title "Scientific Reasoning Score"}
         (mapv #(-> {:value %1 :label %2}) data cols)]]}}]))

(defn summary-card []
  (let [bc-color (colors/colors-rgb :graphite)]
    [card
     {:square    true
      :elevation 0
      :style     {:height  "100%" :background-color bc-color :color :white
                  :display :flex  :flex-direction   :column}}
     {:header  {:title "Total Growth Points"}
      :content {:style {:flex 1}
                :children
                [:div {:style {:display :flex :justify-content :center :align-items :center
                               :color   (colors/colors-rgb :green-light-bright)
                               :height  "100%"}}
                 [:> mui-typography {:variant :h4} "1250 Growth Points"]]}
      :actions {:children
                [:<>
                 [:> mui-divider]
                 [left-right
                  "Last session"
                  [:div {:style {:color (colors/colors-rgb :green-light-bright)}} "+120"]]]}
      :style   {:height "100%"}}]))

(defn score-over-time []
  [:div {:style {:margin-top 10}}
   [line-chart-raw
    [{:x         ["2021-01-01" "2021-02-01" "2021-03-01" "2021-04-01" "2021-05-01" "2021-06-01"]
      :y         [10 60 30 90 70 80 100]
      :name      "Team Scientific Reasoning Score"
      :fill      :tozeroy
      :fillcolor :lightblue
      :line      {:shape :spline
                  :color :lightblue}}]
    {:layout {:margin        {:r 30 :b 30 :l 50}
              :font          {:color :white :family "helvetica"}
              :paper_bgcolor "rgba(0,0,0,0)"
              :plot_bgcolor "rgba(0,0,0,0)"
              :xaxis {:gridcolor "rgba(255,255,255,0.3)"}}}]])

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
               [left-right "Loan-Isaac"
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
   [:> mui-grid {:item true :xs 12 :lg 8}
    [indicators]]
   [:> mui-grid {:item true :xs 12 :lg 12}
    [score-over-time]]])
