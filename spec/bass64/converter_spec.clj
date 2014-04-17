(ns bass64.converter-spec
  (:require [speclj.core :refer :all]
            [clojure.string :as string]
            [bass64.spec-helper :refer :all]
            [bass64.converter :refer :all]))

(describe "convert-to-base64"
  (it "returns base64 string for given file path"
    (should=
      (string/trim-newline (load-fixture-string "image.base64"))
      (convert-to-base64 (fixture-path "image.png")))))

(run-specs)
