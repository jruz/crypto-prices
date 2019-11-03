(ns app.table)

(defn table [prices]
  [:table.table
   [:thead
    [:tr
     [:th "Pair"]
     [:th "Exchange"]
     [:th "Price"]]]
   [:tbody
    (let [sorted (sort-by (fn [[_ i]] (:price i)) prices)]
      (for [[id i] sorted]
        [:tr {:key id}
         [:th (:name i)]
         [:td (:exchange i)]
         [:td.price (if (:price i)
                      (.toLocaleString (js/Number.parseInt (:price i) 10) "en-US" (js-obj "style" "currency" "currency" "USD" "minimumSignificantDigits" 1))
                      "-")]]))]])
