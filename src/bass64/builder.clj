(ns bass64.builder
  (:require [net.cgrand.enlive-html :as html]
            [org.bovinegenius.exploding-fish :as uri]
            [bass64.parser :as parser])
  (:gen-class :main true))

(defn get-url [url] (java.net.URL. url))

(defn path-relative-to-root? [path] (= \/ (first path)))

(defn resolve-url
  [src url]
  (if (uri/absolute? src)
    src
    (let
      [uri-obj (uri/uri url)
       scheme (uri-obj :scheme)
       host (uri-obj :host)
       scheme-and-host (str scheme "://" host)]
      (if (path-relative-to-root? src)
        (str scheme-and-host src)
        src))))

(defn resolve-urls
  [bass64-map page-url]
  ())

(defn -main [url]
  (println
    (html/select (parser/parse-html (get-url url)) [:img])))
