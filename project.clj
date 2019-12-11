(defproject BYU-ODH/sem "1"
  :description "BYU Semester helpers"
  :url "https://github.com/BYU-ODH/sem"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clojure.java-time "0.3.2"]
                 [org.clojure/clojurescript "1.10.520" :scope "provided"]
                 [byu-odh/byu-ws "0.1.6"]]
  :resource-paths ["target/cljsbuild"]
  :source-paths ["src"]
  :repositories [["releases" {:url "https://repo.clojars.org"
                              :creds :gpg}]]
  :target-path "target/"
  :plugins [[lein-cljsbuild "1.1.7"]]
  :cljsbuild
  {:builds
   {:app
    {:source-paths ["src"]
     :compiler
     {:main "sem.core"
      :asset-path "/js/out"
      :output-to "target/cljsbuild/public/js/app.js"
      :output-dir "target/cljsbuild/public/js/out"
      :optimizations :advanced}}
    :min
    {:source-paths ["src"]
     :compiler
     {:output-to "target/cljsbuild/public/js/app.js"
      :output-dir "target/uberjar"
      :externs ["react/externs/react.js"]
      :optimizations :advanced
      :pretty-print false
      :closure-warnings
      {:externs-validation :off :non-standard-jsdoc :off}}}}}
  :repl-options {:init-ns sem.core})
