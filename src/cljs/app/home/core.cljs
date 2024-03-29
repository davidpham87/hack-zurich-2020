(ns app.home.core
  (:require
   ["@material-ui/core/Button" :default mui-button]
   ["@material-ui/core/Divider" :default mui-divider]
   ["@material-ui/core/Grid" :default mui-grid]
   ["@material-ui/core/Typography" :default mui-typography]
   ["@material-ui/icons/Dashboard" :default ic-dashboard]
   ["@material-ui/icons/Help" :default ic-help]
   ["@material-ui/icons/Star" :default ic-star]
   ["@material-ui/icons/NotListedLocation" :default ic-not-listed-location]
   ["@material-ui/icons/Person" :default ic-person]
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [transparency.components.colors :as colors]
   [transparency.components.motion :as motion]
   [transparency.components.reitit :as tcr]
   [transparency.components.screen-size :as tcs]))

(defn center [x]
  [:div {:style {:display :flex :justify-content :center}}
   x])

(defn questions []
  [:<>
   [:> motion/div {:initial {:x -50 :y 50}
                   :animate {:x 50 :y 50 :rotate 360}
                   :transition {:repeat 100 :duration 2 :repeat-type :mirror}
                   :style {:position :absolute
                           :color (colors/colors-rgb :citrin)}}
    [:> ic-help]]
   [:> motion/div {:initial {:x 50 :y 30}
                   :animate {:x -50 :y 30 :rotate 360}
                   :transition {:repeat 100 :duration 2 :repeat-type :reverse}
                   :style {:position :absolute
                           :color (colors/colors-rgb :citrin)}}
    [:> ic-help]]

   [:> motion/div {:animate {:x 50 :y 75 :rotate 360}
                   :initial {:x -50 :y 100}
                   :transition {:repeat 100 :duration 2 :repeat-type :reverse}
                   :style {:position :absolute :color (colors/colors-rgb :citrin)}}
    [:> ic-star]]
   ]
  )

(defn root []
  (let [screen-size @(rf/subscribe [::tcs/screen-size])]
    [:div {:style {:margin           (when-not (#{:xs} screen-size) -20)
                   :padding-bottom   100
                   :min-height       "120vh"
                   :background-color "rgb(132, 177, 255)"
                   :overflow         :hidden}}
     [:div {:style {:background-color (colors/colors-rgb :graphite)
                    :padding-left     20
                    :width            "90%"
                    :padding-top      "2em"
                    :padding-bottom   "2em"
                    :margin-top       "3em"}}
      [:> mui-typography {:variant :h2 :style {:text-align  :left
                                               :font-weight 700
                                               :color       :white}}
       "The Art of Skepticism"]
      [:> mui-typography {:variant :h4 :style {:text-align  :left
                                               :font-weight 700
                                               :color       :white
                                               :margin-top  "1em"}}
       "Scientific Reasoning as Skill For Better Health, Resiliency and Relationships."]]
     [:> mui-grid
      {:container   true
       :style       {:background-color "rgb(132, 177, 255)"
                     :padding          20
                     :overflow         :hidden}
       :spacing     2
       :align-items :stretch :justify :space-around}
      [:> mui-grid {:item true :xs 12}]
      [:> mui-grid {:item true :xs 10 :sm 8 :lg 4}
       [center
        [:<>
         [:img
          {:src "img/monkey.webp"
           :style {:width         "100%"
                   :display       :flex
                   ;; :aspect-ratio  "1 / 1"
                   :border-radius "100%"}}]
         [questions]]]]
      #_[:> mui-grid {:item true :xs 12 :sm 4 :lg 8}]
      [:> mui-grid {:container true :item true :xs 12}
       [:> mui-grid {:item true :xs 4}
        [center
         [:> mui-button
          {:color      :primary
           :start-icon (reagent/as-element [:> ic-not-listed-location])
           :on-click   #(rf/dispatch [::tcr/navigate :app.views/why])}
          "Why?"]]]

       [:> mui-grid {:item true :xs 4}
        [center
         [:> mui-button
          {:color      :primary
           :start-icon (reagent/as-element [:> ic-dashboard])
           :on-click   #(rf/dispatch [::tcr/navigate :app.views/dashboard])}
          "Enter"]]]

       [:> mui-grid {:item true :xs 4}
        [center
         [:> mui-button {:color      :primary
                         :on-click   #(rf/dispatch [::tcr/navigate :app.views/account])
                         :start-icon (reagent/as-element [:> ic-person])} "Login"]]]]]]))
