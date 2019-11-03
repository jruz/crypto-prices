(ns app.main
  (:require
   [reagent.core :as r]
   [app.form :refer [form]]
   [app.table :refer [table]]))

(def API_URL "wss://stream.cryptowat.ch/connect")
(def markets {"87" {:name "BTC-USD" :exchange "Kraken"}
              "579" {:name "BTC-USDT" :exchange "Binance"}
              "49677" {:name "BTC-USDT" :exchange "Bitfinex"}
              "282" {:name "BTC-USDT" :exchange "Poloniex"}
              "65" {:name "BTC-USD" :exchange "Coinbase"}
              "1" {:name "BTC-USD" :exchange "Bitfinex"}
              "5429" {:name "BTC-USDC" :exchange "Coinbase"}
              "6630" {:name "BTC-USDC" :exchange "Binance"}})

(defn get-subscriptions [markets]
  (let [build (fn [id] {:streamSubscription {:resource (str "markets:" id ":summary")}})
        ids (keys markets)]
    (map build ids)))

(def config {:subscribe {:subscriptions (get-subscriptions markets)}})

(defonce prices (r/atom markets))
(defonce is-connected (r/atom false))
(def ws (atom nil))

(defn on-load [reader]
  (let [jsData (.parse js/JSON (.-result reader))
        data (js->clj jsData :keywordize-keys true)
        id (get-in data [:marketUpdate :market :marketId])
        price (get-in data [:marketUpdate :summaryUpdate :lastStr])
        payload (assoc (get markets id) :price price)]
    (if price
      (swap! prices assoc id payload))))

(defn on-message [event]
  (let [reader (new js/FileReader)]
    (.readAsText reader event.data)
    (set! (.-onload reader) #(on-load reader))))

(defn on-open []
  (reset! is-connected true)
  (.send @ws (.stringify js/JSON (clj->js config))))

(defn connect [api-key]
  (reset! ws (new js/WebSocket (str API_URL "?apikey=" api-key)))
  (set! (.-onmessage @ws) on-message)
  (set! (.-onerror @ws) #(.error js/console %))
  (set! (.-onopen @ws) on-open))

(defn on-submit [api-key]
  (connect api-key))

(defn app []
  [:section
   [:header.container.header
    [:h1.title "BTC price across exchanges"]]
   [:div.container
    (if-not @is-connected
      (form on-submit))]
   [:div.container
    (table @prices)]])

(defn mount-root []
  (r/render [app] (.getElementById js/document "app")))

(defn main! []
  (mount-root))
