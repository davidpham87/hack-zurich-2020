(ns app.analytics.core
  (:require
   [app.components.colors :as colors]
   [app.components.mui-utils :refer (card)]
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [reagent.ratom :refer (make-reaction)]
   [transparency.components.charts.scatter :refer (scatter-chart-raw)]
   [transparency.components.tabs :as tct]
   [transparency.components.screen-size :as tcs]
   [transparency.reporting.user-input :as tru]))

(defn user-input [& [{:keys [inputs]}]]
  (let [start-date @(rf/subscribe [:user-input-field :start-date])
        end-date @(rf/subscribe [:user-input-field :end-date])]
    [tru/user-input
     (into (or inputs [])
           [(tru/picker
             {:id ::team
              :label "Team"
              :choices [{:label "On site" :value :on-site}
                        {:label "Remote" :value :remote}
                        {:label "Hybrid" :value :hybrid}]})
            (tru/start-date-picker
             {:value (when start-date (js/Date. start-date))})
            (tru/end-date-picker
             {:value (when end-date (js/Date. end-date))})])
     {:elevation 1 :square true}]))

(defn card-tab-style [_]
  #js {:root     #js {:color       (colors/colors-rgb :emerald)
                      :font-weight 600 :opacity 1.0 :background-color "rgba(0,0,0,0)"
                      "&:hover" #js {:color (colors/colors-rgb :green)
                                     :opacity 1}}
       :selected #js {:border-radius 5 :color (colors/colors-rgb :emerald-dark)}})

(defn tabs []
  (let [screen-size (rf/subscribe [::tcs/screen-size])
        choices [{:label "Ratings" :value :ratings}
                 {:label "Interaction Quality" :value :interaction-quality}
                 {:label "Fallacies" :value :fallacies}
                 {:label "Cognitive Bias" :value :cognitive-bias}
                 {:label "Listening" :value :listening}]]
    (fn []
      (case @screen-size
        (:xs :sm)
        [tru/render-user-input-config
         (tru/picker
          {:id :performance
           :event [::tct/set-tab-args :global :performance]
           :subscription [::tct/tab-global :performance]
           :choices choices})]
        [tct/tabs-global
         {:id :performance
          :choices choices
          :style card-tab-style}]))))

(defn random [n]
  (vec (repeatedly n (fn [] (+ 5 (rand-int 10))))))

(defn random-line [n]
  (vec (repeatedly n (fn [] (- 5 (rand-int 10))))))

(defn plot [tab data]
  [scatter-chart-raw
   [{:x (random 5)
     :y ["David" "Myriam" "Loan-Isaac"]
     :type :bar
     :orientation :h
     :name "Score"
     :marker {:color (colors/colors-hex :citrine-bright)}}]
   {:layout {:margin {:l 80}
             :legend {:reversemode true}
             :height 480
             :yaxis {:dtick 1
                     :autorange :reversed
                     :type :category}}}])

(defn plot-line [tab data]
  [scatter-chart-raw
   (vec (for [[person color] (map vector
                                  ["David" "Myriam" "Loan"]
                                  (mapv colors/colors-rgb [:aquamarine :nucleus-blue
                                                           :emerald]))]
          {:x (range 20)
           :y (reductions + (random-line 20))
           :type :lines
           :marker {:color color}
           :line {:shape :spline}
           :name person}))
   {:layout {:margin {:l 20}
             :yaxis {:dtick 5}
             :height 480
             :legend {:bordercolor :white :x 0 :y -0.05}}}])

(defn root []
  (let [tab (rf/subscribe [::tct/tab-global :performance])
        start-date (rf/subscribe [:user-input-field :start-date])
        end-date (rf/subscribe [:user-input-field :end-date])
        team (rf/subscribe [:user-input-field ::team])
        data (make-reaction (fn [] [@team @start-date @end-date]))]

    (when-not @team
      (rf/dispatch [:set-user-input ::team :on-site]))

    (fn []
      [:div {:style {:padding-left 10 :padding-right 10 :padding-top 10}}
       [user-input]
       [card {:elevation 1 :square true  :style {:height 650 :margin-bottom 20}}
        {:header
         {:title (reagent/as-element [tabs])}
         :content {:children [plot @tab @data]}}]
       [card {:elevation 1 :square true :style {:height 650 :margin-bottom 20}}
        {:header
         {:title (reagent/as-element [tabs])}
         :content {:children [plot-line @tab @data]}}]])))
