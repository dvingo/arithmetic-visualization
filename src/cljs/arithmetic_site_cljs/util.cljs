(ns arithmetic-site-cljs.util
  (:use [arithmetic-site-cljs.state :only [*debug?*]]))

(defn l [x]
  (if *debug?* (.log js/console x)))
