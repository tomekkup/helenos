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
            this.credentials = new qx.data.Array();
            for (var i = 0; i < credentials.length; i++) {
                this.credentials.push(credentials[i].authority);
            }
        },
        
        getCredentials : function() {
            return this.credentials;
        },
        
        getLoggedUser : function() {
            return this.loggedUser;
        },
        
        hasRole : function(role) {
            return this.credentials.contains(role);
        }
    }
});
