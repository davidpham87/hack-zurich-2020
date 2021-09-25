(ns app.components.mui-utils
  (:require
   ["@material-ui/core/Card" :default mui-card]
   ["@material-ui/core/CardActions" :default mui-card-actions]
   ["@material-ui/core/CardContent" :default mui-card-content]
   ["@material-ui/core/CardHeader" :default mui-card-header]
   ["@material-ui/core/Grid" :default mui-grid]
   ["@material-ui/core/Link" :default mui-link]
   ["@material-ui/core/Typography" :default mui-typography]
   ["@material-ui/core/styles" :refer (createTheme)]
   ["markdown-to-jsx" :default react-markdown]
   [reagent.core :as reagent]
   [transparency.components.mui-utils :refer (with-styles)]
   [transparency.components.colors :as colors]))

(defn card-header [m]
  [:> mui-card-header (dissoc m :children) (:children m)])

(defn card-content [m]
  [:> mui-card-content (dissoc m :children) (:children m)])

(defn card-actions [m]
  [:> mui-card-actions (dissoc m :children) (:children m)])

(defn card
  ([m] [card {} m])
  ([props {:keys [header content actions] :as m}]
   [:> mui-card (or props {})
    [card-header header]
    [card-content content]
    [card-actions actions]]))

(def custom-theme
  (let [headline-fonts #js {:fontFamily #js ["Roboto" "Helvetica"]}]
    (createTheme
     #js {:palette #js
          {:primary #js {:main (colors/colors-rgb :graphite)}
           :secondary #js {:main (colors/colors-rgb :citrin)}
           :type "light"}
          :typography #js
          {:fontFamily #js ["Helvetica"]
           :p #js {:fontFamily #js ["Helvetica"]}
           :h1 headline-fonts
           :h2 headline-fonts
           :h3 headline-fonts
           :h4 headline-fonts
           :h5 headline-fonts
           :h6 headline-fonts}})))

(defn left-right [left right]
  [:> mui-grid {:container true :justify :space-between :align-items :center}
   [:> mui-grid {:item true} left]
   [:> mui-grid {:item true} right]])

(defn li-style [theme]
  #js {:root #js {:marginTop (.spacing theme 1)}})

(defn li-comp [{:keys [classes] :as props}]
  [:li {:style {:marginTop 8}}
   [:> mui-typography (merge {:component "span" :variant "body1"} props)]])

(defn typography [_ variant paragraph]
  (fn [props]
    (let [props (or (.-children props) props)]
      (reagent/as-element
       [:> mui-typography {:gutterBottom true
                           :variant variant :component variant
                           :paragraph paragraph} props]))))

(def options
  {:overrides
   {:h1 {:component (typography true "h4" false)}
    :h2 {:component (typography true "h6" false)}
    :h3 {:component (typography true "subtitle1" false)}
    :h4 {:component (typography true "caption" true)}
    :p {:component (typography false "body1" true)}
    :a {:component mui-link}
    :li {:component
         (fn [props]
           (reagent/as-element [:> (with-styles [li-style] li-comp)
                                (.-children props)]))}}})

(defn markdown [& props]
  (let [markdown-options (if (map? (first props))
                           (assoc (first props) :options options)
                           {:options options})
        markdown-props (if (map? (first props)) (last props) (first props))]
    [:> react-markdown markdown-options (or markdown-props "")]))
