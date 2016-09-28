(ns buzhidao.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :word
 (fn [db _]
   (:word db)))

(reg-sub
 :reveal
 (fn [db _]
   (:reveal db)))

(reg-sub
  :get-greeting
  (fn [db _]
    (:greeting db)))
