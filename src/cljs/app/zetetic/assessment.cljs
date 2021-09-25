(ns app.zetetic.assessment
  (:require
   [app.components.mui-utils :refer (markdown)]
   [app.zetetic.common :refer (section title)]))

(defn assessment-root []
  [section
   {}
   [title "Relive some historical experiment"]
   [markdown "Implement some experiment"]])
