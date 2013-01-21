(defproject codox-md "0.1.1-SNAPSHOT"
  :description "Templated codox output with markdown processing."
  :dependencies [[org.markdownj/markdownj "0.3.0-1.0.2b4"]
                 [enlive "1.0.0"]]
  :dev-dependencies [[codox "0.4.0"]]
  :codox {:writer codox-md.writer/write-docs})
