### Preconditions
In order to use this code, you should have a local mySQL database and tomcat9 to emulate a web server.

## Getting Started

### Fork this git.
Change the POM.xml to reflect your wanted changes, including the:
"<remote.server>https://yourdomainname.com/manager/text</remote.server>" for tomcat.
"<db.name>startcode</db.name>"
Change local properties in persistence.xml to use new local database.
Create a databse in MySQL or alike to persist your data.
Use Intellij to create a config to start the project as a tomcat web server.

### The project includes:
Corsfilter.
JPA.
Errorhandling.
Hashing of User entity password.
Entity roles.
Rest API endpoint.
Json web token to authenticate users logging in through REST api.
HttpsFetching from remote API.
Restassured & junit tests.

### How to use.
Use the class "HttpUtils" to fetch new apis.
Use the class "FetcherResource" to create new REST APIs that others can fetch from.
Use DTO (Data transfer objects) to transfer data between the remote endpoint and this project.
Use the entities package to persist data to the database.
Create new entities as new.# CA3-Backend
