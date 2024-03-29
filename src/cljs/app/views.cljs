(ns app.views
  (:require
   ["@material-ui/core/CssBaseline" :default mui-css-baseline]
   ["@material-ui/core/IconButton" :default mui-icon-button]
   ["@material-ui/core/Typography" :default mui-typography]
   ["@material-ui/icons/AccountCircle" :default ic-account-circle]
   ["@material-ui/icons/Help" :default ic-help]
   ["@material-ui/icons/Menu" :default ic-menu]
   ["@material-ui/styles/ThemeProvider" :default mui-theme-provider]
   ["react" :as react]
   [app.achievements.core]
   [app.components.colors :as colors]
   [app.components.drawer :refer (drawer)]
   [app.components.mui-utils :refer (custom-theme markdown)]
   [app.home.core]
   [app.login.core]
   [app.survey.core]
   [app.team.core]
   [app.zetetic.core]
   [cuerdas.core]
   [re-frame.core :as rf :refer (subscribe dispatch)]
   [reagent.core :as reagent]
   [transparency.components.app-bar]
   [transparency.components.code-splitting :refer (lazy-component)]
   [transparency.components.drawer]
   [transparency.components.errors :refer (error-boundary)]
   [transparency.components.feedback :as tcf :refer (snackbar)]
   [transparency.components.mui-utils :as tcm]
   [transparency.components.reitit :as tcr]
   [transparency.components.screen-size :as tcs]
   [transparency.components.scroll]))

(def dashboard-view
  (reagent/adapt-react-class
   (lazy-component app.dashboard.core/root)))

(def analytics-view
  (reagent/adapt-react-class
   (lazy-component app.analytics.core/root)))

(defn home-view []
  [:div {:style {:color (colors/colors-rgb :green)}} "Neo The Doubtful"])

(defn viz-view []
  [:div {:style {:color (colors/colors-rgb :green)}} "Analytics"])

(defn attribution-view []
  [tcm/card
   {:header {:title "Bibliography"}
    :content
    {:children
     [:<>
      [markdown
       "The following source should be attributed for the content of this website.

- Ben Goldacre - [Page](https://www.phc.ox.ac.uk/team/ben-goldacre)
- John Oliver - [Last Week Tonight](https://www.youtube.com/user/LastWeekTonight)
- Thomas C. Durand - [La Tronche En Bias](https://www.youtube.com/user/troncheenbiais)
- Nathan Uyttendaele - [Chat Sceptique](https://www.youtube.com/channel/UCOuIgj0CYCXCvjWywjDbauw)
- Buster Benson [Better Humans](https://betterhumans.pub/cognitive-bias-cheat-sheet-55a472476b18)
- Pictures from [catalyststuff](https://www.freepik.com/vectors/logo)
"]]}}])

;; https://www.youtube.com/watch?v=Ns1zx10kTKU Comment débloquer un débat (et en devenir le héros !)
;; https://www.youtube.com/watch?v=ohU1tEwxOSE&t=31s Ne pas traiter l'autre de petite merde

(defn home-route []
  [""
   ["/" {:name ::home
         :view app.home.core/root
         :link-text "Neo The Doubtful"}]
   ["/dashboard"
    {:name ::dashboard
     :view dashboard-view
     :link-text "Neo The Doubtful"}]
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
   ["/attribution"
    {:name ::attribution
     :view attribution-view
     :link-text "Attribution"}]
   ["/zetetic/why"
    {:name ::why
     :view app.zetetic.core/why?
     :link-text "Why?"}]
   ["/zetetic/fallacies"
    {:name ::fallacies
     :view app.zetetic.core/fallacies-root
     :link-text "Rhetological Fallacies"}]
   ["/zetetic/cognitive-bias"
    {:name ::cognitive-bias
     :view app.zetetic.core/cognitive-bias-root
     :link-text "Cognitive Bias"}]
   ["/zetetic/graham-target"
    {:name ::graham-target
     :view app.home.core/root
     :link-text "Interaction Quality - Graham's Target"}]
   ["/zetetic/guardrails"
    {:name ::guardrails
     :view app.zetetic.core/guardrails-root
     :link-text "How to Doubt Efficiently"}]
   ["/zetetic/assessment"
    {:name ::assessment
     :view app.zetetic.core/assessment-root
     :link-text "Assessment"}]
   ["/zetetic/tools"
    {:name ::tools
     :view app.zetetic.core/tools-root
     :link-text "Tools to Reason"}]])

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
                    [:> icon]])
        scroll-y (subscribe [:transparency.components.scroll/y])
        direction (subscribe [:transparency.components.scroll/direction])]
    (fn []
      [:div.appbar
       {:style {:opacity (if (or (nil? @direction)
                                 (= @direction :up)
                                 (< @scroll-y 65)) "100%" "0%")
                :transition "opacity 0.3s ease-in-out"}}
       [:> transparency.components.app-bar/app-bar
        {:position :fixed
         :breakpoint :sm
         :buttons
         (reagent/as-element
          [:<>
           #_[nav-button ::settings ic-settings]
           [nav-button ::account ic-account-circle]
           [nav-link "https://google.ch" ic-help]])
         :menu
         (reagent/as-element
          [:<>
           [transparency.components.drawer/menu-button]])}]])))

(defn footer []
  [:div {:style {:height 200}}])

(defonce main-ref (reagent/atom nil))

(defn app []
  (let [current-route (subscribe [:current-route])
        screen-size   (subscribe [::tcs/screen-size])]
    (fn []
      (let [current-route-name (get-in @current-route [:data :name])]
        [:div {:style {:display "flex"}}
         [:> mui-theme-provider {:theme custom-theme}
          [:> mui-css-baseline]
          [:main {:style {:flex-grow           1
                          :padding             (case @screen-size :xs 0 20)
                          :overflow            :hidden
                          :min-height          "100vh"
                          :background-color    (colors/colors-rgb :graphite)
                          :z-index             0
                          :width               "100%"}}
           [app-bar]
           [drawer :app.views]
           [snackbar :default]
           [:div {:style {:display :grid
                          :height "100%"
                          :gri-template-rows "1fr"}}
            (when (-> @current-route :data :name)
              [:> react/Suspense
               {:fallback (reagent/as-element
                           [:div {:style {:height "100vh" :color :white}} "Loading"])}
               [:div {:style {:margin-bottom 0 :margin-top 60}}
                [(error-boundary [[:app.events/init]])
                 [(get-in @current-route [:data :view] home-view) current-route-name
                  (-> @current-route :data :link-text)]]]])]]]]))))
