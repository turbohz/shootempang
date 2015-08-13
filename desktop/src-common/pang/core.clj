(ns pang.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]
            [play-clj.g2d :refer :all]))

(defn add-to [e k v] (assoc e k (+ (k e) v)))

(defn map-if [c f xs]
  (->> xs (map (fn [x]
                 (if (c x) (f x) x)
                 )))
  )

(def inputs (atom {:left false :right false :fire false}))

(defn player? [e] (= :player (:type e)))
(def valid-controls #{(key-code :space) (key-code :dpad-left) (key-code :dpad-right)})

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
             (render! screen entities)
             )

           :on-key-down
           (fn [screen entities]
             (when
               (contains? valid-controls (:key screen))
               (let [
                     key (:key screen)
                     move (fn [player] (cond
                                         (= key (key-code :dpad-right))
                                         (add-to player :x 8)
                                         (= key (key-code :dpad-left))
                                         (add-to player :x -8)))
                     entities (->> entities (map-if player? move))
                     ]

                 entities)
               )))

(defgame pang-game
         :on-create
         (fn [this]
           (set-screen! this main-screen)))
