# clj-jq

[![Clojars Project](https://img.shields.io/clojars/v/me.bwest/clj-jq.svg)](https://clojars.org/me.bwest/clj-jq)

A Clojure library designed to make working with JSON objects simpler and faster.
This wraps the `jq` native library that is provided by com.arakelian/java-jq.

### Leiningen

```[me.bwest/clj-jq "1.0.0"]```

### Maven

```
<dependency>
  <groupId>me.bwest</groupId>
  <artifactId>clj-jq</artifactId>
  <version>1.0.0</version>
</dependency>
```


## Usage

Very simply, 4 methods are provided

```
(require '[clj-jq.core :as jq])

(def json-str "{\"a\": 2, \"foo\": \"bar\", \"baz\": {\"x\": 10, \"y\": \"20\"}}")

(let [doc (jq/prepare json-str)
      a (jq/query doc ".a")
      foo (jq/query doc ".foo")
      x (jq/query doc ".baz.x")]
  (is (= 2 a))
  (is (= "bar" foo))
  (is (= 10 x)))
```

You can alias a `jq` request with `jq/prepare` and then query it multiple times.
Or you can simply call `jq/query` directly on the string and it will prepare the document
behind the scenes.

Note that preparing does not give a performance advantage here because this simply calls
the `jq` library with each request execution.
Some work could be done to take in a map of requests and parse it, but I haven't taken the time
to do that. You could do that directly with the `jq` query string, though.

This also exposes `jq/read` which is just an alias of `(clojure.data.json/read-str x :key-fn keyword)`
and `jq/write`, which just aliases `clojure.data.json/write-str`

If you want to map an incoming JSON message to some consistently structured map, `jq` can do that:

```
(jq/query json-str "{\"id\": .inbound.payload.messageId, \"url\": .inbound.payload.docUrl, \"date\": .metadata.originDate}")
```

This will yield a map with the structure:

```
{:id <id> :url <url> :date <date>}
```

Values can then be mapped further into specialized data types using normal Clojure functions.
