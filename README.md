# Helenos

## About

Web application to browse your data stored in Cassandra...

The main goal is to develop a web application based on Qooxdoo Frontend which provide a capability of managing schema, easy viewing and editing data in column familes stored in any format (recommended is JSON).

## Current version

1.0-RC

## Download

[here](https://github.com/tomekkup/helenos/downloads)

## Features

* Listing keyspaces and column families
* Viewing properties of above
* Add / remove / truncate column family
* Add / Drop keyspace
* Data browsing via:
    * single column
    * slice

## Installation

* Download war package and deploy to your servlet container (tested with Jetty 8)
* put your helenos.properties configuration file (sample at src/test/resources/) into an application classpath
* Run and open your web browser to http://localhost:8080/

## Building manually

You can build manually with Maven but first install all prerequisities:

* Qooxdoo SDK 2.0.1
* Python 2.x

## Wish list

* Browse with indexed slice and range slices
* Results pagination
* Persisting default filter criteria to reuse at anytime
* JMX monitor