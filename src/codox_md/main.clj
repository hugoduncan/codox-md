(ns codox-md.main
  "Main namespace for generating documentation using markdown."
  (:use
   [codox.utils :only [ns-filter]]
   [codox.reader :only [read-namespaces]]
   [codox-md.writer :only [write-docs]]))

(defn generate-docs
  "Generate documentation using markdown from source files."
  ([]
     (generate-docs {}))
  ([{:keys [sources include exclude] :as options}]
     (let [namespaces (-> (apply read-namespaces sources)
                          (ns-filter include exclude))]
       (write-docs (assoc options :namespaces namespaces)))))
