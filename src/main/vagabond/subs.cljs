(ns vagabond.subs
  (:require 
   [refx.alpha :refer [reg-sub sub]]
   [vagabond.spec :as-alias v-spec]))

(reg-sub 
 :character 
 (fn [db _]
   (get-in db [::v-spec/characters 1])))

(reg-sub :stats
         (fn [_query-v _]
           [(sub [:character])])
         (fn [[character] _]
           (::v-spec/stats character)))


;; Not strictly useful, but a good example of a query vector in use.
(reg-sub :stat 
         (fn [_query-v _]
           [(sub [:stats])])
         (fn [[stats] [_sub-name wanted-stat]]
           (stats wanted-stat))) 