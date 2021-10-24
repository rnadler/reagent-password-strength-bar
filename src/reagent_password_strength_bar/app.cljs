(ns reagent-password-strength-bar.app
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]
   [reagent-password-strength-bar.core :refer [password-strength-bar]]))

(defn input-element
  "The password input element which updates its value"
  [password]
  [:input {:id "password"
           :class "form-control"
           :type "password"
           :value @password
           :on-change #(reset! password (-> % .-target .-value))}])

(defn password-form
  [password]
  (fn []
    [:div
     [:label "Password"]
     [input-element password]
     [password-strength-bar password]]))

(defn home-page []
  (let [password (atom nil)]
    (fn []
      [:div {:class "signup-wrapper"}
       [:form [password-form password]]])))

(defn ^:export run []
  (rdom/render [home-page] (js/document.getElementById "app")))
