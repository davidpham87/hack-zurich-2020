(ns app.home.core
  (:require
   ["@material-ui/core/Button" :default mui-button]
   ["@material-ui/core/Divider" :default mui-divider]
   ["@material-ui/core/Grid" :default mui-grid]
   ["@material-ui/core/Typography" :default mui-typography]
   ["@material-ui/icons/Dashboard" :default ic-dashboard]
   ["@material-ui/icons/NotListedLocation" :default ic-not-listed-location]
   ["@material-ui/icons/Person" :default ic-person]
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [transparency.components.reitit :as tcr]))

(defn center [x]
  [:div {:style {:display :flex :justify-content :center}}
   x])

(defn root []
  [:div {:style {:margin           -20
                 :height           "100vh"
                 :background-color "rgb(132, 177, 255)"
                 :overflow         :hidden}}
   [:> mui-grid
    {:container   true
     :style       {:background-color "rgb(132, 177, 255)"
                   :padding          20
                   :overflow         :hidden}
     :spacing     2
     :align-items :stretch :justify :space-around}
    [:> mui-grid {:item true :xs 12}
     [center
      [:> mui-typography {:variant :h2 :style {:text-align :center
                                               :font-weight 700
                                               :color :white}}
       "The Art of Scientific Reasoning"]]
     [center
      [:> mui-typography {:variant :h4 :style {:text-align :center
                                               :font-weight 700
                                               :color :white
                                               :margin-top "1em"}}
       "Skill For Better Resiliency, Health and Relationships."]]]
    [:> mui-grid {:item true :xs 10 :sm 8 :lg 4}
     [center
      [:img {:src "img/monkey.webp" :style {:width         "100%"
                                            :display       :flex
                                            :border-radius "100%"}}]]]
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
                       :start-icon (reagent/as-element [:> ic-person])} "Login"]]]]]])
