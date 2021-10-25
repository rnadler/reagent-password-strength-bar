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
   :width  @s/width
   :height "8px"
   &::-webkit-progress-bar
   {:background-color @s/base-color
    :border-radius  "3px" }
   &::-webkit-progress-value bar-style
   ;;   &::-moz-progress-bar bar-style
   })

(defstyles strength-bar-color
  [strength]
  {& {:background-color (s/strength-to-color strength)}  ;; IE
   &::-webkit-progress-value {:background-color (s/strength-to-color strength)} ;; Chrome/Safari
   &::-moz-progress-bar {:background-color (s/strength-to-color strength)} ;; Firefox
   })

(defn password-strength-bar
  "Password strength bar component"
  [password & {:keys [bar-label strength-labels colors thresholds base-color width]
               :or {bar-label s/default-bar-label
                    strength-labels s/default-strength-labels
                    colors s/default-colors
                    thresholds s/default-thresholds
                    base-color s/default-base-color
                    width s/default-width}}]
  (reset! s/bar-label bar-label)
  (reset! s/strength-labels strength-labels)
  (reset! s/colors colors)
  (reset! s/thresholds thresholds)
  (reset! s/base-color base-color)
  (reset! s/width width)
  (fn []
    (let [strength (s/password-strength @password)]
      [:div {:class (password-strength-meter-class)}
       [:small (str @s/bar-label " ")]
       [:progress {:max (- s/max-strength 1)
                   :value strength
                   :class [(password-strength-meter-progress)
                           (strength-bar-color (- strength 1))] }]
       [:small (str " " (s/strength-to-label strength))]])))
