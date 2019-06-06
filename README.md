# dmms-app
This repository is the core of the Digital Media Management System application. Keep in mind that the app is still being developed.

Main functions of this system:
* User accounts
* Storing digital books in various formats
* Sharing books with other users - also from different DMMS servers

## Installation
To build this app maven is required.

**Required properties:**
```yaml
server.address=
server.port=

spring.datasource.url=
spring.datasource.driver=
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=

book.storage.dir=
logsDirPath=
```
`logsDirPath` - directory where app logs are stored e.g. `/opt/dmms/web-app/logs`
`book.storage.dir` - directory where book files are stored e.g. `/opt/dmms/web-app/storage`

We highly recommend to use ansible to deploy this application (more information in `dmms-ansible` repository.)

## Modules

### app-management
This module is responsible for managing app e.g. downloading logs.

### core
This module is responsible for core business logic of the application.

### user-management
This module is responsible for managing users (e.g. registering, logging and deleting users) 

### spi
This module is responsible for delivering entities

### web-api
This module is responsible for serving REST API for web client application.

## Rest Api
Part of below endpoints are available only for user with ADMIN privileges.

_More specific information about servers and sharing is available in `Resources sharing and server-server communication` document._

**Authors:**
* `GET /authors` - returns list of all authors
* `GET /authors/{authorId}` - returns specified author
* `POST /authors` - creates new author

**Books:**
* `GET /books` - returns list of all books
* `GET /books/{bookId}` - returns specified book
* `GET /books/{bookId}/{type}` - download book with given format
* `POST /books` - creates new book and uploads files
* `PUT /books/{bookId}` - updates specified book
* `DELETE /books/{bookId}` - deletes specified book

**Categories:**
* `GET /categories` - returns list of all categories
* `POST /categories` - creates new category
* `DELETE /categories/{categoryId}` - deletes specified category

**Logs:**
* `GET /logs/download` - downloads archived app logs

**Users:**
* `POST /user-management/login` - user authorization
* `POST /user-management/logout` - destroys user session
* `GET /users` - returns list of all users
* `GET /users/current` - returns information about currently logged user based on his session
* `GET /users/{userId}` - returns specified user
* `POST /users` - creates new user (registration process)
* `PUT /users/current` - updates data of currently logged user
* `PUT /users/role` - updates role of specified user
* `DELETE /users/{userId}` - deletes specified user

**Servers:**
* `GET /servers` - returns list of all servers
* `POST /servers` - creates new INSOURCE server
* `POST /servers/request` - creates new not accepted OUTSOURCE server
* `POST /servers/{id}/accept` - marks specified server as accepted
* `POST /servers/id/reject` - rejects specified server and removes its data 

**Sharing:**
* `GET /sharing` - returns list of all shared resources to other users
* `GET /sharing/in` - return list of all shared resource for current user
* `GET /sharing/out/{serverId}/{userLogin}` - returns list of all shared resources to specified user with given destination server
* `POST /sharing` - shares given books with specified server

