(ns pang.input
  (:require [play-clj.core :refer :all]
            [clojure.pprint :refer [pprint]]))

(def valid-controls #{(key-code :space) (key-code :dpad-left) (key-code :dpad-right)})

(def inputs (atom {:left false :right false :fire false}))
(defn print-inputs [& args] (clojure.pprint/pprint @inputs))
(add-watch inputs :watcher print-inputs)

(defn update-input [key v]
  (when
    (contains? valid-controls key)
    (cond
      (= key (key-code :dpad-right))
      (swap! inputs assoc-in [:right] v)
      (= key (key-code :dpad-left))
      (swap! inputs assoc-in [:left] v)
      (= key (key-code :space))
      (swap! inputs assoc-in [:fire] v)
      )
    ))

(defn handle-key-up [key] (update-input key false))
(defn handle-key-down [key] (update-input key true))


