/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/*
#asset(qx/icon/${qx.icontheme}/16/status/dialog-information.png)
#asset(qx/icon/${qx.icontheme}/16/actions/edit-delete.png)
#asset(qx/icon/${qx.icontheme}/16/actions/edit-clear.png)
#asset(qx/icon/${qx.icontheme}/16/apps/office-spreadsheet.png)
 */
qx.Class.define("helenos.components.menu.ColumnFamilyContextMenu",
{
    extend : qx.ui.menu.Menu,
 
    construct : function(ksName, cfName)
    {
        this.base(arguments);
        
        var propsButton = new qx.ui.menu.Button("Properties", "qx/icon/Oxygen/16/status/dialog-information.png");
        propsButton.setUserData('KSNAME', ksName);
        propsButton.setUserData('CFNAME', cfName);
        propsButton.addListener("execute", this.__showProperties);
        this.add(propsButton);
        
        var viewDataButton = new qx.ui.menu.Button("View data", "qx/icon/Oxygen/16/apps/office-spreadsheet.png");
        viewDataButton.setUserData('KSNAME', ksName);
        viewDataButton.setUserData('CFNAME', cfName);
        this.add(viewDataButton);
        
        this.add(new qx.ui.menu.Separator());
        
        var removeButton = new qx.ui.menu.Button("Remove", "qx/icon/Oxygen/16/actions/edit-delete.png");
        removeButton.setUserData('KSNAME', ksName);
        removeButton.setUserData('CFNAME', cfName);
        removeButton.addListener("execute", this.__removeColumnFamily);
        this.add(removeButton);
        
        var truncateButton = new qx.ui.menu.Button("Truncate", "qx/icon/Oxygen/16/actions/edit-clear.png");
        truncateButton.setUserData('KSNAME', ksName);
        truncateButton.setUserData('CFNAME', cfName);
        truncateButton.addListener("execute", this.__truncateColumnFamily);
        this.add(truncateButton);
    },
    
    members : {
        __showProperties : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var cfName = e.getTarget().getUserData('CFNAME');
            
            helenos.util.GuiObserver.showColumnFamilyInfoTab(ksName, cfName);
        },
        
        __truncateColumnFamily : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var cfName = e.getTarget().getUserData('CFNAME');
            dialog.Dialog.confirm(this.tr('loss.data.alert'), function(ret) {
                if (ret == true) {
                    helenos.util.RpcActionsProvider.truncateColumnFamily(ksName, cfName);
                    //helenos.util.GuiObserver.refreshSchemaTree();
                }
            }, this);
        },
        
        __removeColumnFamily : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var cfName = e.getTarget().getUserData('CFNAME');
            dialog.Dialog.confirm(this.tr('loss.data.alert'), function(ret) {
                if (ret == true) {
                    helenos.util.RpcActionsProvider.removeColumnFamily(ksName, cfName);
                    helenos.util.GuiObserver.refreshSchemaTree();
                }
            }, this);
        }
    }
});
