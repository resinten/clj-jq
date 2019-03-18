(ns clj-jq.core
  (:require [clojure.data.json :as json]))

(def native-jq-library (com.arakelian.jq.ImmutableJqLibrary/of))
(def native-jq-builder (.lib (com.arakelian.jq.ImmutableJqRequest/builder) native-jq-library))

(defn prepare
  "Parse JSON string for future jq queries"
  [json-str]
  (.input native-jq-builder json-str))

(defn read
  "Parse JSON string into a keyword-based map"
  [json-str]
  (json/read-str json-str :key-fn keyword))

(defn write
  "Write JSON to string"
  [json]
  (json/write-str json))

(defn query
  "Take parsed JSON and query it against a jq query"
  [json query-str]
  (if (instance? com.arakelian.jq.ImmutableJqRequest$Builder json)
    (let [result (.execute (.build (.filter json query-str)))]
      (if (.hasErrors result) nil (read (.getOutput result))))
    (query (prepare json) query-str)))
