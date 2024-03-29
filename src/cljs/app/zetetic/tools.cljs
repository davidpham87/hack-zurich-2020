(ns app.zetetic.tools
  (:require
   ["@material-ui/core/Fade" :default mui-fade]
   ["@material-ui/core/Link" :default mui-link]
   ["@material-ui/core/Tooltip" :default mui-tooltip]
   ["@material-ui/core/Typography" :default mui-typography]
   [app.components.mui-utils :refer (markdown)]
   [app.zetetic.common :refer (section ->youtube-video)]
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [transparency.components.colors :as colors]
   [transparency.components.mui-utils :as tcm]
   [transparency.components.scroll]
   [transparency.components.tabs :as tct]))

(defn tooltip-dynamic [{:keys [background-color color]}]
  (tcm/adapt-component
   #js {:tooltip
        #js {:background-color background-color
             :color color
             :font-size 12
             :opacity 1
             :max-width 500
             :padding 10}}
   mui-tooltip))

(def tabs-choices
  [{:value :graham-target :label "Graham's Target"}
   {:value :fallacies :label "Rhetological Fallacies"}
   {:value :medecine :label "Medecine"}])

(def graham-colors
  (mapv colors/colors-rgb [:green-light-dark :green-light :amber-bright
                           :amber-dark
                           :red-light
                           :amethyst-bright
                           :amethyst-dark
                           :nucleus-blue
                           :main-dark]))

(def graham-description
  ["Identify and refute the central thesis"
   "Refute an opponent argument"
   "Argue by argument"
   "Contradict without argumenting"
   "Beyond this limit, the debate is moving backward"
   "Attack the form"
   "Attack the person on one of her attribute"
   "Insult or menace"
   "Assault physically"])

(defn tabs []
  [tct/tabs-global
   {:id :tools :choices tabs-choices}])

(defn graham-target [{:keys [width height]}]
  (let [mouse-position (reagent/atom {:x nil :y nil})]
    (fn [{:keys [width height]}]
      (let [cx (int (/ width 2))
            cy (int (/ height 2))]
        [tcm/card {:square true :elevan 0 :style {:width "100%" :margin 0}}
         {:header {:title "Graham's target"}
          :content
          {:children
           [:div {:style {:display :flex
                          :flex-direction :column
                          :align-items :center}}
            [:div {:style {:align-self :start}}
             [:> mui-typography {:variant :body1} "The Graham's target allows to identify whether a conversation is moving forward or going backward depending on the nature of the argument. Hover or click on the band to see the description."]]
            [:div
             [:svg {:width width :height height :z-index 10}
              [:g
               (for [[i [r c d]]  (reverse (map-indexed vector (map vector (range) graham-colors graham-description)))
                     :let [r (+ 30 (* 15 r))]]
                 ^{:key (str c)}
                 [:<>
                  [(tooltip-dynamic {:background-color :white :color :black})
                   {:style {:z-index 9999}
                    :title d
                    :TransitionComponent mui-fade
                    :enter-next-delay 200
                    :on-mouse-move
                    (fn [e]
                      (let [[x y] [(.-pageX e) (.-pageY e)]]
                        (when (or (> (js/Math.abs (- (:y @mouse-position) y)) 30)
                                  (> (js/Math.abs (- (:x @mouse-position) x)) 30))
                          (reset! mouse-position {:x x :y y}))))
                    :PopperProps
                    {:anchor-el
                     #js {:client-height 0 :client-width 0
                          :getBoundingClientRect
                          (fn []
                            #js {:top (- (:y @mouse-position)
                                         (transparency.components.scroll/get-scroll))
                                 :left (:x @mouse-position)
                                 :right (:x @mouse-position)
                                 :bottom (:y @mouse-position)
                                 :width 0
                                 :height 0})}}}
                   [:circle {:cx cx :cy cy :r r :fill c}]]
                  [:text {:x (- cx 4) :y (+ cy r -3) :fill :white}
                   (let [score (- 3 i)]
                     (when (pos? score) "+")
                     (if (= score -1) "" (if (neg? score) (inc score) score)))]])]]]
            [:div {:style {:display :flex :align-self :start :flex-direction :column
                           :align-items :center
                           :width "100%"}}
             "See this video for more details."
             [:div {:style {:max-width 450}}
              [->youtube-video "https://www.youtube.com/embed/ohU1tEwxOSE"]]]]}}]))))

(defn medecine []
  [tcm/card
   {:header {:title "Scientific studies are good. Meta analysis better."}
    :content
    {:children
     [:> mui-typography
      "For medecine, the best source is "
      [:a {:href "https://www.cochrane.org/"} "Cochrane"]
      " that conduct meta analysis (analysis of multiple papers) in order to make an objective opinion."]}}])

(defn fallacies []
  [:<>
   [tcm/card
    {:square true :elevation 2}
    {:header {:title "Naming is already half of the battle."}
     :content
     {:children
      [:> mui-typography
       "The ability to name and identify the nature of the argument of your
      opponent improves your ability to answer or maybe find his central
      thesis. Sometimes, one can also just give up. This link is probably your
      best ally "
       [:> mui-link {:src "https://www.informationisbeautiful.net/visualizations/rhetological-fallacies/"}
        "Information Is Beautiful "]
       ", until we create NLP algorithm that automatically classify the types of
      arguments."]}}]
   [:iframe {:src "https://www.informationisbeautiful.net/visualizations/rhetological-fallacies/"
             :height 560
             :width "100%"}]])

(defn tools-root []
  (let [tab (rf/subscribe [::tct/tab-global :tools])]
    (fn []
      [:div
       [tabs]
       (case @tab
         :graham-target [graham-target {:width 350 :height 350}]
         :medecine [medecine]
         :fallacies [fallacies]
         [section
          {}
          [markdown "make forms about cible de graham, fallacies, potential bias, games"]])])))
