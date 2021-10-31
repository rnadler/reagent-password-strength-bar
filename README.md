# reagent-password-strength-bar

[![Build Status](https://travis-ci.org/rnadler/reagent-password-strength-bar.svg?branch=master)](https://travis-ci.org/rnadler/reagent-password-strength-bar)
[![npm version](https://badge.fury.io/js/reagent-password-strength-bar.svg)](https://badge.fury.io/js/reagent-password-strength-bar)

This a ClojureScript implementation of [ng9-password-strength-bar](https://www.npmjs.com/package/ng9-password-strength-bar).

[Try it live!](https://plnkr.co/edit/z0x5gG?p=preview)

## Install in your project

`npm install reagent-password-strength-bar --save`

## Run the example application locally
- `git clone https://github.com/rnadler/reagent-password-strength-bar.git`
- `cd reagent-password-strength-bar`
- `lein figwheel`

## Run the unit tests
- Same as above, except for the last step do:
- `lein doo node test once`

## Using the Component
### Add Component to your Application
```clojure
(defn password-form
  "The password input element and strength bar"
  [password]
  (fn []
    [:div
     [:label "Password"]
     [input-element password]
     [password-strength-bar password

      {:bar-label "PW Strength:"
       :strength-labels ["(Yuk)" "(Aweful)" "(OK)" "(Above Average)" "(Marvolous!)"]
       :colors ["#DD2C00", "#FF6D00", "#FFD600", "#AEEA00", "#00C853"]
       :thresholds  [90, 75, 45, 25]
       :base-color "#BBB"
       :bar-width "325px" }
      ]]))
```
### Component Parameters
```clojure
[password-strength-bar password options]
```
- The options map is optional and if exists, all options are also optional.
#### password (type: string)

- The atom containing the password to check. This atom should be updated when the input string changes. 

#### bar-label (type: string, optional)

- The variable containing the label displayed to the left of the bar.

#### bar-colors (type: Array\<string\>, optional)

- The variable can be used to define custom bar colors.<br>
- This must be an Array of 5 strings.<br>
- Lowest security level picks `colors[0]`, ..., the highest picks `colors[4]`.<br>
- If not specified, the default is: `['#F00', '#F90', '#FF0', '#9F0', '#0F0']`

#### base-color (type: string, optional)

- The variable can be used to define the color of bars when no strength is applied (i.e. when there is no password text).<br>
- If not specified, the default is: '#DDD'.<br>
For example:
```clojure
:base-color "#BBB"
```

#### strength-labels (type: Array\<string\>, optional)

- The variable can be used to define a strength label that will be appended to the colored bars.<br>
- This must be an Array of 5 strings.<br>
For example:
```clojure
public strengthLabels = ['(Useless)', '(Weak)', '(Normal)', '(Strong)', '(Great!)'];
```

#### custom-thresholds (type: Array\<number\>, optional)

- The variable can be used to define custom strength algorithm thresholds.<br>
- This must be an Array of 4 integers. See the source code for details on how these values are used.<br>
- If not specified, the default is: `[90, 70, 40, 20]`

#### bar-width (type: string, optional)

- The variable can be used to specify the width of the strength progress bar..<br>
- If not specified, the default is: `350px`

### License

[MIT](https://tldrlegal.com/license/mit-license)

