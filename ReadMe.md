# Instalació de la base de dades (MongoDB) a MasOS:
1. Instal·lar MongoDB: https://www.mongodb.com/docs/manual/administration/install-community/
2. Obrir/tancar el servei: brew services start mongodb-community@6.0 | brew services stop mongodb-community@6.0
3. Crar usuari:
    3.1 mongosh
    3.1 use admin
    3.2 db.createUser(
        {
            user: "root",
            pwd: "root",
            roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
        }
        )
4. Instal·lar MongoDB Compass per veure els canvis a la DB
5. No fa falta tocar res de configuració al codi

