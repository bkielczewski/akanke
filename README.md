Akanke - Tech Blogging Engine
=============================
Easy blogging for more technical users.

Features
--------
* Simple to deploy, backed by files, no database or configuration necessary.
* Articles written in Markdown.
* Wordpress compatible URL scheme for easy migration.
* Build-in Facebook integration for social features.
* Open source, 100% Java.

User Guide
----------
For downloads and installation instructions check out the page: [kielczewski.eu/akanke](http://kielczewski.eu/akanke)
Otherwise just run it, it's in the example content.

Docker
------
You can run it straight from Docker image:

TBD

Development
-----------

### Requirements
* [Java Platform (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.x](http://maven.apache.org/)
* [Node.js](http://nodejs.org/download/)

### Quick start
1. `cd akanke-web`
2. `mvn spring-boot:run`
3. Point your browser to [http://localhost:8080](http://localhost:8080)

### Contributing
You're really welcome to contribute, just create a pull request and I'll accept it if it makes sense.

### Road map
I'll be looking into:

* Support for more templating engines
* Writing an admin module that allows creating and editing Markdown files online
* Build-in Git integration to store and sync documents using a repository

Release History
---------------
03/01/2015 - 1.0.0-SNAPSHOT made public