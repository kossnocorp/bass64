(defproject bass64 "0.1.0"
  :description ""
  :url "https://github.com/kossnocorp/bass64"
  :main bass64.builder
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [enlive "1.1.5"]]
  :profiles {:dev {:dependencies [[speclj "3.0.2"]]}}
  :plugins [[speclj "3.0.2"]]
  :test-paths ["spec"])
