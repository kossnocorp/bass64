(ns bass64.parser
  (:require [net.cgrand.enlive-html :as html]))

(defn parse-html
  [data]
  (html/html-resource data))

(defn filter-imgs
  [nodes]
  (html/select nodes [:img]))

(defn has-attr?
  [node, attr]
  (get-in node [:attrs attr]))

(defn filter-nodes-by-attr
  [nodes attr-name]
  (defn has-bass64-attr? [node] (has-attr? node attr-name))
  (filter has-bass64-attr? nodes))

(defn filter-bass64-imgs
  [nodes]
  (filter-nodes-by-attr nodes :data-bass64))
