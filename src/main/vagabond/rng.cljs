(ns vagabond.rng
  (:require
   [cljx-sampling.random :as ra]))

;; so that this can be changed if needed
(def next-int! ra/next-int!)

(def make-rng ra/create)

(defn rand-from "Gets a random value from a collection" [rng coll]
  (get coll (ra/next-int! rng (count coll))))
