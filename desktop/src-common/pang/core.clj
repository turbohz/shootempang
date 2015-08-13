(ns pang.core
  (:require
    [play-clj.core :refer :all]
    [play-clj.ui :refer :all]
    [play-clj.g2d :refer :all]
    [pang.input :refer [handle-key-up handle-key-down]]
    [pang.entity.player :as player]))

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

(defscreen main-screen

           :on-show
           (fn [screen entities]
             (update! screen :renderer (stage))
             (let [
                   sheet (texture "tiles.png")
                   tiles (texture! sheet :split 32 32)
                   player (player/create tiles)
                   meteor (texture (aget tiles 1 0))
                   meteor (assoc meteor :x 300 :y 300 :width 32 :height 32 :type :meteor)
                   ]
               [player meteor]
               )
             )

           :on-render
           (fn [screen entities]
             (clear!)
             (let [entities (->> entities (map-if player/? #(-> % player/move within-bounds)))]
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
