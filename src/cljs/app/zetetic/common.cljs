(ns app.zetetic.common
  (:require
   ["@material-ui/core/Box" :default mui-box]
   ["@material-ui/core/Typography" :default mui-typography]))

(defn title [s]
  [:div {:style {:width "100%" :margin-bottom "1em"}}
   [:> mui-typography {:variant :h3 :style {:color :white :font-weight 700}}
    s]])

(defn section [{:keys [style]} & children]
  (into [:div {:style (merge
                       {:min-height "80vh" :color :white
                        :margin-top "1em"
                        :grid-template-columns "clamp(250px, 100%, 600px)"
                        :grid-template-rows "1fr auto"
                        :display :grid
                        :align-items :space-between
                        :justify-content :center}
                       style)}]
        children))

(defn ->youtube-video [url]
  [:iframe
   {:allowfullscreen "allowfullscreen",
    :allow
    "accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture",
    :frameborder "0",
    :title "YouTube video player",
    :src url
    :height "315",
    :width "100%"
    :style {:margin-top "1em"}}])
