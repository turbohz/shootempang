(ns pang.entity.enemy
  (:require
    [play-clj.g2d :refer [texture]]
    [play-clj.math :refer [vector-2]]
    [pang.settings :refer [settings]]
    [pang.util :refer [dig]]
    [pang.tiles :refer [tiles]]
    ))

(def start {
            :x            300
            :y            300
            :width        32
            :height       32
            :type         :enemy
            :velocity     (vector-2 (dig settings :enemy :x-speed), 0)
            :acceleration (vector-2 0, (:gravity settings))
            })
(defn ? [e] (= :enemy (:type e)))

(defn create []
  "Returns entity, extracted from the game tileset"
  (-> @tiles
      (aget 1 0)
      texture
      (merge start))
  )
