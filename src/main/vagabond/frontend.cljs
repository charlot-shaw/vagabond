(ns vagabond.frontend
  (:require ["react" :as react]
            ["react-dom" :as react-dom]
            [helix.core :refer [$ defnc]]
            [helix.dom :as d]
            [refx.alpha :as refx]))

(defnc test-view []
  (d/div "Hello from HELIX"))


(defn render []
  (react-dom/render ($ react/StrictMode ($ test-view))
                    (.getElementById js/document "app")))

(defn ^:dev/after-load clear-cache-and-render!
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code. We force a UI update by clearing
  ;; the Reframe subscription cache.
  (refx/clear-subscription-cache!)
  (render))


(defn ^:export init []
  (render))