(ns reagent-password-strength-bar.app
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]
   [reagent-password-strength-bar.core :refer [password-strength-bar]]
   [reagent-password-strength-bar.strength :as s]))

(defn input-element
  "The password input element which updates its value"
  [password]
  [:input {:id "password"
           :name "password"
           :class "form-control"
           :placeholder "Enter password"
           :type "password"
           :value @password
           :on-change #(reset! password (-> % .-target .-value))}])

(defn password-form
  [password]
  (fn []
    [:div
     [:label "Password"]
     [input-element password]
     [password-strength-bar password
      :bar-label "PW Strength:"
      :strength-labels ["(Yuk)" "(Aweful)" "(OK)" "(Above Average)" "(Marvolous!)"]
      :colors ["#DD2C00", "#FF6D00", "#FFD600", "#AEEA00", "#00C853"]
      :thresholds  [90, 75, 45, 25]
      :base-color "#BBB"
      :width "325px"
      ]]))

(defn home-page []
  (let [password (atom nil)]
    (fn []
      [:div {:class "signup-wrapper"}
       [:form [password-form password]]])))

(defn ^:export run []
  (rdom/render [home-page] (js/document.getElementById "app")))
