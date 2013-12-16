versioning
==========

*versioning* is a simple web-application to track when a software was deployed
to a server. It allows the user to add a list of projects and servers, and to
specify versions of the project to be deployed on the servers. This allows you
to easily see which deployments need to be done to get a server updated.

Setup
-----

To run *versioning* you need a servlet container capable of providing datasources
by JNDI. Only a single datasource is needed to be bound to
"java:comp/env/jndi/versioningDS". At the moment the database will automatically
be updated. This will change at a later point when an actual release happened.

Operations
----------

*Copy*

The copy operation will download an artifact (e.g. a JAR-file) from a specified URL
and save it to a given directory. The copy operation will try to parse the first
pom.xml found in the project to determine the file name to be used for saving, if
the filename was not specified by the operation.

*Extract*

Using the extract operation it's possible to download a file from a given URL and
extract parts of it. This can e.g. be used to extract default configurations.

*Checkout*

The checkout operation will download source code from a VCS repository (see
supported VCSs below). This can, for example, be used to checkout projects that
don't need to be compiled. It is also available for servers and can be used to
checkout their VCS managed configuration.

*Replace*

The replace operation will perform search and replace on the prepared files. It
uses a list of replacements provided by the versioning server or can use a
properties file. The later can especially be used to insert passwords or other
sensitive information into files publically saved on a server. The operation
is available for projects and for servers.

Servletcontainers
-----------------

*versioning* was tested sucessfully on the following containers

* Tomcat 7: This container is used for development and likely to be supported
  best at the moment.

The following containers are not supported right now

* Jetty 9: The JSF 2 reference implementation (Mojarra) only supports Jetty up
  to version 7. Jetty 9 switched its package from org.mortbay to org.eclipse
  causing injection to fail.

All other containers are untested.

Supported Databases
-------------------

The project is using hibernate for data access. This means that theoretically
every database supported by hibernate is usable. You have to keep in mind that
we are using flyway to initialize and migrate the databases. The flyway scripts
are only available and tested on on the following databases right now:

* MySQL 5.0+
* MariaDB 5.5+
* hsqldb 2.3+

Further database systems are going to be supported at a later point.

Supported VCSs
--------------

* Git
* Subversion

Support for other VCS systems is not yet planned. If you need support for
another VCS you can implement the *Provider* interface. As of now the VCS
only needs to implement a method to verify a tag exists.

Used software
-------------

The following components were used to develop *versioning*:

* slf4j 1.7
* hibernate 4.2, hibernate validator 5.0
* joda-time 2.3
* commons-lang 3.1 and commons-collections 4.0
* JSF 2.2 and RichFaces 4.3
* jgit 3.1 and svnkit 1.7
* junit 4.11, mockito 1.9 and hsqldb 2.3
* flyway 2.2
