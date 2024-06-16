(ns vagabond.components
  (:require 
   [helix.core :refer [$ defnc <>]]
   [helix.dom :as d]
   [refx.alpha :as refx :refer [use-sub dispatch]]
   [vagabond.spec :as-alias v-spec]))


(def key->color-class {:primary "is-primary"
                       :danger "is-danger"
                       :warning "is-warning"
                       :link "is-link"
                       :info "is-info"
                       :success "is-success"})

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
           (d/p {:class "has-text-primary has-text-weight-semibold"}
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

(defnc item-tag [props]
  (let [{:keys [title desc color]} (:data props)
        color-class (or (key->color-class color)
                        "is-info")]
    (d/div {:class "control"}
           (d/div {:class "tags has-addons"}
                  (d/span {:class (str "tag " color-class)} title)
                  (when desc (d/span {:class "tag"} desc))))))

(defnc inventory-row [props]
  (let [{:keys [::v-spec/name
                ::v-spec/description
                ::v-spec/slots-taken
                ::v-spec/armor
                ::v-spec/attack
                ::v-spec/range
                ::v-spec/wound]} (:data props)]
    (d/tr {:key name
           :class (when wound "is-danger")} 
          (d/td name)
          (d/td (d/div description
                       (d/div {:class "field is-grouped is-multiline"}
                              (when (< 0 (or armor 0)) 
                                ($ item-tag {:data {:title "Armor"
                                                    :desc (str "+" armor)}}))
                              (when range
                                ($ item-tag {:data {:title "Range"
                                                    :desc range}}))
                              (when attack
                                ($ item-tag {:data {:title "Attack"
                                                    :desc attack}})))))
          (d/td slots-taken))))

(defnc inventory-block []
  (let [inventory (use-sub [:inventory])
        inventory-max-slots (use-sub [:inventory-max-slots])
        taken-inventory (use-sub [:inventory-taken])] 
    (d/div {:class ""}
           (d/p "Inventory " (str taken-inventory "/" inventory-max-slots))
           (d/table {:class "table is-fullwidth"}
                    (d/thead {:class "thead"}
                             (d/tr
                              (d/th "Name")
                              (d/th "Description")
                              (d/th "Slots")))
                    (d/tbody {:class "tbody"}
                             (for [item inventory]
                               (<>
                                ($ inventory-row {:data item}))))))))


(defnc sheet-layout []
  (<>
   ($ name-bar)
   (d/div {:class "columns"}
          (d/div {:class "column"}
                 ($ stat-block))
          (d/div {:class "column"}
                 ($ inventory-block)))))


(comment 
  (name ::v-spec/str))