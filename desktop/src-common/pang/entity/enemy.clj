(ns pang.entity.enemy
  (:require
    [play-clj.g2d :refer [texture]]
    [play-clj.math :refer [vector-2]]
    ))

(def start {
            :x 300
            :y 300
            :width 32
            :height 32
            :type :enemy
            :velocity (vector-2 2,0)
            :acceleration (vector-2 0,-0.1)
            })
(defn ? [e] (= :enemy (:type e)))

(defn create [tiles]
  "Returns entity, extracted from the provided tileset"
  (-> tiles
      (aget 1 0)
      texture
      (merge start))
  )
