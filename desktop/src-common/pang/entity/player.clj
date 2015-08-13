(ns pang.entity.player
  (:require
    [pang.input :refer [inputs]]
    [pang.util :as util]
    [play-clj.g2d :refer [texture]]
    ))

(def start {:x 0 :y 0 :width 32 :height 32 :type :player})
(defn ? [e] (= :player (:type e)))

(defn add-to [e k v] (assoc e k (+ (k e) v)))

(defn create [tiles]
  "Returns a player entity, extracted from the provided tileset"
  (-> tiles
      (aget 0 0)
      texture
      (merge start))
  ;player (texture (aget tiles 0 0))
  ;player (assoc player start)
  )

(defn move [p]
  "Updates player position according to current inputs"
  (reduce
    (fn [p [k v]]
      (if v
        (case k
          :right (util/add-to p :x 8)
          :left (util/add-to p :x -8)
          p)
        p)
      )
    p
    @inputs))