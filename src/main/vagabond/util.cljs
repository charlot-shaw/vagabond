(ns vagabond.util
  (:reqire
   [clojure.string :as strng]))

(defn js-event->int [event]
  (let [string-val (clojure.string/trim  (.. event -target -value))]
    (if (= "" string-val)
      0
      (js/parseInt string-val))))
