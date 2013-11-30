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

Servletcontainers
-----------------

*versioning* was tested sucessfully on the following containers

* Tomcat 7: This container is used for development and likely to be supported
  best at the moment.

The following containers are not supported right now

* Jetty 9: The JSF 2 reference implementation (Mojarra) only supports Jetty up
  to version 7. Jetty 9 switched its package from org.mortbay to org.eclipse
  causing injection to fail.

Supported VCSs
--------------

* Git
* Subversion

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
