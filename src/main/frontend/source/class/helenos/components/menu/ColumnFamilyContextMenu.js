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
#asset(qx/icon/${qx.icontheme}/16/apps/utilities-keyring.png)
*/
qx.Class.define('helenos.components.menu.ColumnFamilyContextMenu',
{
    extend : qx.ui.menu.Menu,
 
    construct : function(ksName, cfName)
    {
        this.base(arguments);
        
        var propsButton = new qx.ui.menu.Button('Properties', 'icon/16/status/dialog-information.png');
        propsButton.setUserData('KSNAME', ksName);
        propsButton.setUserData('CFNAME', cfName);
        propsButton.addListener('execute', this.__showProperties);
        this.add(propsButton);
        
        var browserMenu = new qx.ui.menu.Button('Browse', null, null, this.__getBrowseSubMenu(ksName, cfName));
        this.add(browserMenu);
        
        this.add(new qx.ui.menu.Separator());
        
        var dropButton = new qx.ui.menu.Button('Drop', 'icon/16/actions/edit-delete.png');
        dropButton.setUserData('KSNAME', ksName);
        dropButton.setUserData('CFNAME', cfName);
        dropButton.addListener('execute', this.__dropColumnFamily);
        this.add(dropButton);
        
        var truncateButton = new qx.ui.menu.Button('Truncate', 'icon/16/actions/edit-clear.png');
        truncateButton.setUserData('KSNAME', ksName);
        truncateButton.setUserData('CFNAME', cfName);
        truncateButton.addListener('execute', this.__truncateColumnFamily);
        this.add(truncateButton);
    },
    
    members : {
        __showProperties : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var cfName = e.getTarget().getUserData('CFNAME');
            
            helenos.util.GuiObserver.showColumnFamilyInfoTab(ksName, cfName);
        },
        
        __getBrowseSubMenu : function(ksName, cfName) {
            var menu = new qx.ui.menu.Menu();
          
            var byKeyButton = new qx.ui.menu.Button('By key', 'icon/16/apps/utilities-keyring.png');
            byKeyButton.setUserData('KSNAME', ksName);
            byKeyButton.setUserData('CFNAME', cfName);
            byKeyButton.addListener('execute', this.__showBrowseByKeyPane);
          
            var sliceButton = new qx.ui.menu.Button('Slice predicate', 'icon/16/apps/office-spreadsheet.png');
            sliceButton.setUserData('KSNAME', ksName);
            sliceButton.setUserData('CFNAME', cfName);
            //sliceButton.addListener('execute', this.__showBrowserPane);
            
            menu.add(byKeyButton);
            menu.add(sliceButton);
            return menu;
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
        
        __showBrowseByKeyPane : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var cfName = e.getTarget().getUserData('CFNAME');
            helenos.util.GuiObserver.showBrowseByKeyTab(ksName, cfName);
        },
        
        __dropColumnFamily : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var cfName = e.getTarget().getUserData('CFNAME');
            dialog.Dialog.confirm(this.tr('loss.data.alert'), function(ret) {
                if (ret == true) {
                    helenos.util.RpcActionsProvider.dropColumnFamily(ksName, cfName);
                    helenos.util.GuiObserver.refreshSchemaTree();
                }
            }, this);
        }
    }
});
