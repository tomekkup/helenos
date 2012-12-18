/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.util.CredentialsProvider', {
        
    statics : {
        loggedUser : null,
        credentials : null,
        
        registerLoggedUser : function(username, credentials) {
            this.loggedUser = username;
            this.credentials = credentials;
        },
        
        getCredentials : function() {
            return this.credentials;
        },
        
        getLoggedUser : function() {
            return this.loggedUser;
        }
    }
});
