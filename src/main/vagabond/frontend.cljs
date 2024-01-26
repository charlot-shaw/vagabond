(ns vagabond.frontend
  (:require
   [helix.core :refer [defnc $]]
   [helix.hooks :as hooks]
   [helix.dom :as d]
   ["react-dom/client" :as rdom]
   [vagabond.character-sheet :as char-sheet]
   [vagabond.generator :as char-gen]))

(defnc greeting "A component to give a greeting"
  [{:keys [name]}]
  (d/div "Hello, " (d/strong name) "!"))

(defnc app [] {:helix/features {:fast-refresh true}}
  (println "hello")
  (d/h1 "hello"))
(defonce root (rdom/createRoot (js/document.getElementById "root")))

(defn init []
  (.render root ($ app)))

(comment)
