(ns vagabond.spec
  (:require 
   [malli.core :as m]))

(def NonEmptyString
  [:string {:min 1}])

(def NatInt 
  [:int {:min 0}])

(def Stats 
  [:map 
   [::dex NatInt]
   [::str NatInt]
   [::con NatInt]
   [::wis NatInt]
   [::int NatInt]
   [::cha NatInt]])

(def Character 
  [:map 
   [::name NonEmptyString]
   [::level NatInt]
   [::stats Stats]])

(def Database 
  [:map 
   [::characters [:map-of NatInt Character]]])

(comment
  (m/validate 0)
  (m/validate Database
              {::characters
               {1
                {::name "hello"
                 ::stats {::dex 0
                          ::con 2
                          ::int 3
                          ::str 1
                          ::cha 2
                          ::wis 1}
                 ::level 4}}})
  (+ 9 0 8)
  Character
  *e)
