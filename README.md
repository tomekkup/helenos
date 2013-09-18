# Helenos

## About

Helenos is a web based GUI Cassandra client that helps you to explore data and manage your database schema.

## Current version

1.4-RC2

## Download

Packages are available to download via [Sourceforge.net](https://sourceforge.net/projects/helenos-gui/files/) as WAR files and Apache Tomcat bundles.

## Changelog

See [here](https://github.com/tomekkup/helenos/wiki/Changelog) for all changes in current and all previous releases

## Gallery

[![CF properties] (https://raw.github.com/tomekkup/helenos/gh-pages/gallery/helenos1_small.png)](https://raw.github.com/tomekkup/helenos/gh-pages/gallery/helenos1.png)  [![CQL] (https://raw.github.com/tomekkup/helenos/gh-pages/gallery/helenos2_small.png)](https://raw.github.com/tomekkup/helenos/gh-pages/gallery/helenos2.png)  [![Browse] (https://raw.github.com/tomekkup/helenos/gh-pages/gallery/helenos3_small.png)](https://raw.github.com/tomekkup/helenos/gh-pages/gallery/helenos3.png)

Click here for [preview on Youtube](http://www.youtube.com/watch?v=gOWHN6bCybQ&hd=1&autoplay=1)

## Features

* Authentication
* User ADMIN & SIMPLE roles
* Listing keyspaces and column families
* Viewing properties of above
* Add / remove / truncate column family
* Add / Drop keyspace
* Data browsing via:
    * keyRange
    * predicate
* Results pagination
* CQL support

## Installation

* Make sure that a user account which your application server runs as, has write permission to home directory
* Download war package and deploy to your servlet container (tested with Jetty 8, Tomcat 6, Resin 4)
* When running on Resin, additionally make sure that fileServlet is enabled and configured (see web.xml or Resin manual)
* Start app
* Make sure your web browser supports Flash 10
* Open your web browser to http://localhost:8080/{yourdeploypath}/index.html
* Sign in with your credentials (default is admin:admin) 
* Edit your connections by clicking icon in upper right corner
* After editing click button 'Connect to'

## Building manually

You can build manually with Maven but first install all prerequisities:

* Qooxdoo SDK 2.1 [download] (http://sourceforge.net/projects/qooxdoo/)
* Python 2.x [download] (http://www.python.org/getit/releases/2.7.3/)

Next download this package: [https://github.com/cboulanger/qx-contrib-Dialog/archive/master.zip] (https://github.com/cboulanger/qx-contrib-Dialog/archive/master.zip)
and unpack to directory src/main/frontend/contribs and change destination folder name to 'dialog'. Next apply patch file 'src/main/frontent/Dialog.js.patch'.

## Wish list

* Browse with indexed columns
* Results export to file
* Manually setting serializers
* Persisting default filter criteria to reuse at anytime
* JMX monitor
* Schema editing
* Queries log

## FAQ

 * I see a cluster structure in a left pane but how to do anything more !? - Use the force Luke ...I mean right mouse button
 * My Cassandra instance is not listening on localhost:9160. How to change the CassandraHost after installation ? - put your values into defaults.properties file, then restart
