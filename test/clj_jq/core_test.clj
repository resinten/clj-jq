(ns clj-jq.core-test
  (:require [clojure.test :refer :all]
            [clj-jq.core :as jq]))

(def json-str "{\"a\": 2, \"foo\": \"bar\", \"baz\": {\"x\": 10, \"y\": \"20\"}}")

(deftest can-parse-json-test
  (testing "Can parse JSON documents"
    (is (= {:a 2 :foo "bar" :baz {:x 10 :y "20"}} (jq/read json-str)))))

(deftest can-query-json-test
  (testing "Can query parsed JSON documents"
    (is (= 2 (jq/query (jq/prepare json-str) ".a")))
    (is (= "bar" (jq/query (jq/prepare json-str) ".foo")))
    (is (= {:x 10 :y "20"} (jq/query (jq/prepare json-str) ".baz")))))

(deftest can-query-json-strings-test
  (testing "Can query raw JSON string documents"
    (is (= 2 (jq/query json-str ".a")))
    (is (= "bar" (jq/query json-str ".foo")))
    (is (= {:x 10 :y "20"} (jq/query json-str ".baz")))))

(deftest can-get-multiple-values-test
  (testing "Can get multiple values from the same document"
    (let [doc (jq/prepare json-str)
          a (jq/query doc ".a")
          foo (jq/query doc ".foo")
          x (jq/query doc ".baz.x")]
      (is (= 2 a))
      (is (= "bar" foo))
      (is (= 10 x)))))

(deftest can-get-complex-record-test
  (testing "Can get complex value from a single document"
    (is (= {:x 10 :bar "bar"} (jq/query json-str "{\"x\": .baz.x, \"bar\": .foo}")))))

(deftest nonexistent-values-are-nil-test
  (testing "Nonexistent values default to nil"
    (is (= nil (jq/query "{}" ".testing")))))
