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
        dropKeyspace : function(keyspaceName) {
            var rpc = new helenos.util.Rpc('Cluster');
            rpc.callSync('dropKeyspace', keyspaceName);
        },
        
        describeClusterName : function() {
            var rpc = new helenos.util.Rpc('Cluster');
            return rpc.callSync('describeClusterName');
        },
        
        describeKeyspaces  : function() {
            var rpc = new helenos.util.Rpc('Cluster');
            return rpc.callSync('describeKeyspaces');
        },
        
        describeColumnFamily  : function(keyspaceName, columnFamily) {
            var rpc = new helenos.util.Rpc('Cluster');
            return rpc.callSync('describeColumnFamily', keyspaceName, columnFamily);
        },
        
        truncateColumnFamily  : function(keyspaceName, columnFamily) {
            var rpc = new helenos.util.Rpc('Cluster');
            return rpc.callSync('truncateColumnFamily', keyspaceName, columnFamily);
        },
        
        dropColumnFamily  : function(keyspaceName, columnFamily) {
            var rpc = new helenos.util.Rpc('Cluster');
            return rpc.callSync('dropColumnFamily', keyspaceName, columnFamily);
        },
        
        createColumnFamily  : function(formData) {
            var rpc = new helenos.util.Rpc('Cluster');
            return rpc.callSync('createColumnFamily', formData);
        }
    }
});
