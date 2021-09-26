(ns app.zetetic.assessment
  (:require
   ["@material-ui/core/Button" :default mui-button]
   ["@material-ui/core/Typography" :default mui-typography]
   [app.components.mui-utils :refer (markdown)]
   [app.zetetic.common :refer (section title)]
   [clojure.core.async :as a]
   [clojure.string :as str]
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [transparency.components.motion :as motion]
   [transparency.components.mui-utils :as tcm]
   [transparency.components.tabs :as tct]))

(def tabs-choices
  [{:value :cognitive-bias :label "Cognitive Bias"}
   {:value :fallacies :label "Rhetological Fallacies"}])

(def words-list
  ["pineapple" "strawberry" "plum" "melon" "apricot" "mango" "cherry" "transistor" "watermelon" "gooseberry" "raspberry" "fig" "banana" "orange"])

#_["Effect of primacy" "Effect of recency" "Effect of intruder"]

(defn cognitive-bias []
  (let [c (a/chan)
        words (into [] cat (repeat 2 words-list))
        state (reagent/atom {:started? false :over? false :word nil :dialog? false})]
    (a/go
      (a/<! c)
      (loop [ws words]
        (when (seq ws)
          (swap! state assoc :word (first ws))
          (a/<! (a/timeout 2000))
          (recur (rest ws))))
      (swap! state assoc :word "")
      (swap! state assoc :over? true))
    (fn []
      [:<>
       [tcm/dialog
        {:open? (:dialog? @state)
         :on-close #(swap! state assoc :dialog? false)
         :title "Did you behave like the mass?"
         :content
         [:div {:style {:max-width 460}}
          [markdown
           (str "
If you are similar to the average, you probably wrote down \"pineapple\" and \"strawberry\" as they were the first words of the list (primacy effect). \"Banana\" and \"orange\" also probably on your list as they get the advantage of the recency effect. Finally, \"transistor\" probably belong to the list as well, as it is an intruder.
<br/> <br/>
Now what is interesting is whether you added \"apple\" to the list as it was a collection of fruits and most people believe it was among them.

<br/> <br/> The list of words was
"
                (str "    " (str/join ", " words-list) "."))]]}]
       [tcm/card
        {:header {:title (str "Can you remember a list of "
                              " words?")
                  :subheader "Try to remember as many words as possible. Each words will be separated by 2 seconds. The list will be repeated twice."}
         :content
         {:children ^{:key (:word @state)}
          (if-not (:over? @state)
            [:div {:style {:min-height 200 :display :grid :place-content :center}}
             [:> mui-typography {:variant :h4}
              (str/upper-case (or (:word @state) "Click on Start to begin the game."))]]
            [:div {:style {:min-height 200 :display :grid :place-content :center}}
             [:> mui-typography {:variant :h4}
              "Can you remember the words? Try to enumerate as many as possible
           now." [:> mui-button {:variant :contained
                                 :color :primary
                                 :style {:margin-left "2em"}
                                 :on-click #(swap! state assoc :dialog? true)}
                  "Next"]]]  )}
         :actions
         {:children
          (if-not (:over? @state)
            [:> mui-button {:variant :contained
                            :disabled (:started? @state)
                            :color :primary
                            :on-click #(do (a/offer! c :start)
                                           (swap! state assoc :started? true))}
             "Start"]
            [:> mui-button {:variant :contained
                            :color :primary
                            :on-click #(swap! state assoc :dialog? true)}
             "Next"])}}]])))

(defn tabs []
  [tct/tabs-global
   {:id :assessment :choices tabs-choices}])

(defn assessment-root []
  (let [tab (rf/subscribe [::tct/tab-global :assessment])]
    (fn []
      [:<>
       [tabs]
       ^{:key @tab}
       (case @tab
         :cognitive-bias [cognitive-bias]
         :fallacies [:div "TODO Fallacies"]
         [section
          {}
          [title "Relive some historical experiment"]
          [:<>
           [tabs]
           [markdown "Implement some experiment"]]])])))
