(ns reagent-password-strength-bar.strength)

(def max-strength 5)
(def default-base-color  "#DDD")
(def default-bar-label "Password strength:")
(def default-colors [ "#F00" "#F90" "#FF0" "#9F0" "#0F0"])
(def default-strength-labels ["(Useless)" "(Weak)" "(Normal)" "(Strong)" "(Great!)"])
(def default-width "300px")
(def default-thresholds  [90, 70, 40, 20])

(def bar-label (atom default-bar-label))
(def strength-labels (atom default-strength-labels))
(def colors (atom default-colors))
(def thresholds (atom default-thresholds))
(def base-color (atom default-base-color))
(def width (atom default-width))

(defn valid-strength
  "Make a strength value valid"
  [strength]
  (let [s (if (< strength 0) 0 strength)
        s (if (>= s max-strength) (- max-strength 1) s)]
    s))

(defn strength-to-color
  "Get the color for a strength value"
  [strength]
  (nth @colors (valid-strength strength)))

(defn strength-to-label
  "Get the color for a strength value"
  [strength]
  (@strength-labels (valid-strength strength)))

(defn get-threshold
  "Get the nth threshold"
  [i]
  (nth @thresholds i))

(defn get-strength
  "Get strength and color for a score"
  [score]
  (let [idx (cond
              (> score (get-threshold 0))  4
              (> score (get-threshold 1))  3
              (>= score (get-threshold 2)) 2
              (>= score (get-threshold 3)) 1
              :else 0
              )]
    (+ idx 1)))

(def variations
  [#"\d"    ;; digits
   #"[a-z]" ;; lower
   #"[A-Z]" ;; upper
   #"\W"    ;; nonWords
   ])

(defn variation-count
  "Return number of string variations"
  [password]
  (if (nil? password)
    0
    (reduce
      (fn [v re] (if (re-find re password) (+ v 1) v))
      0
      variations)))

(defn count-accum
  "Award every unique letter until 5 repetitions"
  [count]
  (let [accum (atom 0)]
    (dotimes [n count] (reset! accum (+ @accum (/ 5 (+ n 1)))))
    @accum))

(defn count-score
  "Unique letter count score"
  [password]
  (reduce
    (fn [v c] (+ v (count-accum (nth c 1))))
    0
    (frequencies password)))

(defn measure-strength
  "Get score for a password"
  [password]
  (let [score (count-score password)
        var-count (variation-count password)]
    (Math/floor (+ score (* 10 (- var-count 1))))))

(defn password-strength
  "Get the password strength"
  [password]
  (- (get-strength (measure-strength password)) 1))
