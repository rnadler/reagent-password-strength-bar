(ns reagent-password-strength-bar.strength)

(def max-strength 5)
(def default-base-color  "#DDD")
(def default-bar-label "Password strength:")
(def default-strength-labels ["(Useless)" "(Weak)" "(Normal)" "(Strong)" "(Great!)"])
(def default-width "300px")
(def default-thresholds  [90, 70, 40, 20])
(def colors
  {:strength-0 "#F00"
   :strength-1 "#F90"
   :strength-2 "#FF0"
   :strength-3 "#9F0"
   :strength-4 "#0F0"})

(defn valid-strength
  "Make a strength value valid"
  [strength]
  (let [s (if (< strength 0) 0 strength)
        s (if (>= s max-strength) (- max-strength 1) s)]
    s))

(defn strength-to-color
  "Get the color for a strength value"
  [strength]
  (colors (keyword (str "strength-" (valid-strength strength)))))

(defn strength-to-label
  "Get the color for a strength value"
  [strength]
  (default-strength-labels (valid-strength strength)))

(defn get-threshold
  "Get the nth threshold"
  [i]
  (nth default-thresholds i))

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
