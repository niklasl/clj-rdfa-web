(ns rdfa.dev
  (:require [clojure.java.io :as io]
            [ring.server.options :as options]
            [ring.middleware.reload :as reload]
            [rdfa.web :as web]))

; NOTE: although lein-ring uses ring-server which adds wrap-reload if dev-env?,
; this is needed to also pick up lein "checkouts".
(def app
  (if options/dev-env?
    (let [dirs (for [f (.listFiles (io/file "checkouts"))
                     :when (.isDirectory f)]
                 (.getPath (io/file f "src")))]
      (reload/wrap-reload web/app {:dirs dirs}))
    web/app))

