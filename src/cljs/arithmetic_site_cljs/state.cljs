(ns arithmetic-site-cljs.state
  (:require [om.core :as om]))

(def *debug?* false)

(def app-state
  (atom { :first-number 0
         :second-number 0
         :operation "+" }))
