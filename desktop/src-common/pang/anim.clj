(ns pang.anim
  (:require
    [pang.entity.player :as player]
    [play-clj.core :refer :all]
    [play-clj.math :refer [vector-2 vector-2!]]))

(defn update-velocity [e]
  (assoc-in e [:velocity] (vector-2! (:velocity e) :add (:acceleration e))))

(defn update-position [e]
  (let [
        position (vector-2 (:x e) (:y e))
        position (vector-2! position :add (:velocity e))]
    (-> e (assoc-in [:x] (x position)) (assoc-in [:y] (y position)))))

(defn reflect-vector-component [c v]
  (case c
    :x (x! v (* -1 (x v)))
    :y (y! v (* -1 (y v)))
    v
    ))

(defn enforce-bounds [screen e]
  (cond
    (or (< (:x e) 0) (> (:x e) (width screen))) (assoc-in e [:velocity] (reflect-vector-component :x (:velocity e)))
    (or (< (:y e) 0) (> (:y e) (height screen))) (assoc-in e [:velocity] (reflect-vector-component :y (:velocity e)))
    :else e))

(defn move-player [e]
  (if (player/? e) (player/move e) e))

(defn animate [screen entities]
  (map (fn [e]
         (->> e
             move-player
             update-velocity
             update-position
             (enforce-bounds screen)))
       entities))