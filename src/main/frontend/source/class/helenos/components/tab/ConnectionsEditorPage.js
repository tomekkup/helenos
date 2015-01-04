/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/*
#asset(qx/icon/${qx.icontheme}/16/actions/list-add.png)
#asset(qx/icon/${qx.icontheme}/16/actions/edit-cut.png)
#asset(qx/icon/${qx.icontheme}/16/actions/list-remove.png)
*/
qx.Class.define("helenos.components.tab.ConnectionsEditorPage",
{
    extend : helenos.components.tab.AbstractCloseablePage,

    /*
  *****************************************************************************
     MEMBERS
  *****************************************************************************
  */
 
    construct : function()
    {
        this.base(arguments);
        this.set({
            label: 'Connections',
            icon: 'helenos/users-16.png',
            layout: new qx.ui.layout.VBox(3, 'top')
        });

        this.__addMainContainer();
    },

    members :
    {
        __connectionsTable : null,
        __addButton : null,
        __connectButton : null,
        __editButton : null,
        __deleteButton : null,
    
        __addMainContainer : function() {
            this.__connectionsTable = new helenos.ui.table.Table(new helenos.remote.ConnectionsTableModel());
            this.__connectionsTable.getTableColumnModel().setDataCellRenderer(3, new qx.ui.table.cellrenderer.Boolean());
            this.__connectionsTable.getSelectionModel().addListener('changeSelection', this.__onTableSelectionChanged, this);
            this.__connectionsTable.setContextMenuHandlers([0,1,2]);
            this.__connectionsTable.setColumnsWidth([15,45,25,15]);
            
            var gb = new qx.ui.groupbox.GroupBox('Connections');
            gb.setLayout(new qx.ui.layout.VBox(5));
            
            var sp = new qx.ui.core.scroll.ScrollPane();
            sp.add(this.__connectionsTable);
            
            gb.add(sp, {
                flex : 1
            });
            gb.add(this.__getActionButtonsPane());
            this.add(gb, {
                flex : 1
            });
        },
        
        __getActionButtonsPane : function() {
            var pane = new qx.ui.container.Composite(new qx.ui.layout.HBox(5, 'right'));
            this.__addButton = new qx.ui.form.Button('Add', 'icon/16/actions/list-add.png');
            this.__addButton.addListener('execute', this.__onAddConnection, this);
            
            this.__connectButton = new qx.ui.form.Button('Connect to', 'helenos/connect.png');
            this.__connectButton.addListener('execute', this.__onConnectToConnection, this);
            this.__connectButton.setEnabled(false);
            
            this.__deleteButton = new qx.ui.form.Button('Delete', 'icon/16/actions/list-remove.png');
            this.__deleteButton.addListener('execute', this.__onDeleteConnection, this);
            this.__deleteButton.setEnabled(false);
            
            this.__editButton = new qx.ui.form.Button('Edit', 'icon/16/actions/edit-cut.png');
            this.__editButton.addListener('execute', this.__onEditConnection, this);
            this.__editButton.setEnabled(false);
            
            pane.add(this.__addButton);
            pane.add(this.__editButton);
            pane.add(this.__connectButton);
            pane.add(this.__deleteButton);
            return pane;
        },
        
        /** 
        * @lint ignoreUndefined(dialog)
        */
        __onAddConnection : function(e) {
            var formData = {
                'alias' : {
                    'type'  : 'TextField',
                    'label' : 'Alias (unique)', 
                    'value' : '',
                    'validation' : {
                        'required' : true
                    }
                },
                'hosts' : {
                    'type'  : 'TextField',
                    'label' : 'Hosts (comma separated)', 
                    'value' : '',
                    'validation' : {
                        'required' : true
                    }
                },
                'clusterName' :
                {
                    'type'  : 'TextArea',
                    'label' : 'Cluster name',
                    'lines' : 4,
                    'value' : '',
                    'validation' : {
                        'required' : true
                    }
                },
                'username' : {
                    'type'  : 'TextField',
                    'label' : '[Credentials] Username', 
                    'value' : '',
                    'validation' : {
                        'required' : false
                    }
                },
                'password' : {
                    'type'  : 'PasswordField',
                    'label' : '[Credentials] Password', 
                    'value' : '',
                    'validation' : {
                        'required' : false
                    }
                }
            };
            var _this = this;
            (new dialog.Form({
                "message"    : '<h3>Create new connection</h3>',
                "formData"    : formData,
                "allowCancel" : true,
                "callback"    : function(result) {
                    if (result != null) {
                        result['active'] = false;
                        result['credentials'] = {};
                        result['credentials']['username'] = result['username'];
                        result['credentials']['password'] = result['password'];
                        delete result['username'];
                        delete result['password'];
                        helenos.util.RpcActionsProvider.createConnection(result);
                        _this._reloadConnectionsTable();
                    }
                },
                "context"     : this
            })).set({width : 550}).show();
        },
        
        /** ,k
        * @lint ignoreUndefined(dialog)
        */
        __onEditConnection : function(e) {
            var alias = this.__getSelectedAlias();
            var cc = helenos.util.RpcActionsProvider.getConnectionByAlias(alias);
            var formData = {
                'hosts' : {
                    'type'  : 'TextField',
                    'label' : 'Hosts (comma separated)', 
                    'value' : cc.hosts,
                    'validation' : {
                        'required' : true
                    }
                },
                'clusterName' :
                {
                    'type'  : 'TextField',
                    'label' : 'Cluster name',
                    'value' : cc.clusterName,
                    'validation' : {
                        'required' : true
                    }
                },
                'username' : {
                    'type'  : 'TextField',
                    'label' : '[Credentials] Username', 
                    'value' : cc.credentials.username,
                    'validation' : {
                        'required' : false
                    }
                },
                'password' : {
                    'type'  : 'PasswordField',
                    'label' : '[Credentials] Password', 
                    'value' : cc.credentials.password,
                    'validation' : {
                        'required' : false
                    }
                }
            };
            var _this = this;
            (new dialog.Form({
                "message"    : '<h3>Edit connection</h3>',
                "formData"    : formData,
                "allowCancel" : true,
                "callback"    : function(result) {
                    if (result != null) {
                        result['alias'] = cc.alias;
                        result['active'] = cc.active;
                        result['credentials'] = {};
                        result['credentials']['username'] = result['username'];
                        result['credentials']['password'] = result['password'];
                        delete result['username'];
                        delete result['password'];
                        helenos.util.RpcActionsProvider.storeConnection(result);
                        _this._reloadConnectionsTable();
                    }
                },
                "context"     : this
            })).set({width : 550}).show();
        },
        
        /** 
        * @lint ignoreUndefined(dialog)
        */
        __onDeleteConnection : function(e) {
            var selectionModel = this.__connectionsTable.getSelectionModel();
            var selectedRow = selectionModel.getSelectedRanges()[0].minIndex;
            var active = this.__connectionsTable.getTableModel().getValue(3, selectedRow);
            
            if (active == true) {
                dialog.Dialog.error('Active connection can not be deleted !');
            } else {
                var alias = this.__connectionsTable.getTableModel().getValue(0, selectedRow);
                dialog.Dialog.confirm(this.tr('are.you.sure'), function(ret) {
                    if (ret == true) {
                        helenos.util.RpcActionsProvider.deleteConnection(alias);
                        this._reloadConnectionsTable();
                    }
                }, this);
            }
        },
        
        _reloadConnectionsTable : function() {
            this.__connectionsTable.resetSelection();
            this.__connectionsTable.getTableModel().reloadData();
        },
        
        __getSelectedAlias : function() {
            var selectedRow = this.__connectionsTable.getSelectionModel().getSelectedRanges()[0].minIndex;
            return this.__connectionsTable.getTableModel().getValue(0, selectedRow);
        },
        
        /** 
        * @lint ignoreUndefined(dialog)
        */
        __onConnectToConnection : function(e) {
            var alias = this.__getSelectedAlias();
            dialog.Dialog.confirm(this.tr('are.you.sure'), function(ret) {
                if (ret == true) {
                    helenos.util.RpcActionsProvider.activate(alias);
                    this._reloadConnectionsTable();
                    helenos.util.GuiObserver.refreshSchemaTree();
                }
            }, this);
        },
        
        __onTableSelectionChanged : function(e) {
            var isSelected = !this.__connectionsTable.getSelectionModel().isSelectionEmpty();
            this.__connectButton.setEnabled(isSelected);
            this.__deleteButton.setEnabled(isSelected);
            this.__editButton.setEnabled(isSelected);
        }
    }
});
