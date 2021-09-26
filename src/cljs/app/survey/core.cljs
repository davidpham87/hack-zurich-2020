(ns app.survey.core
  (:require
   ["@material-ui/core/Button" :default mui-button]
   ["@material-ui/core/Grid" :default mui-grid]
   ["@material-ui/lab/Rating" :default mui-rating]
   ["@material-ui/core/Typography" :default mui-typography]
   [reagent.core :as reagent]
   [app.components.mui-utils :refer (card card-header)]
   [transparency.components.motion :as motion]
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
   {:on-click #(rf/dispatch [::tcr/navigate :app.views/dashboard])
    :style {:maring-left :auto}}
   "End survey"])

(defn root []
  [:div {:style {:background-color "#B5FEC8"
                 :margin -20}}
   [:> mui-grid {:container true :spacing 4 :style {:padding 10}
                 :justify :space-around}
    [:> mui-grid {:item true :xs 12 :md 8}
     [card-header
      {:title (reagent/as-element
               [:div {:style {:color :black}}
                [:div "Tell " [:b "Neo "] "about your last call"]
                [:div {:style {:width "100%" :display :grid :place-content :center
                               :padding-top "1em"}}
                 [:> motion/img
                  {:animate {:rotate 15}
                   :initial {:rotate -15}
                   :transition {:duration 1.5 :repeat 100 :repeat-type :mirror :type :tween}
                   :src "img/3600_4_01.webp"
                   :style {:width 250
                           :border-radius "100%"}}]]])}]]
    [:> mui-grid {:item true :xs 12 :md 8}
     [card {:header {:title "How was the mood of the call overall?"
                     :subheader "Did you spend a good time?"}
            :content {:children [rating "overall" ["Bad" "Ok" "Alright" "Pretty Good" "Amazing"]]}
            :actions {:children [end-survey-button]}}]]
    [:> mui-grid {:item true :xs 12 :md 8}
     [card
      {:header {:title "Are you happy with your contribution?"
                :subheader "Do you feel people listened and discussed about your ideas?"}
       :content {:children [rating "contribution" ["No" "I was quiet." "Hmm.." "Yes" "I was too chatty."]]}
       :actions {:children [end-survey-button]}}]]
    [:> mui-grid {:item true :xs 12 :md 8}
     [card
      {:header
       {:title "How many fallacies did you remarked?"
        :subheader "Can you enumerate rhetological fallacies from the person with whom you speak?"}
       :content {:children
                 [rating "fallacies"
                  ["No" "There were few, but minor." "There were some, but the point was still valid."
                   "Yes, many but some arguments were good. "
                   "The whole argument was based on fallacies."]]}
       :actions {:children [end-survey-button]}}]]
    [:> mui-grid {:item true :xs 12 :md 8}
     [card
      {:header
       {:title "Cognitive Bias"
        :subheader "Did you try to have 360 degree horizon and search actively
        for cognitive bias?"}
       :content {:children
                 [rating "cognitive-bias"
                  ["No"
                   "We mentioned the possibility, but there was no time to study them."
                   "Yes, we discussed about some, but ignored them."
                   "Yes, we enumerated them, and discussed about them "
                   "Yes, we look thoroughly and to act accordingly."]]}
       :actions {:children [end-survey-button]}}]]

    [:> mui-grid {:item true :xs 12 :md 8}
     [card
      {:header {:title "Do you think the meeting was productive?"
                :subheader "Did we reach the goal set for the meeting?"}
       :content {:children [rating "productive" ["No" "No" "Meh..." "Yes" "Yes"]]}
       :actions {:children [end-survey-button]}}]]]])
