# Helenos

## About

Web application to browse your data stored in Cassandra...

The main goal is to develop a web application based on Qooxdoo Frontend which provide a capability of managing schema, easy viewing and editing data in column familes stored in any format (recommended is JSON).

## Current version

1.0-RC3

## Download

[here](https://github.com/tomekkup/helenos/downloads)

## Gallery

[Picasa album](https://picasaweb.google.com/tomekkuprowski/Helenos)

## Features

* Listing keyspaces and column families
* Viewing properties of above
* Add / remove / truncate column family
* Add / Drop keyspace
* Data browsing via:
    * single column
    * slice

## Installation

* Download war package and deploy to your servlet container (tested with Jetty 8, Tomcat 6)
* Put your helenos.properties configuration file (sample at src/test/resources/) into an application classpath. First set hosts property to coma separated list of Cassandra hosts
* Restart app
* Run and open your web browser to http://localhost:8080/

## Building manually

You can build manually with Maven but first install all prerequisities:

* Qooxdoo SDK 2.0.1
* Python 2.x

## Wish list

* Browse with indexed slice and range slices
* Results pagination and export to file
* Persisting default filter criteria to reuse at anytime
* JMX monitor 

## FAQ

 * I see a cluster structure in a left pane but how to do anything more !? - Use the force Luke ...I mean right mouse button