(ns app.views
  (:require
   ["@material-ui/core/CssBaseline" :default mui-css-baseline]
   ["@material-ui/core/IconButton" :default mui-icon-button]
   ["@material-ui/icons/AccountCircle" :default ic-account-circle]
   ["@material-ui/icons/Help" :default ic-help]
   ["@material-ui/icons/Settings" :default ic-settings]
   ["@material-ui/styles/ThemeProvider" :default mui-theme-provider]
   ["react" :as react]
   [app.achievements.core]
   [app.components.colors :as colors]
   [app.components.drawer :refer (drawer)]
   [app.components.mui-utils :refer (custom-theme)]
   [app.home.core]
   [app.login.core]
   [app.survey.core]
   [app.team.core]
   [cuerdas.core]
   [re-frame.core :as rf :refer (subscribe dispatch)]
   [reagent.core :as reagent]
   [transparency.components.app-bar]
   [transparency.components.code-splitting :refer (lazy-component)]
   [transparency.components.drawer]
   [transparency.components.errors :refer (error-boundary)]
   [transparency.components.feedback :as tcf :refer (snackbar)]
   [transparency.components.reitit :as tcr]
   [transparency.components.screen-size :as tcs]))


(def dashboard-view
  (reagent/adapt-react-class
   (lazy-component app.dashboard.core/root)))

(def analytics-view
  (reagent/adapt-react-class
   (lazy-component app.analytics.core/root)))

(defn home-view []
  [:div {:style {:color (colors/colors-rgb :green)}} "Tim the Team Plant"])

(defn viz-view []
  [:div {:style {:color (colors/colors-rgb :green)}} "Analytics"])

(defn home-route []
  [""
   ["/" {:name ::home
         :view app.home.core/root
         :link-text  "Tim the Team Plant"}]
   ["/dashboard"
    {:name ::dashboard
     :view dashboard-view
     :link-text "Tim the Team Plant"}]
   ["/analytics"
    {:name ::analytics
     :view analytics-view
     :link-text "Analytics"}]
   ["/survey"
    {:name ::survey
     :view app.survey.core/root
     :link-text "Survey"}]
   ["/achievements"
    {:name ::achievements
     :view app.achievements.core/root
     :link-text "Achievements"}]
   ["/team-members"
    {:name ::team-members
     :view app.team.core/root
     :link-text "Team Members"}]
   ["/account"
    {:name ::account
     :view app.login.core/root-panel
     :link-text "Account"}]
   #_["/settings"
    {:name ::settings
     :view home-view
     :link-text "Settings"}]])

(def routes
  ["" (home-route)])

(defn app-bar []
  (let [nav-button (fn [path icon]
                     [:> mui-icon-button
                      {:size :small :on-click #(rf/dispatch [::tcr/navigate path])}
                      [:> icon]])
        nav-link (fn [url icon]
                   [:> mui-icon-button
                    {:size :small :on-click #(.open js/window url "_blank")}
                    [:> icon]])]
    [:div {:style {:margin-bottom 64 :z-index 1201}}
     [:> transparency.components.app-bar/app-bar
      {:breakpoint :sm
       :buttons
       (reagent/as-element
        [:<>
         #_[nav-button ::settings ic-settings]
         [nav-button ::account ic-account-circle]
         [nav-link "https://google.ch" ic-help]])
       :menu
       (reagent/as-element
        [:<>
         [transparency.components.drawer/menu-button]])}]]))

(defn footer []
  [:div {:style {:height 200}}])

(defonce main-ref (reagent/atom nil))

(defn app []
  (let [current-route (subscribe [:current-route])
        screen-size   (subscribe [::tcs/screen-size])]
    (fn []
      (let [current-route-name (get-in @current-route [:data :name])]
        [:div
         [:> mui-css-baseline]
         [:> mui-theme-provider {:theme custom-theme}
          [:div {:style {:display "flex" :height "100vh" :width "100%"}}
           [drawer :app.views]
           [:main {:ref   #(reset! main-ref %)
                   :style {:flex-grow           1
                           :padding             (case @screen-size :xs 0 20)
                           :overflow            :auto
                           :min-height          "100vh"
                           :background-position :center
                           :background-size     :cover
                           :background-color    :white
                           ;; :background-color    :black
                           :overflow-x          :hidden
                           :z-index             1201
                           :width               "100%"}}
            [:div {:style (cond-> {:height "90%"}
                            (and (= current-route-name :home)
                                 (not (#{:xs :sm} @screen-size))) (merge {:overflow :hidden}))}
             [app-bar]
             [snackbar :default]
             (when (-> @current-route :data :name)
               [:> react/Suspense
                {:fallback (reagent/as-element
                            [:div {:style {:height "100vh" :color :white}} "Loading"])}
                [:div {:style {:margin-bottom 0 :margin-top 60}}
                 [(error-boundary [[:scorecards-modelling.events/initialize-db]])
                       [(get-in @current-route [:data :view] home-view) current-route-name
                        (-> @current-route :data :link-text)]]]])
             [footer]]]]]]))))
