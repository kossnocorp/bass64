(ns bass64.converter
  (:require [clojure.data.codec.base64 :as b64]
            [me.raynes.fs :as fs]
            [clojure.java.io :as io]))

(defn convert-to-base64
  [path]
  (let
    [temp-file (fs/temp-file "bass64")]
    (with-open
      [in (io/input-stream path)
       out (io/output-stream temp-file)]
      (b64/encoding-transfer in out))
    (slurp temp-file)))
