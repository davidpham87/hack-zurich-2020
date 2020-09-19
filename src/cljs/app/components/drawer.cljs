(ns app.components.drawer
  (:require
   ["@material-ui/core/Collapse" :default mui-collapse]
   ["@material-ui/core/Divider" :default mui-divider]
   ["@material-ui/icons/AccountTree" :default ic-account-tree]
   ["@material-ui/icons/Extension" :default ic-extension]
   ["@material-ui/icons/Home" :default ic-home]
   ["@material-ui/icons/Settings" :default ic-settings]
   ["@material-ui/icons/Eco" :default ic-eco]
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
                       :background-color (colors/colors-rgb :sand-dark)}}])

(defn tabs-public [ns]
  (fn [_]
    [:<>
     [std-item [ic-home "Home"] (keyword ns :home)]
     [std-item [ic-account-tree "Analytics"] (keyword ns :analytics)]
     #_[std-item [ic-eco "ESG"] (keyword ns :esg-modelling)]
     #_[std-item [ic-extension "Lab"] (keyword ns :lab)]
     #_[std-item [ic-settings "Settings"] (keyword ns :settings)]]))

(defn drawer [s]
  [:> tcd/drawer {:tabs-public (tabs-public s) :variant :temporary}])
