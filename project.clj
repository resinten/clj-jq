(defproject me.bwest/clj-jq "1.0.0"
  :description "A simple jq query engine for Clojure"
  :url "github.com/BrianMWest/clj-jq"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.6"]
                 [com.arakelian/java-jq "0.10.1"]]
  :repl-options {:init-ns clj-jq.core})
