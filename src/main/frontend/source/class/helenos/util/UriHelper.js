/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.util.UriHelper', {
        
    statics : {
        serverPath : null,
        
        getRemoteUri : function(resource) {
            if (this.serverPath == undefined)  {
                var pathIdx = window.location.href.indexOf('/gui/index.html');
                this.serverPath = window.location.href.substr(0, pathIdx);
            }
            
            return this.serverPath + resource;
        }
    }
});
