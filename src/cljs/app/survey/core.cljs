(ns app.survey.core
  (:require
   ["@material-ui/core/Button" :default mui-button]
   ["@material-ui/core/Grid" :default mui-grid]
   ["@material-ui/lab/Rating" :default mui-rating]
   ["@material-ui/core/Typography" :default mui-typography]
   [app.components.mui-utils :refer (card card-header)]
   [transparency.components.reitit :as tcr]
   [re-frame.core :as rf]))

(defn rating [id]
  (let [value (rf/subscribe [:user-input-field (keyword :rating id)])]
    (fn [id]
      [:div {:style {:display :flex :justify-content :center}}
       [:> mui-rating
        {:name id
         :value @value
         :on-change
         (fn [e v]
            (rf/dispatch
             [:set-user-input (keyword :rating id) v]))}]])))

(defn end-survey-button []
  [:> mui-button
   {:on-click #(rf/dispatch [::tcr/navigate :app.views/home])
    :style {:maring-left :auto}}
   "End survey"])

(defn root []
  [:<>
   [:> mui-grid {:container true :spacing 4 :style {:padding 10}}
    [:> mui-grid {:item true :xs 12}
     [card-header {:title "End of meeting feedback"}]]
    [:> mui-grid {:item true :xs 12 :md 6}
     [card {:header {:title "How would you rate the meeting overall?"
                     :subheader "Did you spend a good time?"}
            :content {:children [rating "overall"]}
            :actions {:children [end-survey-button]}}]]
    [:> mui-grid {:item true :xs 12 :md 6}
     [card
      {:header {:title "Do you think the meeting was productive?"
                :subheader "Did we reach the goal set for the meeting?"}
       :content {:children [rating "productive"]}
       :actions {:children [end-survey-button]}}]]]])
