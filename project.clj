(defproject
  rdfa/rdfa-web "1.0.0-SNAPSHOT"
  :description "Web interface for the Clojure RDFa library"
  :url "https://github.com/niklasl/clj-rdfa-web"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [rdfa/rdfa "0.5.0-SNAPSHOT"]
                 [compojure "1.0.1"]
                 [ring/ring-core "1.0.2"]
                 [ring/ring-jetty-adapter "1.0.1"]
                 [ring/ring-devel "1.0.1"]]
  :dev-dependencies [[lein-ring "0.6.3"]]
  :ring {:handler rdfa.dev/app})
