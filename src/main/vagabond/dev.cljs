(ns vagabond.dev
  (:require [helix.experimental.refresh :as r]))

;; Adds the refresh hook into the page
(r/inject-hook!)

(defn ^:dev/after-load refresh []
  (r/refresh!))
