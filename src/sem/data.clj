(ns sem.data
  "Functions to get the current yearterm.")

(defn get-yt
  "Get the current yearterm string"
  []
  (let [date-url "https://api.byu.edu:443/records/controlDates/v1"
        response (ws/get-standard-ws {:url date-url
                                      :client-id (-> env :client-id)
                                      :client-secret (-> env :client-secret)})]
    (-> response (get "content") first (get "yearTerm"))))

(defn get-dates-yt
  "Get the start and enddate of a given yt string"
  [& [yt]]
  (let [yt (or yt (get-yt))
        url (format "https://api.byu.edu:443/records/controlDates/v1?yearTerm=%s&dateType=SEMESTER"
                    yt)
        date-string-format "yyyy-MM-dd"
        update-to-date (partial time/sql-date date-string-format)
        response (ws/get-standard-ws {:url url
                                      :client-id (-> env :client-id)
                                      :client-secret (-> env :client-secret)})]
    
    (-> response
        (get "content")
        first
        (select-keys ["startDate" "yearTerm" "endDate"])
        (clojure.set/rename-keys {"startDate" :start-date
                                  "yearTerm" :year-term
                                  "endDate" :end-date})
        (update :start-date update-to-date)
        (update :end-date update-to-date))))
