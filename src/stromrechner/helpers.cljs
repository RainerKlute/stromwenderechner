(ns stromrechner.helpers
  (:require
   [re-frame.core :as rf :refer [reg-event-db reg-sub]]
   [clojure.string :as str]))

(defn classes
  ""
  [& classstrings]
  (str/join " " classstrings))

(defn map-vals
  ""
  [f coll]
  (reduce
   (fn [sofar [key val]]
     (assoc sofar key (f val)))
   {} coll))

(defn reverse-paths
  ""
  [indata]
  (let [first-level-keys (keys indata)
        second-level-keys (keys (reduce merge (map second indata)))
        paths (for [flk first-level-keys
                    slk second-level-keys]
                [flk slk])]
    
    (reduce
     (fn [sofar nextpath]
       (assoc-in sofar (vec (reverse nextpath))
                 (get-in indata nextpath)))
     {} paths)))

(defn nan->nil
  ""
  [val]
  (if (js/isNaN val) nil val))

(defn nan->0
  ""
  [val]
  (if (js/isNaN val) 0 val))




(defn structure-int
  "Structures large integers
  by interposing it with whitespace"
  [integer]
  (if (= 0 integer)
    "0"
   (str/replace 
    (->> integer
         str
         reverse
         (partition 3 3 (repeat "0"))
         (interpose " ")
         flatten
         reverse
         (apply str)) #"^0*" "")))





(defn dispatch-on-x
  "Returns a function that dispatches a Re-Frame event.
  The event is created by conjing the first argument of
  the returned function to the event vector.
  f can be a function the  will be applied to that argument
  before conjing.
  Suppresses the default effect."
  ([event]
   (dispatch-on-x false  event))
  ([sync? event]
   (dispatch-on-x sync? identity event))
  ([sync? f event]
   #(let [newval (-> % .-target .-value)]
      (.preventDefault %)
      ((if sync?
         rf/dispatch-sync
         rf/dispatch) (conj event (f newval))))))





(defn dangerous-html
  ""
  [htmlstring]
  [:div {:dangerouslySetInnerHTML
        {:__html
         htmlstring}}])

(defn radius-from-area-circle
  ""
  [surface]
  (Math/sqrt (/ surface Math/PI)))


