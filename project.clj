(defproject reagent-password-strength-bar "1.0.0"
  :description "Reagent Password Strength Bar"
  :url "https://github.com/rnadler/reagent-password-strength-bar"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.9.1"

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/clojurescript "1.10.891"]
                 [org.clojure/core.async  "1.4.627"]
                 [reagent "1.1.0"]
                 [reagent-utils "0.3.4"]
                 [cljsjs/react "17.0.2-0"]
                 [cljsjs/react-dom "17.0.2-0"]
                 [clj-commons/cljss "1.6.4"]]

  :plugins [[lein-figwheel "0.5.20"]
            [lein-cljsbuild "1.1.8" :exclusions [[org.clojure/clojure]]]
            [lein-doo "0.1.11"]]

  :source-paths ["src"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]

                ;; The presence of a :figwheel configuration here
                ;; will cause figwheel to inject the figwheel client
                ;; into your build
                :figwheel {;;:on-jsload "reagent-password-strength-bar.core/on-js-reload"
                           ;; :open-urls will pop open your application
                           ;; in the default browser once Figwheel has
                           ;; started and compiled your application.
                           ;; Comment this out once it no longer serves you.
                           :open-urls ["http://localhost:3449/index.html"]
                           :on-jsload "reagent-password-strength-bar.app/run"}

                :compiler {:main reagent-password-strength-bar.app
                           :optimizations :none
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/reagent_password_strength_bar.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           ;; To console.log CLJS data-structures make sure you enable devtools in Chrome
                           ;; https://github.com/binaryage/cljs-devtools
                           :preloads [devtools.preload]}}
               ;; This next build is a compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/reagent_password_strength_bar.js"
                           :main reagent-password-strength-bar.core
                           :optimizations :advanced
                           :pretty-print false}}
               {:id "test"
                :source-paths ["src" "test"]
                :compiler {:main reagent-password-strength-bar.test-runner
                           :output-to "resources/public/js/reagent_password_strength_bar_test.js"
                           :output-dir "resources/public/js/compiled/test-out"
                :optimizations :none
                :target :nodejs}}
               ]}

  :figwheel {;; :http-server-root "public" ;; default and assumes "resources"
             ;; :server-port 3449 ;; default
             ;; :server-ip "127.0.0.1"

             :css-dirs ["resources/public/css"] ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this

             ;; doesn't work for you just run your own server :) (see lein-ring)

             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you are using emacsclient you can just use
             ;; :open-file-command "emacsclient"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"

             ;; to pipe all the output to the repl
             ;; :server-logfile false
             }

  :profiles {:dev {:dependencies [[binaryage/devtools "1.0.4"]
                                  [figwheel-sidecar "0.5.20"]]
                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths ["src" "dev" "test"]
                   ;; need to add the compiled assets to the :clean-targets
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}
             :uberjar {:source-paths ["src"]
                       :prep-tasks ["compile" ["cljsbuild" "once" "min"]]
                       :aot :all
                       :omit-source true}})
