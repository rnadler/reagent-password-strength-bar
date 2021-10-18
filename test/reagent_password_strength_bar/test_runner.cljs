(ns reagent-password-strength-bar.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [reagent-password-strength-bar.strength-test]))

(enable-console-print!)

;; Command line: lein doo node test once
;; Installing node: https://github.com/nodesource/distributions/blob/master/README.md#debinstall
;; v16.11.1 [17-Oct-21]
(doo-tests 'reagent-password-strength-bar.strength-test)
