(ns arithmetic-site-cljs.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [arithmetic-site-cljs.state :as state])
  (:use [arithmetic-site-cljs.draw :only [draw-rectangle]]))

(enable-console-print!)

(defn l [x] (.log js/console x))
(defn draw-vis [app-state svg-el]
  (let [first-num (:first-number @app-state)
        second-num (:second-number @app-state)
        operator (:operator @app-state)
        matrix-data (vec (map #(range 0 first-num) (range 0 second-num)))
        svg-el (:svg-el @app-state)]
    (l "SVG EL: ")
    (l svg-el)
    (l "Matrix data: ")
    (l matrix-data)
    (l "cljs->js Matrix data: ")
    (l (clj->js matrix-data))
    (draw-rectangle matrix-data svg-el)))

(defn update-num-value [app-key new-val app-state]
  (om/update! app-state app-key new-val))

(defn arithmetic-view [app owner]
  (reify
    om/IDidMount
    (did-mount [_]
      (om/update! app :svg-el
                  (-> js/d3 (.select "#vis-output") (.append "svg")
                    (.attr "width" 800) (.attr "height" 800))))
    om/IRenderState
    (render-state [_ _]
      (dom/div #js {:className "container"}
        (dom/header #js {:className "head"}
          (dom/h1 nil "Arithmetic")
          (dom/h3 nil "The method or process of computation with figures:")
          (dom/h4 nil "the most elementary branch of mathematics.")

        (dom/form #js {:className "inputs"}

          (dom/div #js {:className "first-input"}
            (dom/label #js {:htmlFor "first"} "This")
            (dom/input #js {:type "number" :name "first" :placeholder "number..."
                            :onChange #(update-num-value :first-number (.. % -target -value) app)}))

          (dom/div #js {:className "operation-selector"}
            (dom/select #js {:onChange #(om/update! app :operation (.. % -target -value))}
              (dom/option nil "+")
              (dom/option nil "-")
              (dom/option nil "*")
              (dom/option nil "/")))

          (dom/div #js {:className "second-input"}
            (dom/label #js {:htmlFor "second"} "That")
            (dom/input #js {:type "number" :name "second" :placeholder "number..."
                            :onChange #(update-num-value :second-number (.. % -target -value) app)}))

          (dom/button #js {:type "button" :onClick #(draw-vis app)} "Equals"))

          (dom/div #js {:className "visualization"}
            (dom/div #js {:className "columns"}
              (dom/div #js {:className "one"})
              (dom/div #js {:className "two"})
              (dom/div #js {:id "vis-output"}))))))))

(defn main []
  (om/root arithmetic-view state/app-state
    {:target (. js/document (getElementById "app"))}))
