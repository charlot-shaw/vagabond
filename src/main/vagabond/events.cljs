(ns vagabond.events
  (:require 
   [refx.alpha :refer [reg-event-db reg-event-fx inject-cofx]]
   [refx.interceptors :refer [path after]]
   [malli.core :as m]
   [vagabond.spec :as v-spec]
   [vagabond.database :as v-db]))

(defn check-and-throw 
  "Throws an exception if the db doesn't match the provided malli spec"
  [spec db]
  (when-not (m/validate spec db)
    (throw (ex-info (str "spec check failed: " (m/explain spec db))
                    {}))))

(def check-spec-interceptor (after (partial check-and-throw v-spec/Database)))

(reg-event-fx 
 ::init-db
 [check-spec-interceptor]
 (fn initialize-db [_]
   {:db v-db/initial-db}))