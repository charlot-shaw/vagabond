(ns vagabond.attribute-test
  (:require [vagabond.attribute :as sut]
            [clojure.test :as test :refer [deftest is testing]]))

(def test-ctx
  {:con 1
   :wis 2
   :xp 3491
   :vitality {:hp 10
              :damage 8}})

(deftest context->vars-test
  (testing "Simple extraction"
    (is (every? symbol? (map first
                             (sut/context->vars
                              test-ctx [:con :wis :xp]))))
    
    (is (= 3491 (-> (sut/context->vars
                     test-ctx
                     [:xp])
                    first ;; first of the sequence of vars 
                    last ;; last of the pair
                    ))))
  
  (testing "Layered extraction"
    (is (= 8 (-> (sut/context->vars
                  test-ctx
                  [[:vitality :damage]])
                 first
                 last))))
  
  (testing "Not-found marker"
    (is (= ::sut/not-found
           (-> (sut/context->vars
                test-ctx
                [:vitypo :damage])
               first
               last)))))



(deftest eval-single-transform-test
  (testing "simple arithmetic"
    (is (= 12 (:wis-save
               (sut/eval-single-transform test-ctx
                                          {:inputs [:wis]
                                           :output :wis-save
                                           :expr "(+ wis 10)"}))))
    (is (= 3 (:level
              (sut/eval-single-transform test-ctx
                                         {:inputs [:xp]
                                          :output :level
                                          :expr "(quot xp 1000)"})))))
  (testing "whitelisted math fns"
    #_(is (= 3 (:level
                (sut/eval-single-transform test-ctx
                                           {:inputs [:xp]
                                            :outputs :level
                                            :expr "(floor (/ xp 1000))"}))))))
(comment
  (-> [:vitality :damage] last symbol)
  (get-in test-ctx [:vitality :damage]))

(deftest eval-transform-test
  (testing "Multiple evaluations"
    (is (= {:con 11
            :wis 12}
           (select-keys (sut/eval-transform test-ctx 
                                            {:name "Example transform"
                                             :description ""
                                             :inputs [:con :wis [:vitality :hp]]
                                             :outputs {:wis "(+ hp wis)"
                                                       :con "(+ hp con)"}})
                        [:wis :con])))))