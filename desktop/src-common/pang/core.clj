(ns pang.core
  (:require
    [play-clj.core :refer :all]
    [play-clj.ui :refer :all]
    [play-clj.g2d :refer :all]
    [pang.input :refer [handle-key-up handle-key-down]]
    [pang.entity.player :as player]
    [pang.entity.enemy :as enemy]
    [pang.util :as util]
    [play-clj.math :refer [vector-2 vector-2!]]
    ))


(defn update-velocity [e]
  (assoc-in e [:velocity] (vector-2! (:velocity e) :add (:acceleration e))))

(defn update-position [e]
  (let [
        position (vector-2 (:x e) (:y e))
        position (vector-2! position :add (:velocity e))]
    (-> e (assoc-in [:x] (.x position)) (assoc-in [:y] (.y position)))))

(defn animate [screen entities]
  (map (fn [e]
         (if (enemy/? e)
           (-> e update-velocity update-position)
           e)
         )
       entities))

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
             (let [
                   entities (->> entities (util/map-if player/? #(-> % player/move util/within-bounds)))
                   entities (animate screen entities)
                   ]

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
