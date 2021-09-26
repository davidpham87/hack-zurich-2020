(ns app.zetetic.fallacies
  (:require
   ["@material-ui/core/Button" :default mui-button]
   ["@material-ui/core/Link" :default mui-link]
   ["@material-ui/core/Typography" :default mui-typography]
   [app.components.mui-utils :refer (markdown)]
   [app.zetetic.common :refer (->youtube-video)]
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [transparency.components.mui-utils :as tcm]
   [transparency.reporting.user-input :as tru]))

(def tabs-choices
  [{:label "Overview" :value :overview}
   {:label "Appeal To The Mind", :value :appeal-to-the-mind}
   {:label "Appeal To Emotions", :value :appeal-to-emotions}
   {:label "Faulty Deduction", :value :faulty-deduction}
   {:label "Manipulating Content", :value :manipulating-content}
   {:label "Garbled Cause Effect", :value :garbled-cause-effect}
   {:label "On The Attack", :value :on-the-attack}])

(def tabs-value->label (zipmap (map :value tabs-choices)
                               (map :label tabs-choices)))

(defn tabs []
  [tru/render-user-input-config
   (tru/picker {:id :fallacies :choices tabs-choices
                :label "Fallacy family"})])

(defn fallacies-root []
  (let [tab (rf/subscribe [:user-input-field :fallacies])]
    (fn []
      [tcm/card
       {:square true
        :style {:margin "1em"}}
       {:header {:title (reagent/as-element [tabs])}
        :content
        {:children
         ^{:key @tab}
         (case @tab
           :appeal-to-the-mind
           [markdown {}
            "
It covers mostly fallacies that try to convince by appealing to a third party,
instead of discussing the argument itself. The appeal to tradition
and to authority are the most famous ones.
<br/> <br/>

The appeal to tradition claims something is true because it's (apparently)
always been that way. For example, homosexual marriages, or giving
birth by two person of the same sex.

<br/> <br/>

The appeal to authority is an insidious one as following expert's opinions is
usually a safe strategy. However, this does not prove that their idea is
correct. Example are claims about climate changes, 5G or vaccines.

    Over 400 prominent scientists and engineers
    dispute global warming.
"]
           :appeal-to-emotions
           [markdown "This covers two of the most important fallacies that you
           might use even without knowing: appeal to nature and appeal to
           ridicule.

<br/> <br/>

The appeal to nature make a claims seem more true by drawing a comparison with
the \"good\" natural world, .e.g

    Of course eating bio is without a doubt
    much healthier better than the alternative


The appeal to ridicule is extremely hard to avoid. In its essence, it present
the opponnent's argument in its weakest form, typically by applying it into a
context which was not meant to, e.g.

    Opponents of astrology claim that the stars have
    no influence on us. Go ask
    the sailors if the Moon does not influence
    the tides!

"]
           :faulty-deduction
           [markdown
            "

This section is concerned mostly argument that a pure logical mistakes. The
most famous ones are the middle ground fallacy and the anecdotal evidence that
dismisses evidence done by systematic research in favor of few sample
observations.

    COVID-19 is not dangerous for the babies
    as my nephew got it and is now very healthy.

    Racism does not exists since I have
    never experienced it.

<br/>

The middle ground fallacy assume the answer between two opposing argument that
have merit should be in the middle.

    Between not vaccinating your kids and
    vaccinating them on time,
    one could just plan a delayed
    vaccination plan.

<br/>"]
           :manipulating-content
           [markdown
            "
- Ad hoc rescue: adding new crazier hypothesis to a claim so that it holds.
- Biased generalizing: making a general rule based on a biasd training set.
- Confirmation bias: cherry-picking evidence to support a claim.
- Lie: An outright untruth repeated knowingly as a fact, e.g. \"Election were stolen!\"
"]
           :garbled-cause-effect
           [markdown
            "
Basically, this one can be summarized as \"Correlation is not Causation\".
"]
           :on-the-attack
           [markdown
            "
- Ad Hominem: Bypassing the argument by launching an irrelevant attack on the person and not their claim.
- Burden of Proof: I don't need to prove my claim - you must prove it is false.

That being said, the ad hominem argument is not always a sophism, if one has
been qualified and verified as unreliable because of sustained mistake, it is a
valid argument for doubting their statements.
"]
           ;; default
           [:<>
            [:> mui-typography {:variant :body1}
             "This section inspires a lot from the wonderful dashboard from "
             [:> mui-link {:src "https://www.informationisbeautiful.net/visualizations/rhetological-fallacies/"}
              "www.informationisbeautiful.net"]
             "."]
            [:br]
            [markdown {}
             "

Fallacies are dangerous, because they appear as valid argument, but under
better scrutiny, fallacious argument does not help to decide whether or a not a
statement is valid.

<br/> <br/>

This is one of the main reason why the Greek philosopher Plato was so obsessed
with ideas and mathematics: they do not leave room to *sophisms*.

"]
            [->youtube-video "https://www.youtube.com/embed/5lXIK_4tpDs"]])}}])))
