(defproject codox-md "0.1.1-SNAPSHOT"
  :description "Templated codox output with markdown processing."
  :dependencies [[org.markdownj/markdownj "0.3.0-1.0.2b4"]
                 [enlive "1.0.0"]]
  :profiles {:dev {:dependencies [[codox/codox.core "0.6.4"]]}}
  :codox {:writer codox-md.writer/write-docs})
