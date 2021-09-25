(ns app.zetetic.core
  (:require
   ["@material-ui/core/Box" :default mui-box]
   ["@material-ui/core/Typography" :default mui-typography]
   [app.components.mui-utils :refer (markdown)]
   [cuerdas.core :as cuerdas]
   [transparency.components.layout :as tcl]))

(defn title [s]
  [:div {:style {:width "80%" :margin-bottom "1em"}}
   [:> mui-typography {:variant :h3 :style {:color :white :font-weight 700}}
    s]])

(defn section [{:keys [style]} & children]
  (into [:div {:style (merge
                       {:height "80vh" :color :white
                        :margin-top "1em"
                        :grid-template-columns "clamp(460px, 100%, 600px)"
                        :grid-template-rows "1fr auto"
                        :display :grid
                        :align-items :space-between
                        :justify-content :center}
                       style)}]
        children))

(defn why? []
  [:<>
   [tcl/parallax {:image "img/marek-piwnicki-fRSkQPgUSHM-unsplash.webp"
                  :style {:margin -20
                          :padding 20}}
    [section
     {}
     [title "Differentiating Truth From Lies Is A Life Saving Skill"]
     [:div
      [markdown "

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
   [tcl/parallax {:image "img/zach-lucero-qAriosuB-lY-unsplash.webp"
                  :style {:margin -20
                          :padding 20}}
    [section
     {}
     [title "Epistemology: How Do We Know What We Know We Know"]
     [markdown "

Epistemology is

    \"The theory of knowledge, especially with regard to its methods, validity,
    and scope, and the distinction between justified belief and opinion.\"

It answers the questions:

- Which methods are the most robust and able to discover the truth?
- Which methods have been failing consistently over history?
- To which failures is one exposed when using certain methods?
- How to weigh contradicting statements?
"

      ]]]
   [tcl/parallax {:image "img/emily-morter-8xAA0f9yQnE-unsplash.webp"
                  :style {:margin -20
                          :padding 20}}
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
                  :style {:margin -20
                          :padding 20}}
    [section
     {}
     [title "Rhetological Fallacies"]
     [markdown "

Fallacious argumentation are commonly accepted and the skill to detect them is not taught at school anymore.

"]]]
   [tcl/parallax {:image "img/dan-dimmock-3mt71MKGjQ0-unsplash.webp"
                  :style {:margin -20
                          :padding 20}}
    [section
     {}
     [title "Dive Deeper"]
     (let [->youtube-video
           (fn [url] [:iframe
                      {:allowfullscreen "allowfullscreen",
                       :allow
                       "accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture",
                       :frameborder "0",
                       :title "YouTube video player",
                       :src url
                       :height "315",
                       :width "560"
                       :style {:margin-top "1em"}}])]
       [:<>
        [markdown "Here is a selection of short videos that might help you make
      your own opinion the aforementioned topics "]
        [->youtube-video "https://www.youtube.com/embed/WhMGcp9xIhY"]
        [->youtube-video "https://www.youtube.com/embed/0b_eHBZLM6U"]
        [->youtube-video "https://www.youtube.com/embed/7VG_s2PCH_c"]
        [->youtube-video "https://www.youtube.com/embed/gPHgRp70H8o"]
        [->youtube-video "https://www.youtube.com/embed/xecEV4dSAXE"]])]]])

(defn fallacies-root []
  [:a
   {:href "https://www.informationisbeautiful.net/visualizations/rhetological-fallacies/"}
   "www.informationisbeautiful.net"])

(defn cognitive-bias-root []
  [:a
   {:href "https://www.informationisbeautiful.net/visualizations/rhetological-fallacies/"}
   "www.informationisbeautiful.net"])
