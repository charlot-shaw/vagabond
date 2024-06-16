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

(reg-sub :inventory
         (fn [_query-v _]
           [(sub [:character])])
         (fn [[character] _]
           (::v-spec/inventory character)))

(reg-sub :inventory-max-slots 
         (fn [_query-v _]
           [(sub [:stat ::v-spec/con])])
         (fn [[con] _]
           (+ 10 con)))

(reg-sub :inventory-taken
         (fn [_query-v _]
           [(sub [:inventory])])
         (fn [[inventory] _]
           (reduce (fn [acc new]
                     (+ acc (get new ::v-spec/slots-taken 0)))
                   0
                   inventory)))

(reg-sub :inventory-free
         (fn [_query-v _]
           [(sub [:inventory-max-slots])
            (sub [:inventory-taken])])
         (fn [[slots taken] _]
           (- slots taken)))