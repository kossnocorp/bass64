(ns bass64.parser-spec
  (:require [speclj.core :refer :all]
            [bass64.parser :refer :all]))

(defn load-fixture
  [path]
  (java.io.StringReader. (slurp (str "./spec/bass64/fixtures/" path))))

(describe "parse-html"
  (it "parses html string"
    (should=
      [{:tag :html :attrs nil :content [{:tag :body :attrs nil :content [{:tag :div :attrs {:id "first"} :content nil} "\n" {:tag :div :attrs {:id "second"} :content nil} "\n"]}]}]
      (parse-html (load-fixture "two_divs.html")))))

(describe "filter-imgs"
  (it "filters img nodes"
    (should=
      [{:tag :img :attrs {:src "1.png"} :content nil} {:tag :img :attrs {:src "2.png"} :content nil}]
      (filter-imgs (parse-html (load-fixture "with_images.html"))))))

(describe "has-attr?"
  (it "returns true if passed node has the given attribute"
    (def node {:tag :img :attrs {:src "1.png"}})
    (should (has-attr? node :src))
    (should-not (has-attr? node :alt))))

(describe "filter-nodes-by-attr"
  (it "filters nodes by attribute presence"
    (should=
      [{:tag :img :attrs {:src "2.png"}}]
      (filter-nodes-by-attr
        [{:tag :img} {:tag :img :attrs {:src "2.png"}}] :src))))

(describe "filter-bass64-imgs"
  (it "filters node marked as data-bass64"
    (should=
      [{:tag :img :attrs {:data-bass64 "1.png" :src "1.png"} :content nil}
       {:tag :img :attrs {:data-bass64 "3.png" :src "3.png"} :content nil}]
      (filter-bass64-imgs
        [{:tag :img :attrs {:data-bass64 "1.png" :src "1.png"} :content nil}
         {:tag :img :attrs {:src "2.png"} :content nil}
         {:tag :img :attrs {:data-bass64 "3.png" :src "3.png"} :content nil}]))))

(run-specs)
