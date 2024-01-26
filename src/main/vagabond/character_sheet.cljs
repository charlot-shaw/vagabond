(ns vagabond.character-sheet
  (:require
   [helix.core :refer [defnc $]]
   [helix.hooks :as hooks]
   [helix.dom :as d]))

(defnc character-stats
  "Displays the stats of a given character"
  [{:keys [cha wis int str dex con] :as char-map}]
  (d/pre (str (or char-map "character is falsey")))
  (d/div
   (d/div {:class "grid"}
          (d/div "Str: " str)
          (d/div "Con: " con)
          (d/div "Dex: " dex))
   (d/div {:class "grid"}
          (d/div "Wis: " wis)
          (d/div "Int: " int)
          (d/div "Cha: " cha))))

(defnc character-health
  [{:keys [hp max-hp wounds inventory-slots]}]
  (d/div
   (d/progress {:value hp
                :max max-hp})))



