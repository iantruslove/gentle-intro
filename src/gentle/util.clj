(ns gentle.util
  (:import (java.util UUID)
           (java.io InputStream
                    InputStreamReader
                    PushbackReader
                    Reader)))

(defn uuid
  ([] (UUID/randomUUID))
  ([s] (UUID/fromString s)))

(defn pbr
  "Creates a PushbackReader from an InputStream. Charset defaults to
  UTF-8."
  ([in-stream charset]
   (-> in-stream
       (InputStreamReader. charset)
       (PushbackReader.)))
  ([^InputStream in-stream]
   (pbr in-stream "UTF-8")))
