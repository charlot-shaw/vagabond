(ns vagabond.database
  (:require 
   [vagabond.spec :as v-spec]))

(def initial-db 
  {::v-spec/characters {1 
                {::v-spec/name "Tuesday Slitten"
                 ::v-spec/stats {::v-spec/dex 0
                          ::v-spec/con 2
                          ::v-spec/int 3
                          ::v-spec/str 1
                          ::v-spec/cha 2
                          ::v-spec/wis 1}
                 ::v-spec/level 4}}})