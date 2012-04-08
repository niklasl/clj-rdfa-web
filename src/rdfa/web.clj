(ns rdfa.web
  (:use compojure.core
        [ring.adapter.jetty :only [run-jetty]])
  (:require [clojure.string :as string]
            [ring.util.response :as resp]
            [compojure.route :as route]
            [compojure.handler :as handler])
  (:require [rdfa.parser :as parser]
            [rdfa.adapter.jena :as jena]))

(defroutes main-routes
           (GET "/" []
                (resp/resource-response "index.html" {:root "public"}))
           (GET "/extract.:ext" [ext url rdfagraph vocab_expansion]
                (let [{triples :triples
                       proc-triples :proc-triples} (parser/get-rdfa url)
                      result-triples (if (= rdfagraph "processor")
                                       proc-triples
                                       triples)
                      model (jena/triples-to-model result-triples)
                      model (if (= vocab_expansion "true")
                              (jena/expand-vocab model)
                              model)
                      bos (doto (java.io.ByteArrayOutputStream.)
                            (#(.write model % "TURTLE")))
                      turtle-result (.toString bos "utf-8")
                      mime-type (if (= ext "txt") "text/plain" "text/turtle")]
                  {:status 200
                   :headers {"Content-Type" (str mime-type "; charset=utf-8")}
                   :body turtle-result}))
           (route/resources "/")
           (route/not-found "Not Found"))

(def app (handler/site main-routes))

(defn -main [port]
  (let [port (Integer. port)]
    (run-jetty app {:port port})))

