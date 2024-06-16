(ns vagabond.frontend
  (:require
   [vagabond.components :as vcomp]
   ["react-dom/client" :as react-dom]
   [helix.core :refer [$ defnc]]
   [helix.dom :as d]
   [refx.alpha :as refx]
   [vagabond.events :as v-event]
   [vagabond.subs :as v-subs]))

(refx/dispatch-sync [::v-event/init-db])

(defonce root (react-dom/createRoot (js/document.getElementById "root")))

(defn render []
  (.render root ($ vcomp/sheet-layout)))

(defn ^:dev/after-load clear-cache-and-render!
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code. We force a UI update by clearing
  ;; the Reframe subscription cache.
  (refx/clear-subscription-cache!)
  (render))


(defn ^:export init []
  (render))