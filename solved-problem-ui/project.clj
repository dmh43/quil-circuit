(defproject solved-problem-ui "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.456"]]
  :profiles {:dev {:dependencies [[figwheel-sidecar "0.5.8"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.10"]
                                  [quil "2.6.0"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}
  :cljsbuild {:builds [{:id "dev",
                        :source-paths ["src"],
                        :figwheel {:on-jsload "solved-problem-ui.core/fig-reload"},
                        :compiler {:main "solved-problem-ui.core",
                                   :asset-path "js/out"
                                   :output-to "resources/public/js/main.js",
                                   :output-dir "resources/public/js/out",
                                   :optimizations :none,
                                   :source-map true,
                                   :source-map-timestamp true,
                                   :recompile-dependents false}}]}
  :figwheel {:http-server-root "public" ;; this will be in resources/
             :server-port 3449          ;; default is 3449
             :server-ip   "localhost"     ;; default is "localhost"

             ;; CSS reloading (optional)
             ;; :css-dirs has no default value
             ;; if :css-dirs is set figwheel will detect css file changes and
             ;; send them to the browser
             :css-dirs ["resources/public/css"]

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server
             ;; :ring-handler example.server/handler

             ;; Clojure Macro reloading
             ;; disable clj file reloading
                                        ; :reload-clj-files false
             ;; or specify which suffixes will cause the reloading
                                        ; :reload-clj-files {:clj true :cljc false}

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path, a line number and a column
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2:$3 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Load CIDER, refactor-nrepl and piggieback middleware
             :nrepl-middleware ["cider.nrepl/cider-middleware"
                                "refactor-nrepl.middleware/wrap-refactor"
                                "cemerick.piggieback/wrap-cljs-repl"]

             ;; if you need to watch files with polling instead of FS events
             ;; :hawk-options {:watcher :polling}
             ;; ^ this can be useful in Docker environments

             ;; if your project.clj contains conflicting builds,
             ;; you can choose to only load the builds specified
             ;; on the command line
             ;; :load-all-builds false ; default is true
             }
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :plugins [[lein-npm "0.6.1"]
            [lein-figwheel "0.5.10"]]
  :npm {:dependencies [[source-map-support "0.4.0"]]}
  :source-paths ["src" "target/classes"]
  :clean-targets ["out" "release"]
  :target-path "target")
