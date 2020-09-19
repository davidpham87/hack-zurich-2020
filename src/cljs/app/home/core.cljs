(ns app.home.core
  (:require
   ["@material-ui/core/Grid" :default mui-grid]
   [app.components.mui-utils :refer (card left-right)]
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
       [:> mui-grid {:item :true :xs 12 :md 4 :lg 4}
        [indicator-chart
         {:value v
          :range [0 100]
          :color color
          :title title
          :layout {:height 220
                   :margin {:l 0 :t 0 :b 0 :r 0}}}]])]))


(defn summary-card []
  [card
   {:style {:height "99%"}}
   {:header {:title "Total Growth Points"}
    :content {:children "1250 Growth Points"
              :style {:height "80%"}}
    :actions {:children
              [:<>
               [left-right
                "Last session"
                "+120"]]}
    :style {:height "100%"}}])

(defn score-over-time []

  )


(defn root []
  [:> mui-grid {:container true :style {:padding 20} :spacing 2
                :align-items :stretch}
   [:> mui-grid {:item true :xs 12 :lg 8}
    [:img {:src "img/jasper.png" :style {:width "100%"}}]]
   [:> mui-grid {:item true :xs 12 :md 12 :lg 4}
    [summary-card]]
   [:> mui-grid {:item true :xs 12  :lg 8}
    [indicators]]
   [:> mui-grid {:item true :xs 12  :lg 4}
    "Plot"]])
