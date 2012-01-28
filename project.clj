(defproject codox-md "0.1.0-SNAPSHOT"
  :description "Templated codox output via markdown"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.markdownj/markdownj "0.3.0-1.0.2b4"]
                 [enlive "1.0.0"]]
  :dev-dependencies [[org.clojure/clojure "1.3.0-lc"]
                     [codox "0.3.5-SNAPSHOT"]]
  :codox {:writer codox-md.writer/write-docs})
