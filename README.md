Akanke - Tech Blogging Engine ![Travis Build Status](https://travis-ci.org/bkielczewski/akanke.svg "Travis Build Status")
=============================
Aims to provide minimalistic blogging experience with more technical users in mind.

Features
--------
* Backed by Markdown files.
* Fast, files are synced to and served from in-memory database.
* No database, dependencies or configuration necessary, just run the provided JAR.
* Wordpress compatible URL scheme for easy migration.
* Build-in Facebook integration for social features.
* Open source, 100% Java, free to use.

User Guide
----------
For downloads and installation instructions check out the page: [kielczewski.eu/akanke](http://kielczewski.eu/akanke)
Otherwise just grab the binaries and run it, detailed instructions are provided in the example content.

Docker
------
You can pull it from Docker image:

    docker pull bkielczewski/akanke

And then run:

    docker run -rm -p 8080:8080 bkielczewski/akanke

Once it starts, point your browser to:

    http://localhost:8080

Development
-----------

### Requirements
* [Java Platform (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.x](http://maven.apache.org/)
* [Node.js](http://nodejs.org/download/)

### Quick start
0. `mvn install`
1. `cd akanke-web`
2. `mvn spring-boot:run`
3. Point your browser to [http://localhost:8080](http://localhost:8080)

### Contributing
You're really welcome to contribute or share ideas. Given you know Spring it should be quite straight-forward to
make at least some sense out of the code. If not, just ask questions and I'll try to help.

### Road map
I'll be looking into:

* RSS feeds
* Supporting alternative templating engines (JSP)
* Admin module that allows creating and editing Markdown files online
* Build-in Git integration to store and sync `./documents` contents using a repo

Release History
---------------
03/01/2015 - 1.0.0-SNAPSHOT made public
