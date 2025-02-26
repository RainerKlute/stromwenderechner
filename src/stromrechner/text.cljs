(ns stromrechner.text
  (:require-macros [stromrechner.macros :as m]))

(def snippets 
  (m/build-text-map
   {:general {:heading "Allgemeines"}
    :power-density {:heading "Bemessungsleistung pro m²"}
    :deaths {:heading "Statistisch erwartbare Todesfälle pro TWh"}
    :co2 {:heading [:span "Spezifische CO" [:sub "2"] "-Emissionen" ]}
    :resources {:heading "Ressourcenverbrauch"}
    :wind {:heading "Windenergie"}
    :solar {:heading "Photovoltaik"}
    :nuclear {:heading "Kernenergie"}
    :bio {:heading "Biomasse"}
    :natural-gas {:heading "Erdgas"}
    :coal {:heading "Kohle"}
    }))
