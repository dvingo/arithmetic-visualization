(ns arithmetic-site-cljs.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [arithmetic-site-cljs.state :as state]))

(enable-console-print!)

(defn draw-vis [app-state]
  (let [first-num (:first-number @app-state)]
    (.log js/console "first num: ")
    (.log js/console (js/Number first-num))
    ))

(defn update-num-value [app-key new-val app-state]
  (om/update! app-state app-key new-val))

(defn arithmetic-view [app owner]
  (reify
    om/IRenderState
    (render-state [_ state]
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
