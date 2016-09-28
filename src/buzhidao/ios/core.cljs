(ns buzhidao.ios.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [buzhidao.events]
            [buzhidao.subs]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))

(def logo-img (js/require "./images/cljs.png"))

(defn alert [title]
      (.alert (.-Alert ReactNative) title))

(def words
  {"我" "wo"
   "你" "ni"
   "他" "ta"
   "是" "shi"
   "有" "you"
   "要" "yao"})

(defn app-root []
  (let [greeting (subscribe [:get-greeting])
        word (subscribe [:word])
        reveal (subscribe [:reveal])]
    (fn []
      [view {:style {:flex-direction "column"
                     :margin 40
                     :align-items "center"}}

       [image {:source logo-img
               :style  {:width 80
                        :height 80
                        :margin-bottom 30}}]

       [text {:style {:font-size 200
                      :font-weight "100"
                      :margin-bottom 10
                      :text-align "center"}}
        @word]

       [text {:style {:font-size 50
                      :font-weight "100"
                      :margin-bottom 10
                      :text-align "center"}}
        (or @reveal " ")]

       [touchable-highlight {:style {:background-color "#999"
                                     :padding 10
                                     :border-radius 5
                                     :margin 20}
                             :on-press #(dispatch [:next-word words])}

        [text {:style {:color "white"
                       :text-align "center"
                       :font-weight "bold"}} (if @word "Next" "Start")]]

       (when @word
         [touchable-highlight {:style {:background-color "#999" :padding 10 :border-radius 5
                                       :margin 20}
                               :on-press #(dispatch [:reveal words])}
          [text {:style {:color "white" :text-align "center" :font-weight "bold"}} "Reveal"]])])))

(defn init []
  (dispatch-sync [:initialize-db])
  (.registerComponent app-registry "buzhidao" #(r/reactify-component app-root)))
