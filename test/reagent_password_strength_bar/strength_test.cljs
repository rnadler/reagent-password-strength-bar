(ns reagent-password-strength-bar.strength-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            ;;[clojure.string :as s]
            [reagent-password-strength-bar.strength :as s]))

(deftest test-strength-to-color
  (testing "Strength to color"
    (is (= (s/strength-to-color -1)   "#F00"))
    (is (= (s/strength-to-color 0)    "#F00"))
    (is (= (s/strength-to-color 1)    "#F90"))
    (is (= (s/strength-to-color 5)    "#0F0"))
    (is (= (s/strength-to-color 100)  "#0F0"))
    ))

(deftest test-strength-to-label
  (testing "Strength to label"
    (is (= (s/strength-to-label -1)   "(Useless)"))
    (is (= (s/strength-to-label 0)    "(Useless)"))
    (is (= (s/strength-to-label 1)    "(Weak)"))
    (is (= (s/strength-to-label 2)    "(Normal)"))
    (is (= (s/strength-to-label 3)    "(Strong)"))
    (is (= (s/strength-to-label 4)    "(Great!)"))
    (is (= (s/strength-to-label 100)  "(Great!)"))
    ))

(deftest test-get-strength
  (testing "Get strength"
    (is (= (s/get-strength 95)  5))
    (is (= (s/get-strength 88)  4))
    (is (= (s/get-strength 55)  3))
    (is (= (s/get-strength 31)  2))
    (is (= (s/get-strength 10)  1))
    (is (= (s/get-strength -10) 1))
    ))

(deftest test-variation-count
  (testing "Variation count"
    (is (= (s/variation-count "") 0))
    (is (= (s/variation-count "sddddDDD") 2))
    (is (= (s/variation-count "sddddDDD3") 3))
    (is (= (s/variation-count "DDD3") 2))
    (is (= (s/variation-count "#$!") 1))
    (is (= (s/variation-count "sddddDDDewx22!") 4))
    (is (= (s/variation-count "JfAAE%FC@r6&ARbM") 4))
    ))

(deftest test-count-score
  (testing "Count score"
    (is (= (s/count-score "") 0))
    (is (= (Math/floor (s/count-score "asfs")) 17))
    (is (= (Math/floor (s/count-score "sddddDDDewx22!")) 52))
    (is (= (Math/floor (s/count-score "JfAAE%FC@r6&ARbM")) 74))
    ))

(deftest test-measure-strength
  (testing "Measure strength"
    (is (= (s/measure-strength "sddddDDDewx22!") 82))
    (is (= (s/measure-strength "JfAAE%FC@r6&ARbM") 104))
    ))

(deftest test-password-strength
  (testing "Password strength"
    (is (= (s/password-strength "sddddDDDewx22!") 3))
    (is (= (s/password-strength "JfAAE%FC@r6&ARbM") 4))
    ))

