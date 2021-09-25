(ns app.zetetic.assessment
  (:require
   [re-frame.core :as rf]
   [app.components.mui-utils :refer (markdown)]
   [app.zetetic.common :refer (section title)]
   [transparency.components.tabs :as tct]))

(def tabs-choices
  [{:value :cognitive-bias :label "Cognitive Bias"}
   {:value :fallacies :label "Rhetological Fallacies"}])

(defn tabs []
  [tct/tabs-global
   {:id :assessment
    :choices tabs-choices}])

(defn assessment-root []
  (let [tab (rf/subscribe [::tct/tab-global :assessment])]
    (fn []
      [:<>
       [tabs]
       #_(case @tab
         :cognitive-bias [:div "Hello"]
         :fallacies [:div "Hello Fallacies"]
         [section
          {}
          [title "Relive some historical experiment"]
          [markdown "Implement some experiment"]])])))
