(ns pang.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]
            [play-clj.g2d :refer :all]
            [pang.input :refer [handle-key-up handle-key-down inputs]]))

(defn add-to [e k v] (assoc e k (+ (k e) v)))

(defn map-if [c f xs]
  (->> xs (map (fn [x]
                 (if (c x) (f x) x)
                 )))
  )

(defn within-bounds [e]
  "Forces the entity coordinates to be within screen bounds"
  (if (< (:x e) 0) (assoc-in e [:x] 0) e)
  )

(defn move-player [p]
  "Updates player position according to current inputs"
  (reduce
    (fn [p [k v]]
      (if v
        (within-bounds (case k
                         :right (add-to p :x 8)
                         :left (add-to p :x -8)
                         p))
        p)
      )
    p
    @inputs))

(defn player? [e] (= :player (:type e)))

(defscreen main-screen

           :on-show
           (fn [screen entities]
             (update! screen :renderer (stage))
             (let [
                   sheet (texture "tiles.png")
                   tiles (texture! sheet :split 32 32)

                   player (texture (aget tiles 0 0))
                   player (assoc player :x 0 :y 0 :width 32 :height 32 :type :player)
                   meteor (texture (aget tiles 1 0))
                   meteor (assoc meteor :x 300 :y 300 :width 32 :height 32 :type :meteor)
                   ]
               [player meteor]
               )
             )

           :on-render
           (fn [screen entities]
             (clear!)
             (let [entities (->> entities (map-if player? move-player))]
               (render! screen entities))
             )

           :on-key-down
           (fn [screen entities]
             (handle-key-down (:key screen))
             entities
             )

           :on-key-up
           (fn [screen entities]
             (handle-key-up (:key screen))
             entities
             )

           )

(defgame pang-game
         :on-create
         (fn [this]
           (set-screen! this main-screen)))
