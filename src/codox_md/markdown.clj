(ns codox-md.markdown
  "Markdown translation"
  (:import [com.petebevin.markdown MarkdownProcessor]))

(def ^{:no-doc true :doc "Markdown processor"}
  processor (delay (MarkdownProcessor.)))

(defn md
  "Convert a Markdown string to HTML.
       (md \"## This is a h2\")"
  [s]
  (.markdown @processor s))
