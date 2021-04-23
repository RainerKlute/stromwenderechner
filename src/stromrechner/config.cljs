(ns stromrechner.config
  (:require [stromrechner.helpers :as h]
            [stromrechner.color :as color]
            [clojure.string :as str]
            [stromrechner.google-defines :as gf])
  (:require-macros [stromrechner.macros :as m]))

;; ##########################################
;; ######### Enrichment of Settings #########
;; ##########################################

;; some constants can be derived from the settings right away
;; to avoid recalculation

;; TODO: We could do this at compile time

(defn enrich-nrg-constants
  ""
  [nrg-constants]
  (h/map-vals #(-> %
                   (assoc :transparent-color
                          (color/make-transparent (:color %)))
                   (assoc :darker-color
                          (color/set-brightness (:color %) 0.42))
                   (assoc-in [:props :fill] (color/make-transparent (:color %) 0.65)))              
              nrg-constants))

(defn enrich-settings
  ""
  [settings]
  (update settings :nrg-constants enrich-nrg-constants))


;; ####################################################################
;; ############## Load the Settings from the config file ##############
;; ####################################################################

(m/def-config config)

(def settings (:settings config))

(def texts (:texts config))

(def snippets (:snippets config))

(defn snippet
  "Extracts the snippet at PATH. If at any point
  of the path a string should be encountered it is returned."
  [& path]
  (reduce
   (fn [structure next-path-elem]
     (if (string? structure)
       (reduced structure)
       (get structure next-path-elem)))
   snippets
   path))

;; ##############################################################
;; ############# Extraction of Configuration Values #############
;; ##############################################################

(def nrgs (enrich-nrg-constants
                    (:nrg-constants settings)))

(def nrg-keys (map first (get settings :init-mix)))

(def total-landmass (:total-landmass settings))

(def co2-colors (:co2-colors settings))

(defn feature-active?
  ""
  [feature-key]
  ((:features settings) feature-key))

(defn icon-for-nrg
  ""
  [nrg-key]
  (get-in nrgs [nrg-key :icon])) 
