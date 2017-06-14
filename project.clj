(defproject story-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.0"]
                 [eigenhombre/namejen "0.1.12"]
                 [com.novemberain/monger "3.0.2"]
                 [me.raynes/conch "0.8.0"]
                 [ring/ring-defaults "0.2.0"]
                 [ring/ring-json "0.4.0"]
                 [ring "1.4.0"]
                 [tropic "0.9.9"]
                 ]
  :main ^:skip-aot story-server.core
  :target-path "target/%s"
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler story-server.handler/app}
  :profiles {
             :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.3.0"]]}
             })
