/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */

qx.Class.define('helenos.remote.ConnectionsTableModel',
{
    extend: qx.ui.table.model.Remote,
    
    construct: function() {
        this.base(arguments);
        this.setColumns(['Alias','Hosts','Cluster name', 'Active'],['alias','hosts','clusterName', 'active']);
    },

    members:
    {
        _loadRowCount : function() {
            this._onRowCountLoaded(helenos.util.RpcActionsProvider.getConnectionsCount());
        },
           
        _loadRowData : function() {
            this._onRowDataLoaded(helenos.util.RpcActionsProvider.getAllConnections());
        }
    }
});