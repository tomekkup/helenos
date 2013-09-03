/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.util.RpcActionsProvider', {
        
    statics : {
        
        _SCHEMA : '/SchemaInfo.json',
        _SCHEMAADMIN : '/admin/Schema.json',
        _CREDENTIALS : '/Credentials.json',
        _STANDARDQUERY : '/query/Standard.json',
        _SUPERQUERY : '/query/Super.json',
        _CLUSTERCONNECTION : '/ClusterConnectionProvider.json',
        _ACCOUNT : '/admin/Account.json',
        
        getConnectionStatus : function() {
            var rpc = new helenos.util.Rpc(this._CLUSTERCONNECTION);
            rpc.setHandleExceptions(false);
            return rpc.callSync('getConnectionStatus');
        },
        
        getAllConnections : function() {
          var rpc = new helenos.util.Rpc(this._CLUSTERCONNECTION);
          return rpc.callSync('loadAll');
        },
        
        getAllAccounts : function() {
          var rpc = new helenos.util.Rpc(this._ACCOUNT);
          return rpc.callSync('loadAll');
        },
                
        getConnectionByAlias : function(alias) {
            var rpc = new helenos.util.Rpc(this._CLUSTERCONNECTION);
            return rpc.callSync('getConnectionByAlias', alias);
        },
        
        saveNewPasword : function(passwordBean) {
            var rpc = new helenos.util.Rpc(this._ACCOUNT);
            return rpc.callSync('saveNewPassword', passwordBean);
        },
        
        createAccount : function(account) {
            var rpc = new helenos.util.Rpc(this._ACCOUNT);
            return rpc.callSync('createAccount', account);
        },
        
        loadUserByUsername : function(username) {
            var rpc = new helenos.util.Rpc(this._ACCOUNT);
            return rpc.callSync('loadUserByUsername', username);
        },
        
        deleteConnection : function(alias) {
            var rpc = new helenos.util.Rpc(this._CLUSTERCONNECTION);
            return rpc.callSync('delete', alias);
        },
        
        deleteAccount : function(username) {
            var rpc = new helenos.util.Rpc(this._ACCOUNT);
            return rpc.callSync('delete', username);
        },
        
        storeConnection : function(connection) {
            var rpc = new helenos.util.Rpc(this._CLUSTERCONNECTION);
            return rpc.callSync('store', connection);
        },
        
        activate : function(alias) {
            var rpc = new helenos.util.Rpc(this._CLUSTERCONNECTION);
            return rpc.callSync('activate', alias);
        },
        
        getConnectionsCount : function() {
          var rpc = new helenos.util.Rpc(this._CLUSTERCONNECTION);
          return rpc.callSync('getConnectionsCount');
        },
        
        getAccountsCount : function() {
          var rpc = new helenos.util.Rpc(this._ACCOUNT);
          return rpc.callSync('getAccountsCount');
        },
        
        dropKeyspace : function(keyspaceName) {
            var rpc = new helenos.util.Rpc(this._SCHEMAADMIN);
            rpc.callSync('dropKeyspace', keyspaceName);
        },
                
        describeClusterName : function() {
            var rpc = new helenos.util.Rpc(this._SCHEMA);
            return rpc.callSync('describeClusterName');
        },
        
        describeKeyspace  : function(keyspaceName) {
            var rpc = new helenos.util.Rpc(this._SCHEMA);
            return rpc.callSync('describeKeyspace', keyspaceName);
        },
        
        describeKeyspaces  : function() {
            var rpc = new helenos.util.Rpc(this._SCHEMA);
            return rpc.callSync('describeKeyspaces');
        },
        
        describeColumnFamily  : function(keyspaceName, columnFamily) {
            var rpc = new helenos.util.Rpc(this._SCHEMA);
            return rpc.callSync('describeColumnFamily', keyspaceName, columnFamily);
        },
        
        truncateColumnFamily  : function(keyspaceName, columnFamily) {
            var rpc = new helenos.util.Rpc(this._SCHEMAADMIN);
            return rpc.callSync('truncateColumnFamily', keyspaceName, columnFamily);
        },
        
        dropColumnFamily  : function(keyspaceName, columnFamily) {
            var rpc = new helenos.util.Rpc(this._SCHEMAADMIN);
            return rpc.callSync('dropColumnFamily', keyspaceName, columnFamily);
        },
        
        createColumnFamily  : function(formData) {
            var rpc = new helenos.util.Rpc(this._SCHEMAADMIN);
            return rpc.callSync('createColumnFamily', formData);
        },
        
        createKeyspace : function(formData) {
            var rpc = new helenos.util.Rpc(this._SCHEMAADMIN);
            return rpc.callSync('createKeyspace', formData);
        },
        
        queryCql : function(query) {
            var rpc = new helenos.util.Rpc(this._STANDARDQUERY);
            return rpc.callSync('cql', query);
        },
        
        queryPredicate : function(cfDef, query) {
            var rpc = null;
            if (cfDef.columnType == 'Standard') {
                rpc = new helenos.util.Rpc(this._STANDARDQUERY);
            } else {
                rpc = new helenos.util.Rpc(this._SUPERQUERY);
            }
            return rpc.callSync('predicate', query );
        },
        
        queryKeyRange : function(cfDef, query) {
            var rpc = null;
            if (cfDef.columnType == 'Standard') {
                rpc = new helenos.util.Rpc(this._STANDARDQUERY);
            } else {
                rpc = new helenos.util.Rpc(this._SUPERQUERY);
            }
            return rpc.callSync('keyRange', query );
        }
    }
});
