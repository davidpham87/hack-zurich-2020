(ns app.home.core
  (:require
   ["@material-ui/core/Button" :default mui-button]
   ["@material-ui/core/Divider" :default mui-divider]
   ["@material-ui/core/Grid" :default mui-grid]
   ["@material-ui/core/Typography" :default mui-typography]
   ["@material-ui/icons/Dashboard" :default ic-dashboard]
   ["@material-ui/icons/Person" :default ic-person]
   [re-frame.core :as rf]
   [transparency.components.reitit :as tcr]
   [reagent.core :as reagent]))

(defn center [x]
  [:div {:style {:display :flex :justify-content :center}}
   x])

(defn root []
  [:> mui-grid {:container true :style {:padding 20} :spacing 2
                :align-items :stretch :justify :space-around}
   [:> mui-grid {:item true :xs 8}
    [center
     [:img {:src "img/jasper_gif_opt.gif" :style {:width 200 :style :flex}}]]]

   [:> mui-grid {:item true :xs 6}
    [center
     [:> mui-button {:color :secondary :start-icon (reagent/as-element [:> ic-dashboard])
                     :on-click #(rf/dispatch [::tcr/navigate :app.views/dashboard])} "Enter"]]]

   [:> mui-grid {:item true :xs 6}
    [center
     [:> mui-button {:color :secondary
                     :on-click #(rf/dispatch [::tcr/navigate :app.views/account])
                     :start-icon (reagent/as-element [:> ic-person])} "Login"]]]])
