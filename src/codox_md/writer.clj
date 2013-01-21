(ns codox-md.writer
  "Output of codox documentation using markdown."
  (:use
   [codox-md.markdown :only [md]]
   [net.cgrand.enlive-html
    :only [add-class at clone-for content do-> html-content html-resource
           set-attr template transform transformation]])
  (:require
   [clojure.java.io :as io]
   [codox.writer.html :as html-writer]))

(def ns-template (html-resource "codox/ns_template.html"))
(def index-template (html-resource "codox/index_template.html"))

(defn ns-link [namespace]
  (transformation
   [:.ns-name] (html-content (:name namespace))
   [:.ns-link] (set-attr :href (#'html-writer/ns-filename namespace))))

(defn ns-var-link [namespace v]
  (transformation
   [:.ns-var-name] (html-content (:name v))
   [:.ns-var-link] (set-attr :href (#'html-writer/var-uri namespace v))))

(def namespace-page
  (template
   "codox/ns_template.html" [project namespace]
   [:title] (html-content (#'html-writer/namespace-title namespace))
   [:.project-title] (content (#'html-writer/project-title project))
   [:.ns-links :.ns-list] (clone-for
                           [namespace (:namespaces project)]
                           (ns-link namespace))
   [:.ns-var-links :.ns-var-list] (clone-for
                                   [v (:publics namespace)]
                                   (ns-var-link namespace v))
   [:.namespace-title] (html-content (#'html-writer/namespace-title namespace))
   [:.namespace-docs :.doc] (html-content (md (:doc namespace)))
   [:.public] (clone-for
               [v (:publics namespace)]
               [:.var-name] (do->
                             (html-content (:name v))
                             (set-attr :id (#'html-writer/var-id v)))
               [:.source] (set-attr
                           :href (#'html-writer/var-source-uri
                                  (:src-dir-uri project)
                                  v
                                  (:src-linenum-anchor-prefix project)))
               [:.var-type] (add-class (cond
                                        (:macro v) "macro"
                                        (:arglists v) "fn"
                                        :else "var"))
               [:#var-type] (html-content (cond
                                           (:macro v) "macro"
                                           (:arglists v) "fn"
                                           :else "var"))
               [:.doc] (html-content (md (:doc v)))
               [:.usage] (clone-for
                          [arg-list (:arglists v)]
                          (html-content arg-list))
               [:.added] (when-let [version (:added v)]
                           #(at % [:.version] (html-content version)))
               [:.deprecated] (when-let [version (:deprecated v)]
                                #(at % [:.version] (html-content version))))))

(def index-page
  (template
   "codox/index_template.html" [project]
   [:title] (content (#'html-writer/project-title project))
   [:.project-title] (html-content (#'html-writer/project-title project))
   [:.doc] (html-content (md (:description project)))
   [:.ns-links :.ns-link] (clone-for
                           [namespace (:namespaces project)]
                           (ns-link namespace))
   [:.namespace] (clone-for
                  [namespace (:namespaces project)]
                  [:.ns-link] (ns-link namespace)
                  [:.doc] (html-content (md (:doc namespace)))
                  [:.ns-var-list] (clone-for
                                   [v (:publics namespace)]
                                   (ns-var-link namespace v)))))

(defn- write-index [output-dir project]
  (spit (io/file output-dir "index.html") (apply str (index-page project))))

(defn- write-namespaces [output-dir project]
  (doseq [namespace (:namespaces project)]
    (spit (#'html-writer/ns-filepath output-dir namespace)
          (apply str (namespace-page project namespace)))))

(defn write-docs
  "Take raw documentation info and turn it into formatted HTML."
  [project]
  (doto (:output-dir project "doc")
    (#'html-writer/mkdirs "css" "js")
    (#'html-writer/copy-resource
     "codox/css/default.css" "css/default.css")
    (#'html-writer/copy-resource
     "codox/css/codox-md.css" "css/codox-md.css")
    (#'html-writer/copy-resource
     "codox/js/jquery.min.js" "js/jquery.min.js")
    (#'html-writer/copy-resource
     "codox/js/page_effects.js" "js/page_effects.js")
    (write-index project)
    (write-namespaces project))
  nil)
