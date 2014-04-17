(ns bass64.builder
  (:require [net.cgrand.enlive-html :as html]
            [bass64.parser :as parser])
  (:gen-class :main true))

(defn get-url [url] (java.net.URL. url))

(defn -main [url]
  (println
    (html/select (parser/parse-html (get-url url)) [:img])))
