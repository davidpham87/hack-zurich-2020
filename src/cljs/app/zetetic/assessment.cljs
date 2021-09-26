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

(def sentences
  [{:label "Ad Hoc Rescue"
    :description "Trying to save a cherished belief by repeatedly revising the argument to explain away problems."
    :sentence "...But apart from better sanitation, medicine, education, irrigation, public health, roads, a freshwater system and public order... what have the Romans done for us?"}

   {:label "Cum Hoc Ergo Propter Hoc (Correlation is Causation)"
    :description "Claiming two events that occur together must have a cause-and-effect relationship. (Correlation = cause)"
    :sentence "Teenagers in gangs listen to rap music with violent themes. Rap music inspires violence in teenagers."}

   {:label "Post Hoc Ergo Propter Hoc"
    :description "Claiming that because one event followed another, it was also caused by it."
    :sentence "Since the election of the President more people than ever are unemployed. Therefore the President has damaged the economy."}

   {:label "Burden of Proof"
    :description "I don't need to prove my claim - you must prove it is false."
    :sentence "I maintain long-term solar cycles are the cause of global warming. Show me I'm wrong."}

   {:label "Perfectionist Fallacy"
    :description "Assuming that the only option on the table is perfect success, then rejecting anything that will not work perfectly."
    :sentence "What's the point of this anti-drunk driving campaign? People are still going to drink and drive no matter what."}

   {:label "Perfectionist Fallacy"
    :description "Assuming that the only option on the table is perfect success, then rejecting anything that will not work perfectly."
    :sentence "You can still get COVID-19 even with the vaccination, hence it is useless. "}

   {:label "Appeal to Popular Belief"
    :decription "Claiming something is true because the majority of people believe it."
    :sentence "Everyone knows milk is good for your bones."}

   {:label "Appeal to Authority"
    :description "Claiming something is true because an 'expert', whether qualified or not, says it is."
    :sentence "COVID-19 is a just the flu, because Pres. Trump said so. "}
   {:label "Valid"
    :description "This is a valid argument"
    :sentence "The scientific community have repeatedly failed to discover any effect of homeopathy. Hence we can state that it does not work."}
   {:label "Valid"
    :description "This is a valid argument"
    :sentence "Multiple studies have shown that mRNA vaccines are safe, so it probably is."}
   {:label "Appeal To Ignorance"
    :description "A claim is true simply because it has not been proven false (or false because it has not been proven true)."
    :sentence "Not enough time has been passed since the mRNA vaccines, so we ignore if they are safe."}])

(def fallacies-label (vec (sort (into #{} (map :label sentences)))))

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

(defn fallacies []
  (let [c (a/chan)
        words (shuffle sentences)
        state (reagent/atom {:started? false :over? false :word nil :dialog? false})]
    (a/go
      (a/<! c)
      (loop [ws words]
        (when (seq ws)
          (swap! state assoc :word (first ws))
          (a/<! (a/timeout 2000))
          (a/<! c)
          (recur (rest ws))))
      (swap! state assoc :word "")
      (swap! state assoc :over? true))
    (fn []
      [:<>
       (let [m (:word @state)]
         [tcm/dialog
          {:open? (:dialog? @state)
           :on-close #(do (swap! state assoc :dialog? false)
                          (a/offer! c :next))
           :title (:label m)
           :content
           [:div {:style {:max-width 460}}
            [markdown
             (str (:description m)
                  "<br/> <br/>"
                  (:sentence m))]]}])
       [tcm/card
        {:header {:title (str "Can you classify these statements?")
                  :subheader "Try to name the nature of each of the following arguments."}
         :content
         {:children ^{:key (:word @state)}
          [:div {:style {:min-height 200 :display :grid :place-content :center}}
           [:> mui-typography {:variant :h4}
            (if (seq (:word @state))
              (let [m (:word @state)
                    choices (into [(:label m)] (take 3 (shuffle fallacies-label)))]
                [:<>
                 (:sentence m)
                 [markdown {} (apply str "- " (str/join "\n\n- " choices))]
                 [:> mui-button {:on-click #(swap! state assoc :dialog? true)} "Show answer"]])
              (str/upper-case "Click on Start to begin the game."))]]}
         :actions
         {:children
          (if-not (:over? @state)
            [:> mui-button {:variant :contained
                            :color :primary
                            :on-click #(do (a/offer! c :start)
                                           (swap! state assoc :dialog? false)
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
         :fallacies [fallacies]
         [section
          {}
          [title "Relive some historical experiment"]
          [:<>
           [tabs]
           [markdown "Implement some experiment"]]])])))
