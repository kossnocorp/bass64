(ns bass64.builder-spec
  (:require [speclj.core :refer :all]
            [clojure.data.json :as json]
            [bass64.spec-helper :refer :all]
            [bass64.builder :refer :all]))

(describe "path-relative-to-root?"
  (it "returns true if path is relative to root"
    (should (path-relative-to-root? "/assets/1.png"))
    (should-not (path-relative-to-root? "./1.png"))
    (should-not (path-relative-to-root? "1.png"))))

(describe "normalize-url"
  (it "returns absolute image url for given src and page url"
    (should=
      "http://www.toptal.com/assets/1.png"
      (normalize-url "http://www.toptal.com/assets/1.png" "http://www.toptal.com/clients"))
    (should=
      "http://www.toptal.com/assets/1.png"
      (normalize-url "/assets/1.png" "http://www.toptal.com/clients"))
    (should=
      "http://localhost:3000/assets/1.png"
      (normalize-url "/assets/1.png" "http://localhost:3000/clients"))))
    ; TODO:
    ;(should=
      ;"http://www.toptal.com/assets/1.png"
      ;(normalize-url "./../assets/1.png" "http://www.toptal.com/clients"))
    ;(should=
      ;"http://www.toptal.com/assets/1.png"
      ;(normalize-url "./1.png" "http://www.toptal.com/assets"))
    ;(should=
      ;"http://www.toptal.com/assets/1.png"
      ;(normalize-url "1.png" "http://www.toptal.com/assets"))))

(describe "bass64-map-with-url"
  (it "assoc url to bass64 map"
    (should=
      {:id "3" :src "/assets/3.png" :url "http://www.toptal.com/assets/3.png"}
      (bass64-map-with-url {:id "3" :src "/assets/3.png"} "http://www.toptal.com/clients"))))

(describe "bass64-maps-with-urls"
  (it "assoc urls to every bass64 map"
    (should=
      [{:id "1" :src "/assets/1.png" :url "http://www.toptal.com/assets/1.png"}
       {:id "3" :src "/assets/3.png" :url "http://www.toptal.com/assets/3.png"}]
      (bass64-maps-with-urls
        [{:id "1" :src "/assets/1.png"}
         {:id "3" :src "/assets/3.png"}]
        "http://www.toptal.com/clients"))))

(describe "bass64-map-with-base64"
  (it "assos base64 to bass64 map"
    (def fixture-image-path (fixture-path "1.png"))
    (should=
      {:id "3" :src "/assets/3.png" :url fixture-image-path :base64 "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAADUExURf///6fEG8gAAAAKSURBVAjXY2AAAAACAAHiIbwzAAAAAElFTkSuQmCC"}
      (bass64-map-with-base64 {:id "3" :src "/assets/3.png" :url fixture-image-path}))))

(describe "bass64-maps-with-base64"
  (it "assos base64 to bass64 map"
    (def fixture-image-path (fixture-path "1.png"))
    (should=
      [{:id "1" :src "/assets/1.png" :url fixture-image-path :base64 "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAADUExURf///6fEG8gAAAAKSURBVAjXY2AAAAACAAHiIbwzAAAAAElFTkSuQmCC"}
       {:id "3" :src "/assets/3.png" :url fixture-image-path :base64 "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAADUExURf///6fEG8gAAAAKSURBVAjXY2AAAAACAAHiIbwzAAAAAElFTkSuQmCC"}]
      (bass64-maps-with-base64
        [{:id "1" :src "/assets/1.png" :url fixture-image-path}
         {:id "3" :src "/assets/3.png" :url fixture-image-path}]))))

(describe "bass64-maps-as-json-map"
  (it "returns map formatted for json builder"
    (should=
      {"1" "123" "3" "456"}
      (bass64-maps-as-json-map
        [{:id "1" :src "/assets/1.png" :url "http://www.toptal.com/assets/1.png" :base64 "123"}
         {:id "3" :src "/assets/3.png" :url "http://www.toptal.com/assets/3.png" :base64 "456"}]))))

(describe "build-json-from-bass64-maps"
  (it "builds json from bass64 maps"
    (def decoded-json-map
      (json/read-str
        (build-json-from-bass64-maps
          [{:id "1" :src "/assets/1.png" :url "http://www.toptal.com/assets/1.png" :base64 "123"}
           {:id "3" :src "/assets/3.png" :url "http://www.toptal.com/assets/3.png" :base64 "456"}])))
    (should= {"1" "123" "3" "456"} decoded-json-map)))

(run-specs)
