(ns pang.entity.player
  (:require
    [play-clj.core :refer :all]
    [pang.input :refer [inputs]]
    [pang.util :refer [dig]]
    [play-clj.g2d :refer [texture]]
    [play-clj.math :refer [vector-2]]
    [pang.settings :refer [settings]]
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
        ; start with a 0 vector
        v (vector-2 0,0)
        ; mutate v according to input
        v (reduce
            (fn [v [key pressed?]]
              (if pressed?
                (case key
                  :right (x! v (dig settings :player :x-speed))
                  :left (x! v (- (dig settings :player :x-speed)))
                  v)
                v))
            v
            @inputs)
        ]
    ; finally, update entity velocity
    (assoc-in e [:velocity] v)
  ))