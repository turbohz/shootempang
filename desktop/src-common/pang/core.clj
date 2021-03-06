(ns pang.core
  (:require
    [play-clj.core :refer :all]
    [play-clj.ui :refer :all]
    [play-clj.g2d :refer :all]
    [pang.input :refer [handle-key-up handle-key-down]]
    [pang.entity.player :as player]
    [pang.entity.enemy :as enemy]
    [pang.util :as util]
    [pang.anim :refer [animate]]
    [pang.tiles :refer [tiles]]
    ))

(defscreen main-screen

           :on-show
           (fn [screen entities]
             (update! screen :renderer (stage))
             (pang.tiles/load "tiles.png")
             (let [
                   player (player/create)
                   enemy (enemy/create)
                   ]
               [player enemy]
               )
             )

           :on-render
           (fn [screen entities]
             (clear!)
             (render! screen (animate screen entities)))

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
