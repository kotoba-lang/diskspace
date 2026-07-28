(ns kotoba.lang.diskspace.host-test
  (:require [clojure.test :refer [deftest is testing]]
            [kotoba.lang.diskspace.host :as host])
  (:import [java.io File]))

(deftest path-component-boundaries
  (testing "recognized directory components require an end or slash boundary"
    (is (host/path-component-host? "/repo/target" "/target"))
    (is (host/path-component-host? "/repo/target/debug" "/target"))
    (is (not (host/path-component-host? "/repo/targeted" "/target")))))

(deftest tracked-path-membership
  (let [repo (File. "/repo")
        tracked #{"src/main.clj" "target/important.txt"}]
    (is (host/tracked-path? repo tracked (File. "/repo/src")))
    (is (host/tracked-path? repo tracked (File. "/repo/target")))
    (is (not (host/tracked-path? repo tracked (File. "/repo/node_modules"))))))

(deftest ordinary-two-element-vectors-are-not-pairs
  (let [value [{:path "one"} {:path "two"}]]
    (is (= value (host/normalize-edn value)))))
