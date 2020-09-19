(ns app.survey.core
  (:require
   ["@material-ui/core/Button" :default mui-button]
   ["@material-ui/core/Grid" :default mui-grid]
   ["@material-ui/lab/Rating" :default mui-rating]
   ["@material-ui/core/Typography" :default mui-typography]
   [reagent.core :as reagent]
   [app.components.mui-utils :refer (card card-header)]
   [transparency.components.reitit :as tcr]
   [re-frame.core :as rf]))

(defn rating [id choices]
  (let [value (rf/subscribe [:user-input-field (keyword :rating id)])]
    (fn [id choices]
      [:<>
       [:div {:style {:display :flex :justify-content :center}}
        [:> mui-rating
         {:name id
          :value @value
          :on-change
          (fn [e v]
            (rf/dispatch
             [:set-user-input (keyword :rating id) v]))}]]
       [:div {:style {:display :flex :justify-content :center :margin-top 10}}
        (get choices (dec @value) "Mmmh... Waiting for your choice.")]])))

(defn end-survey-button []
  [:> mui-button
   {:on-click #(rf/dispatch [::tcr/navigate :app.views/home])
    :style {:maring-left :auto}}
   "End survey"])

(defn root []
  [:<>
   [:> mui-grid {:container true :spacing 4 :style {:padding 10}
                 :justify :space-around}
    [:> mui-grid {:item true :xs 12 :md 8}
     [card-header
      {:title (reagent/as-element
               [:div "Tell "
                [:b "Jasper "]
                "about your last call"])}]]
    [:> mui-grid {:item true :xs 12 :md 8}
     [card {:header {:title "How was the mood of the call overall?"
                     :subheader "Did you spend a good time?"}
            :content {:children [rating "overall" ["Bad" "Ok" "Alright" "Pretty Good" "Amazing"]]}
            :actions {:children [end-survey-button]}}]]
    [:> mui-grid {:item true :xs 12 :md 8}
     [card
      {:header {:title "Are you happy with your contribution"
                :subheader "Do you feel people listened to your ideas?"}
       :content {:children [rating "contribution" ["No" "I was quiet." "Hmm.." "Yes" "I was too chatty."]]}
       :actions {:children [end-survey-button]}}]]
    [:> mui-grid {:item true :xs 12 :md 8}
     [card
      {:header {:title "Do you think the meeting was productive?"
                :subheader "Did we reach the goal set for the meeting?"}
       :content {:children [rating "productive" ["No" "No" "Meh..." "Yes" "Yes"]]}
       :actions {:children [end-survey-button]}}]]]])
