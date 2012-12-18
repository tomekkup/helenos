/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */

qx.Class.define('helenos.remote.AccountsTableModel',
{
    extend: qx.ui.table.model.Remote,
    
    construct: function() {
        this.base(arguments);
        this.setColumns(['Username','Authorities', 'Enabled'],['username','authorities', 'enabled']);
    },

    members:
    {
        _loadRowCount : function() {
            this._onRowCountLoaded(helenos.util.RpcActionsProvider.getAccountsCount());
        },
           
        _loadRowData : function() {
            this._onRowDataLoaded(helenos.util.RpcActionsProvider.getAllAccounts());
        }
    }
});