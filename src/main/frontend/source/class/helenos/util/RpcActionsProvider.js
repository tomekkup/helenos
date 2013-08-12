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
        
        __findParamClass : function(className) {
            switch (className)
            {
                case 'org.apache.cassandra.db.marshal.BytesType':
                case 'org.apache.cassandra.db.marshal.AsciiType':
                case 'org.apache.cassandra.db.marshal.UTF8Type':
                    return 'java.lang.String';
                    break;
                case 'org.apache.cassandra.db.marshal.LongType':
                case 'org.apache.cassandra.db.marshal.CounterColumnType':
                    return 'java.lang.Long';
                    break;
                case 'org.apache.cassandra.db.marshal.LexicalUUIDType':
                case 'org.apache.cassandra.db.marshal.TimeUUIDType':
                    return 'java.util.UUID';
                    break;
                default:
                    this.error('unknown class: ' + className);
                    return null;
                    break;
            }
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
        
        queryCql : function(cfDef, consistencyLevel, queryStr) {
            var query = this.__prepareQuery(cfDef, consistencyLevel);
            query.query = queryStr;
            
            var rpc = new helenos.util.Rpc(this._STANDARDQUERY);
            return rpc.callSync('cql', query);
        },
        
        __prepareQuery : function(cfDef, consistencyLevel) {
            var query = {};
            query.keyClass = this.__findParamClass(cfDef.keyValidationClass);
            if (cfDef.columnType == 'Standard') {
                query.nameClass = this.__findParamClass(cfDef.comparatorType.className);
            } else {
                query.nameClass = this.__findParamClass(cfDef.subComparatorType.className);
            }
            query.valueClass = this.__findParamClass(cfDef.defaultValidationClass);
            query.keyspace = cfDef.keyspaceName;
            query.columnFamily = cfDef.name;
            query.consistencyLevel = consistencyLevel;
            return query;
        },
        
        queryPredicate : function(cfDef, consistencyLevel, keyFrom, columnNames, nameStart, nameEnd, sName, reversed ) {
            var query = this.__prepareQuery(cfDef, consistencyLevel);
            
            query.keyFrom = keyFrom;
            query.columnNames = columnNames;
            query.nameStart = nameStart;
            query.nameEnd = nameEnd;
            query.reversed = reversed;
            
            var rpc;
            if (cfDef.columnType == 'Standard') {
                rpc = new helenos.util.Rpc(this._STANDARDQUERY);
            } else {
                rpc = new helenos.util.Rpc(this._SUPERQUERY);
                query.sNameClass = this.__findParamClass(cfDef.comparatorType.className);
                query.sName = sName;
            }
            return rpc.callSync('predicate', query );
        },
        
        queryKeyRange : function(cfDef, consistencyLevel, keyFrom, keyTo, columnNames, nameStart, nameEnd, sName, reversed ) {
            var query = this.__prepareQuery(cfDef, consistencyLevel);
            
            query.keyFrom = keyFrom;
            query.keyTo = keyTo;
            query.columnNames = columnNames;
            query.nameStart = nameStart;
            query.nameEnd = nameEnd;
            query.reversed = reversed;
            
            var rpc;
            if (cfDef.columnType == 'Standard') {
                rpc = new helenos.util.Rpc(this._STANDARDQUERY);
            } else {
                rpc = new helenos.util.Rpc(this._SUPERQUERY);
                query.sNameClass = this.__findParamClass(cfDef.comparatorType.className);
                query.sName = sName;
            }
            return rpc.callSync('keyRange', query );
        }
    }
});
