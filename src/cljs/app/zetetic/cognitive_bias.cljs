(ns app.zetetic.cognitive-bias
  (:require
   ["@material-ui/core/Typography" :default mui-typography]
   [app.components.mui-utils :refer (markdown)]
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [clojure.core.async :as a]
   [transparency.components.feedback :as tcf]
   [transparency.components.mui-utils :as tcm]
   [transparency.reporting.user-input :as tru]))

(def tabs-choices
  [{:label "Overview" :value :overview}
   {:label "Nudges", :value :nudges}])

(defn overview []
  [:<>
   [markdown {}
    " The most common cognitive biases can be separated into these categories

- Information overload,
- Lack of meaning
- The need to act fast
- How to know what needs to be remembered for later.

The process actually goes as follows:

1. Information overload sucks, so we aggressively filter. Noise becomes signal. But we don’t see everything. Some of the information we filter out is actually useful and important.
2. Lack of meaning is confusing, so we fill in the gaps. Signal becomes a
story. However, our search for meaning can conjure illusions. We sometimes
imagine details that were filled in by our assumptions, and construct meaning
and stories that aren’t really there.
3. Need to act fast lest we lose our chance, so we jump to
conclusions. Stories become decisions. Nevertheless, quick decisions can be
seriously flawed. Some of the quick reactions and decisions we jump to are
unfair, self-serving, and counter-productive.
4. This isn’t getting easier, so we try to remember the important
bits. Decisions inform our mental models of the world. Nonetheless our memory
reinforces errors. Some of the stuff we remember for later just makes all of
the above systems more biased, and more damaging to our thought processes.

We invite to read a good summary of these biases on this [blog post](https://betterhumans.pub/cognitive-bias-cheat-sheet-55a472476b18). The blog also explains the map below with most of 180 cognitive biases.

"]
   [:img {:width "100%"
          :src "https://upload.wikimedia.org/wikipedia/commons/c/ce/Cognitive_Bias_Codex_With_Definitions%2C_an_Extension_of_the_work_of_John_Manoogian_by_Brian_Morrissette.jpg"}]])

(defn nudges []
  [:<>
   [markdown
    "

We seemed to be fairly negative about cognitive biases as they might lead to
wrong conclusion and decision, but one can also use our cognitive blind spots
to improve our life without realizing.

<br/> <br/>

## Not all biases are bad

<br/>

For example, just by mentioning the benefits of mindful and deep breathing and
by some notification at some random interval, you will probably take some of
them without even thinking, just because you were exposed to the idea.

The same idea goes for sport and making connection. This is fairly well
explained [here](https://en.wikipedia.org/wiki/Nudge_theory).

The reason why that nudging might work is that it operates on the part of the
brain that take snap decision by passing many of resistance.

"]])

(defn cognitive-bias-root []
  (let [tab (rf/subscribe [:user-input-field :cognitive-bias])
        state (atom {})]

    (a/go-loop []
      (rf/dispatch
       [::tcf/set-feedback
        {:duration 10000
         :message
         (rand-nth
          ["Don't forget to take some deep breath!"
           "Remember to check your diet and to exercise from time to
                    time."
           "Exercise is more effective when done in team!"
           "Sleeping is extremely beneficial to the brain and the memory."
           "#FakeMed is a real societal issue and became the most
                    likely recruitment channel for cults." ])
         :status (rand-nth [:warning :success :error])}])
      (a/<! (a/timeout (* 2 60 1000)))
      (when-not (:end? @state)
        (recur)))

    (reagent/create-class
     {:component-did-mount
      (fn [])
      :component-will-unmount
      (fn [] (swap! state assoc :end? true))
      :reagent-render
      (fn []
        [tcm/card
         {:header
          {:title
           (reagent/as-element
            [tru/render-user-input-config
             (tru/picker {:id :cognitive-bias :choices tabs-choices})])}
          :content
          {:children
           (case @tab
             :nudges [nudges]
             [overview])
           }}])})))
