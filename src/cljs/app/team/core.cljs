(ns app.team.core
  (:require
   ["@material-ui/core/Card" :default mui-card]
   ["@material-ui/core/CardMedia" :default mui-card-media]
   ["@material-ui/core/Grid" :default mui-grid]
   [re-frame.core :refer (subscribe)]
   [app.components.mui-utils :refer (card-content card-header)]
   [transparency.components.screen-size :as tcs]))

(def team-data
  [{:image 108
    :title "David"
    :subtitle "The Failed Mathematician"
    :details "Clojure for the win."}
   {:image 1041
    :title "Myriam"
    :subtitle "The Anthropologist"
    :details "She is David's hear."}
   {:image 1048
    :title "Loan"
    :subtitle "Product from the David and Myriam Collaboration"
    :details "Born on Valentine's Day."}])

(defn member-card [{:keys [image title subtitle details]}]
  (let [screen-size @(subscribe [::tcs/screen-size])]
    [:> mui-card {:style {:padding (if (= screen-size :xs) 1 10)
                          :width 480}
                  :elevation 4}
     [:div {:style {:display :flex}}
      [:> mui-card-media
       {:style {:border-radius "100%"
                :width 180
                :height 180
                :aspect-ratio "1 / 1"
                :padding 10}
        :image (str "https://picsum.photos/id/" image "/150")}]
      [:div
       [card-header {:title title :subheader subtitle}]
       [card-content {:children details}]]]]))

(defn root []
  (let [screen-size @(subscribe [::tcs/screen-size])]
    [:<>
     [:div {:style {:margin-top 80}}]
     [:> mui-grid {:container true :spacing 2
                   :style {:padding (if (= screen-size :xs) 0 20)}
                   :justify :space-around}
      (for [m team-data]
        [:> mui-grid {:item true :xs 12}
         [:div {:style {:width "100%" :display :grid :place-content :center}}
          [member-card m]]])]]))
