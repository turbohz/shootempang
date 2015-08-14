(ns pang.entity.player
  (:require
    [play-clj.core :refer :all]
    [pang.input :refer [inputs]]
    [pang.util :as util]
    [play-clj.g2d :refer [texture]]
    [play-clj.math :refer [vector-2]]
    ))

(def start {
            :x 0
            :y 0
            :width 32
            :height 32
            :type :player
            :velocity (vector-2 0,0)
            :acceleration (vector-2 0,0)
            })
(defn ? [e] (= :player (:type e)))

(defn create [tiles]
  "Returns a player entity, extracted from the provided tileset"
  (-> tiles
      (aget 0 0)
      texture
      (merge start))
  )

(defn move [e]
  "Updates player physics according to current inputs"
  (let [
        v (vector-2 0,0)
        v (reduce
            (fn [v [key pressed?]]
              (if pressed?
                (case key
                  :right (x! v 4)
                  :left (x! v -4)
                  v)
                v
                ))
            v
            @inputs)
        ]
    ; update entity velocity vector
    (assoc-in e [:velocity] v)
  ))