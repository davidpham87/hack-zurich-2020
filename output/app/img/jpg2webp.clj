(ns jpg2webp
  (:require
   [clojure.java.shell :as sh]
   [clojure.string :as str]))

(->> #"\n"
     (str/split (:out (sh/sh "ls")))
     (filter #(re-find #".*unsplash.*jpg$" %)))

(doseq [s '("dan-dimmock-3mt71MKGjQ0-unsplash.jpg" "emily-morter-8xAA0f9yQnE-unsplash.jpg" "janko-ferlic-sfL_QOnmy00-unsplash.jpg")]
  (let [out (str/replace s #"\.jpg$" ".webp")]
    (println (sh/sh "cwebp" s "-o" out))))
