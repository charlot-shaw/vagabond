(ns vagabond.attribute
  (:require [sci.core :as sci]
            [clojure.math :as math]))

;; An attribute set is a sequence of transformation steps
;; it is meant to be reduced over to produce a resulting map.

;; a transformation is a map with the following keys

(def ex-transform-1
  '{:input-keys [:dex-score]
   :production-expr (/ (- 10 dex-score) 2)
   :output :dex-mod})

(def ex-transform 
  {:inputs [:level :int]
   :name "Transformation name"
   :description "Adds half your level to wisdom and intillegence."
   :outputs {:wis "(+ wis (* level 0.5))"
             :int "(+ int (* level 0.5))"}})

(def approved-math-fns
  {'floor math/floor
   'ceil math/ceil
   'signum math/signum
   'sqrt math/sqrt})

(defn context->vars 
  [ctx wanted]
  (map (fn [key]
         (if (vector? key)
           [(-> key last symbol)
            (get-in ctx key ::not-found)]
           [(symbol key) (get ctx key ::not-found)]))
       wanted))

(defn eval-single-transform [ctx transform]
  (let [user-namespace (into approved-math-fns (context->vars ctx (:inputs transform)))]
    (assoc ctx 
           (:output transform) 
           (sci/eval-string (:expr transform) 
                            {:namespaces {'user user-namespace}}))))

(defn eval-transform [ctx transform]
  (let [user-namespace (into approved-math-fns (context->vars ctx (:inputs transform)))]
    (reduce (fn [ctx [expression-key expression-step]]
                (assoc ctx 
                       expression-key
                              (sci/eval-string expression-step
                                        {:namespaces {'user user-namespace}})))
            ctx
            (:outputs transform))))

(defn eval-attributes [transforms]
  (reduce eval-transform {} transforms))


(comment 
  (eval-transform
   {:con 1
    :wis 2
    :xp 3491
    :vitality {:hp 10
               :damage 8}}
   {:inputs [[:vitality :damage]
             [:vitality :hp]]
    :expr "{:health-remaining (- hp damage)}"})
  
  (eval-single-transform
   {:con 1
    :wis 2
    :xp 3491}
   {:inputs [:xp]
    :output :level
    :expr "(floor (/ xp 1000))"})
  
  (tap> (eval-attributes 
         [{:name :base-stats
           :inputs []
           :outputs {:int "1"
                     :cha "2"
                     :wis "1"
                     :con "0"
                     :level "1"}}
          {:name :base-health
           :inputs [:con :level]
           
           }]
         )))