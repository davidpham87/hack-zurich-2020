(ns app.zetetic.core
  (:require
   [app.components.mui-utils :refer (markdown)]
   [app.zetetic.assessment]
   [app.zetetic.common :refer (section title ->youtube-video)]
   [app.zetetic.tools]
   [cuerdas.core :as cuerdas]
   [re-frame.core :as rf]
   [transparency.components.layout :as tcl]
   [transparency.components.screen-size :as tcs]
   [transparency.components.tabs :as tct]))


(defn tabs-deep-dive []
  [tct/tabs-global
   {:id :deep-dive
    :choices [{:label "Medecine" :value :medecine}
              {:label "Cognitive Bias" :value :biases}
              {:label "Fallacies" :value :fallacies}]}])

(defn why? []
  (let [screen-size @(rf/subscribe [::tcs/screen-size])]
    [:<>
     [tcl/parallax
      {:image "img/marek-piwnicki-fRSkQPgUSHM-unsplash.webp"
       :style {:margin (if-not (#{:xs} screen-size) -20 0)
               :padding-top 10
               :padding 40}}
      [section
       {}
       [title "Differentiating Truth From Lies Is A Life Saving Skill"]
       [:div {:style {:width "100%"}}
        [markdown
         "

It is beyond a scientific doubt that the group with the highest risk to suffer
heavyly from COVID-19 are the unvaccinated. What is common to

- Anti-Vaxxers,
- QAnon Fans,
- Anti 5G,
- Homeopath,
- Psychics believer,
- Cimate Crisis Denialers?

They act and behave based on believes and theories that contradicts commonly
accepted facts and knolwedge base.

<br/> <br/>
But can one ascertain that their believes is wrong?
"]]]]
     [tcl/parallax
      {:image "img/zach-lucero-qAriosuB-lY-unsplash.webp"
       :style {:margin (if-not (#{:xs} screen-size) -20 0)
               :padding 40}}
      [:div {:style {:width "100%"}}
       [section
        {}
        [title "Epistemology: How Do We Know What We Know We Know"]
        [markdown "

Epistemology is

    \"The theory of knowledge, especially
    with regard to its methods, validity,
    and scope, and the distinction between
    justified belief and opinion.\"

It answers the questions:

- Which methods are the most robust and able to discover the truth?
- Which methods have been failing consistently over history?
- To which failures is one exposed when using certain methods?
- How to weigh contradicting statements?
"]]]]
     [tcl/parallax {:image "img/emily-morter-8xAA0f9yQnE-unsplash.webp"
                    :style {:margin (if-not (#{:xs} screen-size) -20 0)
                            :padding 40}}
      [section
       {}
       [title "Cognitives Biases: Evolutionary Efficient Unfit for the Future"]
       [markdown "
Most will live without even noticing their brain will deceive them with
cognitive biases. These blind spots are the most dangerous as they allowed the
human species to survive and evolve in a world where most events had a single
cause. But most of our challenges nowadays are polycausal.
"

        ]]]
     [tcl/parallax {:image "img/dan-dimmock-3mt71MKGjQ0-unsplash.webp"
                    :style {:margin (when-not (#{:xs} screen-size) -20)
                            :padding 40}}
      [section
       {}
       [title "Rhetological Fallacies"]
       [markdown "

Fallacious argumentation are commonly accepted and the skill to detect them is not taught at school anymore.

"]]]
     [tcl/parallax {:image "img/janko-ferlic-sfL_QOnmy00-unsplash.webp"
                    :style {:margin (when-not (#{:xs} screen-size) -20)
                            :padding 40}}
      [section
       {}
       [title "Dive Deeper"]
       [:<>
        [markdown "Here is a selection of short videos that might help you make
      your own opinion the aforementioned topics "]
        [tabs-deep-dive]
        (case @(rf/subscribe [::tct/tab-global :deep-dive])
          :biases
          [:<>
           [->youtube-video "https://www.youtube.com/embed/videoseries?list=PLceYkF8JBqYTREZFAndpNCXbEt8e4CYXG"]
           [->youtube-video "https://www.youtube.com/embed/videoseries?list=PLceYkF8JBqYRSX5delfymBsy3BddpyW1L"]
           [->youtube-video "https://www.youtube.com/embed/videoseries?list=PLceYkF8JBqYQ-G2uJrz7J9p6laBJPFCSS"]]
          :fallacies
          [:<>
           [->youtube-video "https://www.youtube.com/embed/WhMGcp9xIhY"]
           [->youtube-video "https://www.youtube.com/embed/0b_eHBZLM6U"]
           [->youtube-video "https://www.youtube.com/embed/xecEV4dSAXE"]]
          [:<>
           [->youtube-video "https://embed.ted.com/talks/ben_goldacre_battling_bad_science"]
           [->youtube-video "https://www.youtube.com/embed/7VG_s2PCH_c"]
           [->youtube-video "https://www.youtube.com/embed/gPHgRp70H8o"]])]]]]))

(defn fallacies-root []
  [:a
   {:href "https://www.informationisbeautiful.net/visualizations/rhetological-fallacies/"}
   "www.informationisbeautiful.net"])

(defn cognitive-bias-root []
  [:a
   {:href "https://www.informationisbeautiful.net/visualizations/rhetological-fallacies/"}
   "www.informationisbeautiful.net"])

(defn guardrails-root []
  [section
   {}
   [title "Which Tools to Use to Preventing from Believing in Falsehoods"]
   [markdown "

# Scientific Studies

- Mention different qualities, biases, type of studies (observational, control case, double blinded case studies)

# Quality of Proofs

- Anecdotical
- etc, until scientific communities acceptance.
"
    ]])

(defn assessment-root []
  [app.zetetic.assessment/assessment-root])

(defn tools-root []
  [app.zetetic.tools/tools-root])
