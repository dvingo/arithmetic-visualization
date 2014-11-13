(ns arithmetic-site-cljs.draw
  (:require [clojure.string :as string]))

(defn l [x] (.log js/console x))
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
