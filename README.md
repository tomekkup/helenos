# Helenos

## About

Helenos is a web based GUI Cassandra client that helps you to explore data and manage your schema.

## Current version

1.1

## Download

[here](https://github.com/tomekkup/helenos/downloads)

## Changelog

[here](https://github.com/tomekkup/helenos/wiki/Changelog)

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
* Cell data stored in JSON can be easily viewed in tree

## Installation

* Download war package and deploy to your servlet container (tested with Jetty 8, Tomcat 6)
* Put your helenos.properties configuration file (sample at src/test/resources/) into an application classpath. First set hosts property to coma separated list of Cassandra hosts
* Start app
* Run and open your web browser to http://localhost:8080/{yourdeploypath}/index.html
* Edit your connection by clicking icon in upper right corner
* After editing click button 'Connect to'

## Building manually

You can build manually with Maven but first install all prerequisities:

* Qooxdoo SDK 2.0.1
* Python 2.x

... and checkout this repository [SilverBlueTheme] (https://qooxdoo-contrib.svn.sourceforge.net/svnroot/qooxdoo-contrib/trunk/qooxdoo-contrib/SilverBlueTheme/) to folder src/main/frontend

## Wish list

* Browse with indexed slice and range slices
* Results pagination and export to file
* Persisting default filter criteria to reuse at anytime
* JMX monitor 

## FAQ

 * I see a cluster structure in a left pane but how to do anything more !? - Use the force Luke ...I mean right mouse button