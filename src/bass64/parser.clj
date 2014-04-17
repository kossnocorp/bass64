(ns bass64.parser
  (:require [net.cgrand.enlive-html :as html]))

(defn parse-html
  [data]
  (html/html-resource data))

(defn filter-imgs
  [nodes]
  (html/select nodes [:img]))

(defn get-attr
  [node, attr]
  (get-in node [:attrs attr]))

(defn has-attr?
  [node, attr]
  (get-attr node attr))

(defn filter-nodes-by-attr
  [nodes attr-name]
  (defn has-bass64-attr? [node] (has-attr? node attr-name))
  (filter has-bass64-attr? nodes))

(defn filter-bass64-nodes
  [nodes]
  (filter-nodes-by-attr nodes :data-bass64))

(defn convert-to-bass64-map
  [node]
  {:id (get-attr node :data-bass64) :src (get-attr node :src)})

(defn get-bass64-map
  [nodes]
  (map
    convert-to-bass64-map
    (filter-bass64-nodes (filter-imgs nodes))))
