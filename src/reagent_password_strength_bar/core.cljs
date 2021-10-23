(ns reagent-password-strength-bar.core
  (:require-macros [cljss.core])
  (:require [cljss.core :as css :refer-macros [defstyles]]
            [reagent-password-strength-bar.strength :as s]))

(def bar-style
  {:border-radius  "2px"
   :background-size "35px 20px, 100% 100%, 100% 100%"})

(defstyles password-strength-meter-class
  []
  {:text-align "left" })

(defstyles password-strength-meter-progress
  []
  {:-webkit-appearance "none"
   ;;:-moz-appearance "none"
   :appearance "none"
   :width  s/default-width
   :height "8px"
   &::-webkit-progress-bar
   {:background-color s/default-base-color
    :border-radius  "3px" }
   &::-webkit-progress-value bar-style
   ;;   &::-moz-progress-bar bar-style
   })

(defstyles password-strength-meter-label
  []
  {:font-size "14px"})

(defstyles strength-bar-color
  [strength]
  {& {:background-color (s/strength-to-color strength)}  ;; IE
   &::-webkit-progress-value {:background-color (s/strength-to-color strength)} ;; Chrome/Safari
   &::-moz-progress-bar {:background-color (s/strength-to-color strength)} ;; Firefox
   })

(defn password-strength-bar
  "Password strength bar component"
  [password]
  (let [strength (s/password-strength @password)]
    [:div {:class (password-strength-meter-class)}
     [:small s/default-bar-label]
     [:progress {:max s/max-strength
                 :value strength
                 :class [(password-strength-meter-progress)
                         (strength-bar-color (- strength 1))] }]
     [:br]
     [:small {:class (password-strength-meter-label)}
       (s/strength-to-label strength) ]
   ]))
