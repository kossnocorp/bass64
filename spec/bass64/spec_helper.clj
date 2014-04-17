(ns bass64.spec-helper
  (:require
    [speclj.core :refer :all]))

(defn fixture-path
  [path]
  (str "./spec/bass64/fixtures/" path))

(defn load-fixture-string
  [path]
  (slurp (fixture-path path)))

(defn load-fixture
  [path]
  (java.io.StringReader. (load-fixture-string path)))
