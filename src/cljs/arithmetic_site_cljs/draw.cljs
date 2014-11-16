(ns arithmetic-site-cljs.draw
  (:require [clojure.string :as string])
  (:use [arithmetic-site-cljs.util :only [l]]))

(defn draw-rectangle [matrix-data parent-el]
  (let [columns (-> parent-el
                  (.selectAll ".top")
                  (.data (clj->js matrix-data)))]

    (-> columns (.enter)
      (.append "g")
      (.attr "class" "top"))

    (-> columns
      (.attr "transform" #(string/join ["translate(" (* %2 30) ",0)"])))

    (let [nested (-> columns (.selectAll ".nested")
                   (.data (fn [d] d)))]

      (l "NESTED: ")
      (l nested)

      (-> nested (.enter)
          (.append "g")
          (.attr "class" "nested"))

      (-> nested (.append "rect")
        (.attr "x" 50)
        (.attr "y" #(* % 30))
        (.attr "width" 20)
        (.attr "height" 20))

      (-> nested (.append "text")
        (.attr "x" 50)
        (.attr "y" #(* % 30))
        (.attr "dy" ".71em")
        (.text (fn [d] d)))

      (-> columns (.exit) (.remove))
      (-> nested (.exit) (.remove)))))

(defn draw-number-line [data parent-el]
  (let [data (clj->js data)
        line (-> parent-el
               (.selectAll "div")
               (.data data))]
  (-> line .enter (.append "div")
    (.style "width" "20px")
    (.style "opacity" "0")
    (.style "background-color" "lightcyan")
    (.style "border" "1px solid black")
    (.text (fn [d] d)))

  (-> line .transition (.style "opacity" "1"))

  (-> line .exit
    .transition
      (.duration 700)
      (.style "opacity" "0")
      remove)))
