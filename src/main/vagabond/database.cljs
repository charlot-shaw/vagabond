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
                 ::v-spec/level 4
                 ::v-spec/inventory [{::v-spec/name "Stabbed"
                                      ::v-spec/description "Under right shoulder blade."
                                      ::v-spec/slots-taken 2
                                      ::v-spec/wound true}
                                     {::v-spec/name "Dreamlost"
                                      ::v-spec/description "Lethal poison that denies the victim sleep." 
                                      ::v-spec/slots-taken 1
                                      ::v-spec/armor 0}
                                     {::v-spec/name "Hauberk"
                                      ::v-spec/description "Chain vest"
                                      ::v-spec/slots-taken 1
                                      ::v-spec/armor 1}
                                     {::v-spec/name "Sickle"
                                      ::v-spec/slots-taken 1
                                      ::v-spec/attack "1d6"}
                                     {::v-spec/name "Jezail"
                                      ::v-spec/slots-taken 2
                                      ::v-spec/attack "1d8"
                                      ::v-spec/range "120'"}]}}})