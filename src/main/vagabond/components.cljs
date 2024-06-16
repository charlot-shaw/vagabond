(ns vagabond.components
  (:require 
   [helix.core :refer [$ defnc <>]]
   [helix.dom :as d]
   [refx.alpha :as refx :refer [use-sub dispatch]]
   [vagabond.spec :as-alias v-spec]))

(defnc name-bar []
  (let [character (use-sub [:character])]
    (d/div {:class "box level"}
           (d/div {:class "level-left"}
                  (d/span {:class :level-item}
                         (::v-spec/name character)))
           (d/div {:class "level-right"}
                  (d/span {:class :level-item}
                          "Level "(::v-spec/level character))))))


(defnc stat-tile [{:keys [stat stat-name]}]
  (let [stat-amount (use-sub [:stat stat])]
    (d/div {:class "px-1 py-1 cell has-text-centered"}
           (d/p
            stat-name)
           (d/p {:class "has-text-primary"}
            stat-amount))))

(defnc stat-block []
  (d/div {:class "block"}
         (d/div {:class "grid has-auto-count"}
                ($ stat-tile {:stat ::v-spec/dex
                              :stat-name "Dexterity"})
                ($ stat-tile {:stat ::v-spec/str
                              :stat-name "Strength"})
                ($ stat-tile {:stat ::v-spec/con
                              :stat-name "Constitution"})
                ($ stat-tile {:stat ::v-spec/int
                              :stat-name "Intelligence"})
                ($ stat-tile {:stat ::v-spec/wis
                              :stat-name "Wisdom"})
                ($ stat-tile {:stat ::v-spec/cha
                              :stat-name "Charisma"}))))

(defnc inventory-block []
  (d/div {:class "box"}
         (d/p "Inventory")))


(defnc sheet-layout []
  (<>
   ($ name-bar)
   ($ stat-block)
   ($ inventory-block)))


(comment 
  (name ::v-spec/str))