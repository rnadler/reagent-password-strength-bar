(ns reagent-password-strength-bar.app
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]
   [reagent-password-strength-bar.core :refer [password-strength-bar]]))

;; -------------------------
;; Views

(defn input-element
  "An input element which updates its value and on focus parameters on change, blur, and focus"
  [id name type value in-focus]
  [:input {:id id
           :name name
           :class "form-control"
           :type type
           :required ""
           :value @value
           :on-change #(reset! value (-> % .-target .-value))
           :on-focus #(swap! in-focus not)
           :on-blur (fn [_] (when (nil? @value) (reset! value "")) (swap! in-focus not))}])

(defn input-and-prompt
  "Creates an input box and a prompt box that appears above the input when the input comes into focus. Also throws in a little required message"
  [label-value input-name input-type input-element-arg]
  (let [input-focus (atom false)]
    (fn []
      [:div
       [:label label-value]
       [input-element input-name input-name input-type input-element-arg input-focus]])))

(defn password-form
  [password]
  (let [password-type-atom (atom "password")]
    (fn []
      [:div
       [(input-and-prompt "Password"
                          "password"
                          @password-type-atom
                          password)]
       [password-strength-bar password]])))

(defn wrap-as-element-in-form
  [element]
  [:div {:class="row form-group"}
   element])

(defn home-page []
  (let [password (atom nil)]
    (fn []
      [:div {:class "signup-wrapper"}
       [:form (wrap-as-element-in-form [password-form password])]])))

(defn ^:export run []
  (rdom/render [home-page] (js/document.getElementById "app")))
