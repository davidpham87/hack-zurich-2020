(ns app.zetetic.core
  (:require
   [app.components.mui-utils :refer (markdown)]
   [app.zetetic.assessment]
   [app.zetetic.cognitive-bias]
   [app.zetetic.common :refer (section title ->youtube-video)]
   [app.zetetic.fallacies]
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
"]]]
     [tcl/parallax {:image "img/dan-dimmock-3mt71MKGjQ0-unsplash.webp"
                    :style {:margin (when-not (#{:xs} screen-size) -20)
                            :padding 40}}
      [section
       {}
       [title "Rhetological Fallacies"]
       [markdown "

Fallacious argumentation are commonly accepted and the skill to detect them is
not taught at school anymore.  The appeal to authority, the appeal to
popularity and the appeal of nature are regularly used by politicians,
advertisers and alternative medicine to convinced their audience of their
products, but the merit of the arguments are never discussed.
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

(defn fallacies-root [] [app.zetetic.fallacies/fallacies-root])

(defn cognitive-bias-root []
  [app.zetetic.cognitive-bias/cognitive-bias-root])

(defn guardrails-root []
  (let [screen-size @(rf/subscribe [::tcs/screen-size])]
    (fn []
      [tcl/parallax {:image "img/janko-ferlic-sfL_QOnmy00-unsplash.webp"
                     :style {:margin (when-not (#{:xs} screen-size) -20)
                             :padding 40}}
       [section
        {}
        [title "How to Fight our Bias"]
        [markdown "

The first step is to recognize that not all proofs have the same weight.

<br/>
<br/>

# Burden of Proof

The first step is to recognize who need to support the burden of proof. Science
will never be able to state that a phenomenon does not exist, it can guarantee
that it has either extremely low probably to occur, or that if it occurs it
will be almost undetectable. Proving the impossible is an impossible standard.

<br/>

Hence, when someone claims the existence of something, the burden of proof is
on the shoulder of the one claiming the existence and not the other way around.

<br/>
<br/>


# Scientific Studies

There exist myriad of studies and not all are equal. There might be several
level at which a study might go south, but their designs usually define their
reliability.

- Double Blinded Case Studies are the most reliable and the strongest type of
studies. The designer of the experiment selects a representative share of the
population and split in two groups randomly. Then the patient and those who
administer the intervation ignore in which group the patient belongs.

- Observational studies: these studies basically report what they observes, and
the issue here is that many factors can be confounded and hence it is more
difficult to extract genearlized information from them. In an observational,
the patients could all have a common factor that is the the studied effect.

- Control-case studies, in this case, we base our study on people who naturally
  had the intervation (usually, it is a heavy condition and it would be
  unethical to administer it to healthy people).

There is a fourth category, that is usually more powerful and more informative:
the meta-analysis. The meta-analysis tries to select all the papers on a
subject and filter the studies from which one can extract meaningful
information and performs an analysis again. These meta-analysis have shown the
most reliable conclusion as adding studding usually reduce the statistical
noise.

<br/>
<br/>

# Quality of Proofs

People usually ignore the source of information, but it has its importance, not
all proofs are equal. We provided a ranking that can help you weigh the balance
when in doubt.

1. Rumor
2. Witness
3. Anecdotical Evidence
4. Common Knowledge
5. Opinion
6. Expertise
7. Peer Reviewed Published Scientific Studies
8. Scientific Consensus

# Little Tricks to Assess Arguments

- Using the Bayesian formula as support, one should always require
  extraordinary evidence for extraordinary claims.

- Proofs by contradiction. Still using Bayesian formula, accept that the
  statement as true and try predict the consequences and check whether the
  consequences still make a coherent world. For example, homeopathy were to
  work, we would basically need to rethink and update the entirety of physics,
  biology and chemistry, meaning it is unlikely that homeopathy works.

"]]])))

(defn assessment-root []
  [app.zetetic.assessment/assessment-root])

(defn tools-root []
  [app.zetetic.tools/tools-root])
