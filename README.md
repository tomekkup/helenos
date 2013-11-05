## Helenos

### Introduction

Helenos is a free web based environment that simplifies a data exploring & schema managament with Apache Cassandra database.

### Features list

* Exploring schema 
* Viewing properties of keyspaces and column families
* Add / remove / truncate column family
* Add / Drop keyspace
* Data browsing via:
    * keyRange
    * predicate
* Results pagination
* CQL support
* Authentication
* Admin and read only roles

### Current version

1.4

### Changelog

See [here](https://github.com/tomekkup/helenos/wiki/Changelog) for all changes in current and all previous releases

### Download

Packages are available to download via [Sourceforge.net](https://sourceforge.net/projects/helenos-gui/files/) as WAR files and Apache Tomcat bundles.

### Gallery

[![CF properties] (https://raw.github.com/tomekkup/helenos/gh-pages/gallery/helenos1_small.png)](https://raw.github.com/tomekkup/helenos/gh-pages/gallery/helenos1.png)  [![CQL] (https://raw.github.com/tomekkup/helenos/gh-pages/gallery/helenos2_small.png)](https://raw.github.com/tomekkup/helenos/gh-pages/gallery/helenos2.png)  [![Browse] (https://raw.github.com/tomekkup/helenos/gh-pages/gallery/helenos3_small.png)](https://raw.github.com/tomekkup/helenos/gh-pages/gallery/helenos3.png)

Click here for [preview on Youtube](http://www.youtube.com/watch?v=gOWHN6bCybQ&hd=1&autoplay=1)

### Installation

* Make sure that a user account which your application server runs as, has write permission to home directory
* Download war package and deploy to your servlet container (tested with Jetty 8, Tomcat 6, Resin 4)
* When running on Resin, additionally make sure that fileServlet is enabled and configured (see web.xml or Resin manual)
* Start app
* Make sure your web browser supports Flash 10
* Open your web browser to http://localhost:8080/{yourdeploypath}/index.html
* Sign in with your credentials (default is admin:admin) 
* Edit your connections by clicking icon in upper right corner
* After editing click button 'Connect to'

### Building manually

You can build manually with Maven but first install all prerequisities:

* Qooxdoo SDK 2.1.2 [download] (http://sourceforge.net/projects/qooxdoo/)
* Python 2.x [download] (http://www.python.org/getit/releases/2.7.3/)

Next download this package: [https://github.com/cboulanger/qx-contrib-Dialog/archive/master.zip] (https://github.com/cboulanger/qx-contrib-Dialog/archive/master.zip)
and unpack to directory src/main/frontend/contribs and change destination folder name to 'dialog'. Next apply patch file 'src/main/frontent/Dialog.js.patch'.

### Wish list

* Browse with indexed columns
* Results export to file
* Manually setting serializers
* Persisting default filter criteria to reuse at anytime
* JMX monitor
* Schema editing
* Queries log

### FAQ

 * I see a cluster structure in a left pane but how to do anything more !? - Use the force Luke ...I mean right mouse button
 * My Cassandra instance is not listening on localhost:9160. How to change the CassandraHost after installation ? - put your values into defaults.properties file, then restart

### Author

My name is Tomek Kuprowski and I'm java enthusiast, father, husband and IBMer.
You can find me on [LinkedIn] (http://www.linkedin.com/in/tomekkuprowski) and contact via tomekkuprowski@gmail.com

### Licence and disclaimer

Helenos is distributed free, AS-IS, without any warranty under Creative Commons Attribution [licence] (http://creativecommons.org/licenses/by/2.0/legalcode)

### Donation

You can, of course, donate if you found it useful. I had much of satisfaction working on Helenos, but 10 years Scottish tea is still too expensive.

<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
<input type="hidden" name="cmd" value="_s-xclick">
<input type="hidden" name="encrypted" value="-----BEGIN PKCS7-----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-----END PKCS7-----
">
<input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
<img alt="" border="0" src="https://www.paypalobjects.com/pl_PL/i/scr/pixel.gif" width="1" height="1">
</form>
