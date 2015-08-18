(ns pang.util)

(defn add-to [e k v] (assoc e k (+ (k e) v)))

(defn map-if [c f xs]
  "Applies f to elements that satisfies c"
  (->> xs (map (fn [x] (if (c x) (f x) x))))
  )

(defn within-bounds [e]
  "Forces the entity coordinates to be within screen bounds"
  (if (< (:x e) 0) (assoc-in e [:x] 0) e)
  )

(defmacro dig
  "Renders extraction of values within nested hash-maps"
  ([h] h)
  ([h & ks] `(~(last ks) (dig ~h ~@(butlast ks)))))
