(ns pang.tiles
  (:require
    [play-clj.g2d :refer [texture texture!]]
    ))

; apparently, until the screen is created, you cannot load any assets
(def tiles (promise))

(defn load [filename]
      (let [ sheet (texture filename) ]
        (deliver tiles (texture! sheet :split 32 32))))