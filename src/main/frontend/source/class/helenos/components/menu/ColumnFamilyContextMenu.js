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
 
    construct : function(ksName, cfName, columnType)
    {
        this.base(arguments);
        this.__initMenuItems(ksName, cfName, columnType);
    },
    
    members : {
        
        __initMenuItems : function(ksName, cfName, columnType) {
            var propsButton = new qx.ui.menu.Button('Properties', 'icon/16/status/dialog-information.png');
            propsButton.setUserData('KSNAME', ksName);
            propsButton.setUserData('CFNAME', cfName);
            propsButton.addListener('execute', this.__showProperties);
            
            var browseButton = new qx.ui.menu.Button('Browse data', 'icon/16/apps/office-spreadsheet.png');
            browseButton.setUserData('KSNAME', ksName);
            browseButton.setUserData('CFNAME', cfName);
            browseButton.addListener('execute', this.__showBrowseByPredicatePane);
            
            var cqlButton = new qx.ui.menu.Button('CQL query', 'helenos/query-16.png');
            cqlButton.setUserData('KSNAME', ksName);
            cqlButton.setUserData('CFNAME', cfName);
            cqlButton.addListener('execute', this.__showBrowseByCqlQueryPane);
            
            if (columnType != 'Standard') {
                cqlButton.setEnabled(false);
            }
            
            var dropButton = new helenos.ui.menu.RoleAwareButton('Drop', 'icon/16/actions/edit-delete.png', helenos.model.Roles.ADMIN);
            dropButton.setUserData('KSNAME', ksName);
            dropButton.setUserData('CFNAME', cfName);
            dropButton.addListener('execute', this.__dropColumnFamily);
            
            var truncateButton = new helenos.ui.menu.RoleAwareButton('Truncate', 'icon/16/actions/edit-clear.png', helenos.model.Roles.ADMIN);
            truncateButton.setUserData('KSNAME', ksName);
            truncateButton.setUserData('CFNAME', cfName);
            truncateButton.addListener('execute', this.__truncateColumnFamily);
            
            this.add(propsButton);
            this.add(browseButton);
            this.add(cqlButton);
            this.add(new qx.ui.menu.Separator());
            this.add(dropButton);
            this.add(truncateButton);
        },
        
        __showProperties : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var cfName = e.getTarget().getUserData('CFNAME');
            helenos.util.GuiObserver.showColumnFamilyInfoTab(ksName, cfName);
        },
        
        /** 
        * @lint ignoreUndefined(dialog)
        */
        __truncateColumnFamily : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var cfName = e.getTarget().getUserData('CFNAME');
            dialog.Dialog.confirm(this.tr('loss.data.alert'), function(ret) {
                if (ret == true) {
                    helenos.util.RpcActionsProvider.truncateColumnFamily(ksName, cfName);
                    helenos.util.GuiObserver.refreshSchemaTree();
                }
            }, this);
        },
        
        __showBrowseByPredicatePane : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var cfName = e.getTarget().getUserData('CFNAME');
            helenos.util.GuiObserver.showBrowseByPredicateTab(ksName, cfName);
        },
        
        __showBrowseByCqlQueryPane : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var cfName = e.getTarget().getUserData('CFNAME');
            helenos.util.GuiObserver.showBrowseByCqlQueryTab(ksName, cfName);
        },
        
        /** 
        * @lint ignoreUndefined(dialog)
        */
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
