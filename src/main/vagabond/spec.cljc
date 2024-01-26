(ns vagabond.spec 
  (:require 
   [malli.core :as m]
   [malli.generator :as mg]))


(def Transform
  [:map 
   [:inputs [:vector :keyword]]
   [:name :string]
   [:description {:optional true} :string]
   [:outputs [:map-of :keyword :string]]])


(comment
  (m/validate Transform {:name "Example transformation"
                         :description "Hello"
                         :inputs [:int]
                         :outputs {}})
  (mg/generate Transform) 

  )