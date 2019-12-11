(ns sem.data
  "Functions to get the current yearterm."
  (:require [byu-ws.core :as ws]
            [java-time :as time]
            [clojure.set :refer [rename-keys]]))

(defn get-yt
  "Get the current yearterm string. Expects `:client-id` and `:client-secret` to be your registered BYU API codes."
  [{:keys [client-id client-secret]}]
  (let [date-url "https://api.byu.edu:443/records/controlDates/v1"
        response (ws/get-standard-ws {:url           date-url
                                      :client-id     client-id
                                      :client-secret client-secret})]
    (-> response (get "content") first (get "yearTerm"))))

(defn get-dates-yt
  "Get the start and enddate of a given yt string (\"20201\")"
  [{:keys [client-id client-secret yt]
    :as   opt-map
    :or   {yt (get-yt opt-map)}}]
  (let [url                (format "https://api.byu.edu:443/records/controlDates/v1?yearTerm=%s&dateType=SEMESTER"
                                   yt)
        date-string-format "yyyy-MM-dd"
        update-to-date     (partial time/sql-date date-string-format)
        ws-map             (assoc opt-map :url url)
        response           (ws/get-standard-ws ws-map)]
    
    (-> response
        (get "content")
        first
        (select-keys ["startDate" "yearTerm" "endDate"])
        (rename-keys {"startDate" :start-date
                      "yearTerm"  :year-term
                      "endDate"   :end-date})
        (update :start-date update-to-date)
        (update :end-date update-to-date))))
