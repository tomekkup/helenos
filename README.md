# Helenos

## About

Helenos is a web based GUI Cassandra client that helps you to explore data and manage your schema.

## Current version

1.2

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
    * keyRange
    * predicate


## Installation

* Download war package and deploy to your servlet container (tested with Jetty 8, Tomcat 6)
* Start app
* Make sure your browser supports Flash 10
* Run and open your web browser to http://localhost:8080/{yourdeploypath}/index.html
* Edit your connections by clicking icon in upper right corner
* After editing click button 'Connect to'

## Building manually

You can build manually with Maven but first install all prerequisities:

* Qooxdoo SDK 2.0.1 [download] (http://sourceforge.net/projects/qooxdoo/)
* Python 2.x [download] (http://www.python.org/getit/releases/2.7.3/)

## Wish list

* Browse with indexed columns
* Results pagination and export to file
* Manually setting serializers
* Persisting default filter criteria to reuse at anytime
* JMX monitor 

## FAQ

 * I see a cluster structure in a left pane but how to do anything more !? - Use the force Luke ...I mean right mouse button