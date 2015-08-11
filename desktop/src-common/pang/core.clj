(ns pang.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]
            [play-clj.g2d :refer :all]))

(defscreen main-screen
           :on-show
           (fn [screen entities]
             (update! screen :renderer (stage))
             (let [
                   sheet (texture "tiles.png")
                   tiles (texture! sheet :split 32 32)
                   player (texture (aget tiles 0 0))
                   player (assoc player :x 0 :y 0 :width 32 :height 32)
                   meteor (texture (aget tiles 1 0))
                   meteor (assoc meteor :x 300 :y 300 :width 32 :height 32)
                   ]
               [player meteor]
               )
             )

           :on-render
           (fn [screen entities]
             (clear!)
             (render! screen entities)
             )

           )

(defgame pang-game
         :on-create
         (fn [this]
           (set-screen! this main-screen)))
