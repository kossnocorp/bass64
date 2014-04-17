(ns bass64.builder
  (:require [clojure.data.json :as json]
            [org.bovinegenius.exploding-fish :as uri]
            [bass64.parser :as parser]
            [bass64.converter :as converter])
  (:gen-class :main true))

(defn get-url [url] (java.net.URL. url))

(defn path-relative-to-root? [path] (= \/ (first path)))

(defn normalize-url
  [src url]
  (if (uri/absolute? src)
    src
    (let
      [uri-obj (uri/uri url)
       scheme (uri-obj :scheme)
       host (uri-obj :host)
       port  (uri-obj :port)
       scheme-and-host (if (number? port) (str scheme "://" host ":" port) (str scheme "://" host))]
      (if (path-relative-to-root? src)
        (str scheme-and-host src)
        src))))

(defn bass64-map-with-url
  [bass64-map page-url]
  (assoc bass64-map :url (normalize-url (:src bass64-map) page-url)))

(defn bass64-maps-with-urls
  [bass64-maps page-url]
  (defn bass64-map-with-page-url [bass64-map] (bass64-map-with-url bass64-map page-url))
  (map bass64-map-with-page-url bass64-maps))

(defn build-bass64-json
  [bass64-maps])

(defn bass64-map-with-base64
  [bass64-map]
  (assoc bass64-map :base64 (converter/convert-to-base64 (:url bass64-map))))

(defn bass64-maps-with-base64
  [bass64-maps]
  (map bass64-map-with-base64 bass64-maps))

(defn bass64-maps-as-json-map
  [bass64-maps]
  (loop
    [maps bass64-maps
     json-map {}]
    (if (seq maps)
      (let
        [bass64-map (first maps)
         id (:id bass64-map)
         base64 (:base64 bass64-map)]
        (recur (rest maps) (assoc json-map id base64)))
      json-map)))

(defn build-json-from-bass64-maps
  [bass64-maps]
  (json/write-str (bass64-maps-as-json-map bass64-maps)))

(defn -main [url]
  (let
    [html (parser/parse-html (get-url url))
     raw-bass64-maps (parser/get-bass64-map html)
     bass64-maps-with-urls (bass64-maps-with-urls raw-bass64-maps url)
     bass64-maps (bass64-maps-with-base64 bass64-maps-with-urls)
     json (build-json-from-bass64-maps bass64-maps)]
    (println json)))
