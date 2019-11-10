(ns coffee-roulette.core
  (:require [datomic.api :as d])
)

(def uri "datomic:free://localhost:4334/coffee")

;;(d/create-database uri)

(def conn (d/connect uri))

(def person-schema [{:db/ident :person/name
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "Person's name"}

                       {:db/ident :person/phone
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "Person's phone number"}
                    
                    {:db/ident :person/skills
                     :db/valueType :db.type/string
                     :db/cardinality :db.cardinality/many
                     :db/doc "A set of skills that a person has"}
                       ])

;;(d/transact conn person-schema)

(def add-person [{:person/name "misha" :person/phone "614 567-1309" :person/skills ["programmer" "carpenter"]}])

(def add-person-2 [{:person/name "derek" :person/phone "614 567-1308" :person/skills ["welder" "carpenter"]}])

;;(d/transact conn add-person-2)

(def all-people-q '[:find ?e :where [?e :person/name ]])

(def all-people-w-skill-q '[:find ?name ?skills :where [?e :person/name ?name] [?e :person/skills ?skills]])

(defn find-by
  "Returns all people identified by attr and val."
  [attr val db]
  (d/q '[:find ?n ?p
         :in $ ?attr ?val
         :where [?e ?attr ?val] [?e :person/name ?n] [?e :person/phone ?p]]
       db attr val))

;;(defrecord Worker [name skills phone])
