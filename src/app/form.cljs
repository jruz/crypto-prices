(ns app.form
  (:require
   [reagent.core :as r]
   [app.loading :refer [loading]]))

(def api-key (r/atom nil))
(def is-connecting (r/atom false))

(defn on-change [event]
  (reset! api-key event.target.value))

(defn handle-submit [event on-submit]
  (.preventDefault event)
  (reset! is-connecting true)
  (on-submit @api-key))

(defn form [on-submit]
  [:div.form
   (if is-connecting
     [:form {:on-submit #(handle-submit % on-submit)}
      [:div.field.has-addons
       [:label.label {:for "api_key"} "Crytowat.ch API key"]]
      [:div.field.has-addons
       [:div.control.is-expanded
        [:input.input {:type "text" :id "api_key" :on-change on-change}]]
       [:div.control]
       [:button.button.is-primary {:type "submit"
                                   :disabled (if (empty? @api-key) "disabled")}
        "Connect"]]]
     (loading))])
