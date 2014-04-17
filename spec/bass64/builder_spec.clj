(ns bass64.builder-spec
  (:require
    [speclj.core :refer :all]
    [bass64.builder :refer :all]))

(describe "path-relative-to-root?"
  (it "returns true if path is relative to root"
    (should (path-relative-to-root? "/assets/1.png"))
    (should-not (path-relative-to-root? "./1.png"))
    (should-not (path-relative-to-root? "1.png"))))

(describe "resolve-url"
  (it "returns absolute image url for given src and page url"
    (should=
      "http://www.toptal.com/assets/1.png"
      (resolve-url "http://www.toptal.com/assets/1.png" "http://www.toptal.com/clients"))
    (should=
      "http://www.toptal.com/assets/1.png"
      (resolve-url "/assets/1.png" "http://www.toptal.com/clients"))))
    ; TODO:
    ;(should=
      ;"http://www.toptal.com/assets/1.png"
      ;(resolve-url "./../assets/1.png" "http://www.toptal.com/clients"))
    ;(should=
      ;"http://www.toptal.com/assets/1.png"
      ;(resolve-url "./1.png" "http://www.toptal.com/assets"))
    ;(should=
      ;"http://www.toptal.com/assets/1.png"
      ;(resolve-url "1.png" "http://www.toptal.com/assets"))))

(run-specs)
