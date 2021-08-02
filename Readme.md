# Docker Demo with Web App & Database

This project was initialized using [spring initializr](https://start.spring.io/) with Spring Web and
Spring Data JPA.  

This is a very bare demo that showcases the usage of Docker to 
distribute database structure with developers over a VCS (Version Control System -
such as Git).

## Prerequisites
* A Docker installation
* (Optional) A **Maven** installation (preferably version 3.6.x [Download links](https://archive.apache
.org/dist/maven/maven-3/3.6
.3/binaries/);
[Installation Instructions](https://maven.apache.org/install.html))

    *   If not installed, have the ff:
        * Installed JDK (Java Development Kit; preferably ver. 8)
        * Environment variable **JAVA_HOME** set (usually handled by Java installation)


To be able to run the web application, do the following steps:
1. Run the database (Docker)
2. Run the web application (Maven)

## 1. Run the Database

Because the web app needs the database to be running in order start properly, the database needs
to be start first.

To run the database, *build* the database image using the file
**Dockerfile** included in the project root directory:

```
docker build -t demo:postgres .
```

Then run the image to create the container (which initializes its data based on the 
*demo-schema.sql* file copied into the image during its build):

```
docker run --name demo-db -p 54311:54311 -e POSTGRES_PASSWORD=password -e PGPORT=54311 -d demo:postgres
```

## 2. Run the Web App

##### 1. A. If Maven is Installed:

Run the following command in the project root directory (same location as this readme file):

```
mvn spring-boot:run -DskipTests
```

##### 1. B. If Maven is NOT Installed:

Assuming that the system has Java and the environment JAVA_HOME set, Maven can be run from here
using the Maven wrapper file (**mvnw** for Linux/Mac; **mvnw.cmd** for Windows).

To run the app using the Linux/Mac Maven wrapper file, run the following in the shell in the same
directory as this file:

```
./mvnw spring-boot:run -DskipTests
```

To run the app using the Windows Maven wrapper file, run the following in the CMD in the same directory 
as this file:

```
mvnw spring-boot:run -DskipTests
``` 

##### 2. Access the App!

Access the application home endpoint at ***localhost:8080***, and the database-touching endpoints at
***localhost:8080/test-item/{id}***, where ***{id}*** pertains to an ID of a row in the database table
*test_item* (the database was initialized with 3 entries with IDs **1, 2, and 3**!).

---

### Bonus: Containerizing the Web App

Doing this has implications in *networking*.

Docker has built-in support for configurable networks so that containers can communicate with each
other in predictable ways.

For this section, we will use Docker's basic network provisions for communication between the
web app and database containers.

#### 1. Create the Docker network and Connect the Database Container to It

Run the following commands to create the network and add the database container to it:

```
docker network create demonetwork
docker network connect demonetwork demo-db
```

#### 2. Change the Web App Database URL Configuration

In the file *application.properties*, change the value of *spring.datasource.url* to the following:

```
jdbc:postgresql://demo-db:54311/postgres
```

In the above configuration, "*localhost*" was replaced with "*demo-db*", the name of the container of the
postgres database. In basic Docker networking, containers are "hosts" that refer to each other through
their container names.

#### 3. Build the Image of the Web App

First, make sure the app is compiled and packaged via the following Maven command (adjust with the 
wrapper accordingly):

```
mvn package -DskipTests
```

To confirm that this worked, there should be a file named **target/demo-0.0.1-SNAPSHOT.jar**.

Once this is done, the web app image can now be built:

```
docker build -t demo:webapp -f Dockerfile_webapp .
```

Since the file "Dockerfile" is used to build the database (and is also the default file used by the
build command), we specify which file the build command should use via the "***-f***" flag, where
we identify the file as "*Dockerfile_webapp*".

#### 4. Run the Web App Image in the Created Network

To run the image in the network we created, execute the following command:

```
docker run -d --name demo-webapp -p 8080:8080 --network demonetwork demo:webapp 
```

In the above *run* command, we add the flag "***--network***" with the value **demonetwork** to specify
which network the container should be part of.

Additionally, we also specified which container port should be mapped to our external machine port
since the application is now contained in Docker.

From here, the app can now be accessed the same way as before (via *localhost:8080*)!
