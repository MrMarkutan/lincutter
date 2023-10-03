## _Lincutter App_

#### PREREQUISITES

`./mongo_startup/mongo-init.js` - script to create user to work with MongoDB and app document Link
change user, password and db_name if needed. If you changed db_name, you need to change it in docker-compose.yml in
mongodb environment vars.

#### Start application

`docker-compose up --build -d`

`mvn spring-boot:run`

#### REQUESTS

`GET localhost:8080/api/links/list` - retrieve list of Links from the database

`POST localhost:8080/api/links/shorten {String originUrl}` - submit originUrl in body and get back "shortened" version.

`POST localalhost:8080/api/lihost:8080/api/links/resolve {String shortUrl}` - submit shortUrl in body and resolve
original url back.