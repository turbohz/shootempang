(ns pang.core.desktop-launcher
  (:require [pang.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. pang-game "pang" 800 600)
  (Keyboard/enableRepeatEvents true))
