(ns pang.core
  (:require
    [play-clj.core :refer :all]
    [play-clj.ui :refer :all]
    [play-clj.g2d :refer :all]
    [pang.input :refer [handle-key-up handle-key-down]]
    [pang.entity.player :as player]
    [pang.entity.enemy :as enemy]
    [pang.util :as util]))

(defscreen main-screen

           :on-show
           (fn [screen entities]
             (update! screen :renderer (stage))
             (let [
                   sheet (texture "tiles.png")
                   tiles (texture! sheet :split 32 32)
                   player (player/create tiles)
                   enemy (enemy/create tiles)
                   ]
               [player enemy]
               )
             )

           :on-render
           (fn [screen entities]
             (clear!)
             (let [entities (->> entities (util/map-if player/? #(-> % player/move util/within-bounds)))]
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
