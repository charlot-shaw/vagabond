(ns vagabond.generator
  (:require
   [vagabond.rng :as rng]))

;; ----------------
;; This file deals with generating the stats and derived stats for a character
;; ----------------

(def stat-keys [:str :cha :wis :int :con :dex])

(def blank-stats (apply
                  hash-map (interleave stat-keys
                                       (repeat 0))))

(defn vitals [seed-rng level _char-map]
  (let [max-hp (reduce  + (take level (repeatedly #(rng/next-int! seed-rng 6))))
        present-hp max-hp
        wounds 0]
    {:max-hp max-hp
     :present-hp present-hp
     :wounds wounds}))

(defn inventory [seed-rng level char-map]
  ())

#_(defn character-generator "Dispatches and marshalls sub-generators"
    [seed level]
    #_(merge
       {:stats (stats (rng/make-rng [seed :stats])
                      level
                      {})}
       (vitals (rng/make-rng [seed :vitals])
               level
               {})
       (inventory (rng/make-rng [seed :inventory])
                  level
                  {})))

(defn gen-stats "Takes a map with level and rng keys and generates stats"
  [{:keys [rng level] :as char-map}]
  (let [rng-fn (:stats rng)
        _ (println char-map)
        stats-to-boost (map (partial get stat-keys)
                            (take (* level 3)
                                  (repeatedly #(rng/next-int! rng-fn 6))))
        _ (println stats-to-boost)
        stats (reduce #(update %1 %2 inc) blank-stats stats-to-boost)]
    (merge char-map stats)))

(defn gen-vitals [{:keys [rng level] :as char-map}]
  (let [rng-fn (:vitals rng)
        max-hp (first (nth (iterate (fn [[sum level]]
                                      (println :gen/vitals sum level)
                                      [(max (reduce + 0 (take level (repeatedly #(rng/next-int! rng-fn 6))))
                                            (inc sum))
                                       (inc level)])
                                    [0 0])
                           level))]
    (merge char-map {:wounds 0
                     :max-hp max-hp
                     :damage 0})))

(defn character-generator
  "Generates a character from a seed"
  [seed level]
  (println [seed level])
  (-> {:level level
       :rng {:stats (rng/make-rng [seed :stats])
             :vitals (rng/make-rng [seed :vitals])}}
      (gen-stats)
      (gen-vitals)))

(comment
  (rng/next-int! (rng/make-rng 2) 6)
  (nth (iterate inc 0) 3)
  (println "hello")
  (reduce + (take 2 (repeatedly (rng/next-int! (rng/make-rng 3) 6))))
  (character-generator "example" 2)

  (map :max-hp (map #(character-generator %1 3) (range 10)))
  (reduce #(update %2 %1 inc) blank-stats [:str :str :dex])
  (stats (rng/make-rng 1)
         1))

