(ns app.components.drawer
  (:require
   ["@material-ui/core/Collapse" :default mui-collapse]
   ["@material-ui/core/Divider" :default mui-divider]
   ["@material-ui/icons/AccountTree" :default ic-account-tree]
   ["@material-ui/icons/BatteryChargingFull" :default ic-battery-charging-full]
   ["@material-ui/icons/Dashboard" :default ic-dashboard]
   ["@material-ui/icons/Eco" :default ic-eco]
   ["@material-ui/icons/TrackChanges" :default ic-track-changes]
   ["@material-ui/icons/Assessment" :default ic-asssessment]
   ["@material-ui/icons/Forum" :default ic-forum]
   ["@material-ui/icons/NotListedLocation" :default ic-not-listed-location]
   ["@material-ui/icons/Copyright" :default ic-copyright]
   ["@material-ui/icons/Extension" :default ic-extension]
   ["@material-ui/icons/Group" :default ic-group]
   ["@material-ui/icons/Home" :default ic-home]
   ["@material-ui/icons/LiveHelp" :default ic-live-help]
   ["@material-ui/icons/QuestionAnswer" :default ic-question-answer]
   ["@material-ui/icons/Settings" :default ic-settings]
   ["@material-ui/icons/Star" :default ic-star]
   [re-frame.core :as rf :refer (subscribe dispatch)]
   [transparency.components.colors :as colors]
   [transparency.components.drawer :as tcd :refer
    (mui-list-item-std mui-list-item-root mui-list-item-nested)]
   [transparency.components.reitit :as tcr]))

(rf/reg-event-fx
 ::navigate
 (fn [{db :db} [_ id]]
   {:db db
    :fx [[:dispatch [::tcr/navigate id]]
         [:dispatch [::tcd/close]]]}))

(defn nested-items [_ _ _]
  (let [displayed-sublist (subscribe [::tcd/displayed-sublists])]
    (fn [root-icon+label nested-key items]
      [:<>
       [mui-list-item-root root-icon+label
        {:dispatch-event [::tcd/toggle-drawer-sublist nested-key]
         :open? (contains? @displayed-sublist nested-key)}]
       [:> mui-collapse {:in (contains? @displayed-sublist nested-key)}
        (for [[icon+label nav-key] items]
          ^{:key nav-key}
          [mui-list-item-nested icon+label
           {:dispatch-event [::tcr/navigate nav-key]}])]])))

(def std-item #(vector mui-list-item-std %1
                       {:dispatch-event [::navigate %2]}))

(def divider [:> mui-divider
              {:style {:height 1
                       :background-color (colors/colors-rgb :aquamarine)}}])

(defn tabs-public [ns]
  (fn [_]
    [:<>
     [std-item [ic-home "Home"] (keyword ns :home)]
     [nested-items [ic-live-help "Zetetic"] (keyword ns :home)
      [[[ic-not-listed-location "Why"] (keyword ns :dashboard)]
       [[ic-extension "Cognitive Biases"] (keyword ns :cognitive-bias)]
       [[ic-question-answer "Fallacies"] (keyword ns :fallacies)]
       [[ic-track-changes "Interaction Quality"] (keyword ns :interaction-quality)]
       [[ic-battery-charging-full "Guardrails"] (keyword ns :guardrails)]]]
     [std-item [ic-asssessment "Assess Yourself"] (keyword ns :assesement)]
     [:<> divider]
     [std-item [ic-dashboard "Dashboard"] (keyword ns :dashboard)]
     [std-item [ic-account-tree "Analytics"] (keyword ns :analytics)]
     [std-item [ic-eco "Survey"] (keyword ns :survey)]
     [std-item [ic-star "Achievements"] (keyword ns :achievements)]
     [std-item [ic-group "Team Members"] (keyword ns :team-members)]
     [std-item [ic-copyright "Attribution"] (keyword ns :attribution)]]))

(defn drawer [s]
  [:> tcd/drawer {:tabs-public (tabs-public s) :variant :temporary}])
