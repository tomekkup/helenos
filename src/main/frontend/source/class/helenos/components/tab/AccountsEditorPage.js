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
qx.Class.define("helenos.components.tab.AccountsEditorPage",
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
            label: 'Accounts',
            icon: 'helenos/users-16.png',
            layout: new qx.ui.layout.VBox(3, 'top')
        });

        this.__addMainContainer();
    },

    members :
    {
        __accountsTable : null,
        __addButton : null,
        __editButton : null,
        __deleteButton : null,
    
        __addMainContainer : function() {
            this.__accountsTable = new helenos.ui.table.Table(new helenos.remote.AccountsTableModel());
            
            this.__accountsTable.getTableColumnModel().setDataCellRenderer(1, new helenos.ui.table.cellrenderer.Authorities());
            this.__accountsTable.getTableColumnModel().setDataCellRenderer(2, new qx.ui.table.cellrenderer.Password());
            this.__accountsTable.getTableColumnModel().setDataCellRenderer(3, new qx.ui.table.cellrenderer.Boolean());
            this.__accountsTable.getSelectionModel().addListener('changeSelection', this.__onTableSelectionChanged, this);
            this.__accountsTable.setContextMenuHandlers([0,1,2,3]);
            this.__accountsTable.setColumnsWidth([18,38,35,8]);
            
            var gb = new qx.ui.groupbox.GroupBox('Accounts');
            gb.setLayout(new qx.ui.layout.VBox(5));
            
            var sp = new qx.ui.core.scroll.ScrollPane();
            sp.add(this.__accountsTable);
            
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
            this.__addButton.addListener('execute', this.__onAddAccount, this);
            
            this.__deleteButton = new qx.ui.form.Button('Delete', 'icon/16/actions/list-remove.png');
            this.__deleteButton.addListener('execute', this.__onDeleteAccount, this);
            this.__deleteButton.setEnabled(false);
            
            this.__editButton = new qx.ui.form.Button('Reset password', 'icon/16/actions/edit-cut.png');
            this.__editButton.addListener('execute', this.__onEditPasswdAccount, this);
            this.__editButton.setEnabled(false);
            
            pane.add(this.__addButton);
            pane.add(this.__editButton);
            pane.add(this.__deleteButton);
            return pane;
        },
        
        /** 
        * @lint ignoreUndefined(dialog)
        */
        __onAddAccount : function(e) {
            var formData =  
            {
                'username' : 
                {
                    'type'  : "TextField",
                    'label' : "User Name", 
                    'value' : "",
                    "validation" : {
                        "required" : true
                    } 
                },
                'password' :
                {
                    'type'  : "passwordfield",
                    'label' : "Password",
                    'value' : "",
                    'validation' : {
                        "required" : true
                    }
                },
                'authority'   : 
                {
                    'type'  : "SelectBox", 
                    'label' : "Authority",
                    'value' : "ROLE_USER",
                    'options' : [
                    {
                        'label' : "ROLE_USER", 
                        'value' : "ROLE_USER"
                    }, 
{
                        'label' : "ROLE_ADMIN",    
                        'value' : "ROLE_ADMIN"
                    }
                    ]
                }
            };
            
            var _this = this;
            dialog.Dialog.form("<h3>Add account</h3>",formData, function( result )
            {
                result['enabled'] = true;
                result['authorities'] = [result['authority']];
                result['authority'] = undefined;
                helenos.util.RpcActionsProvider.createAccount(result);
                _this._reloadAccountsTable();
            }, _this);
        },
        
        /** 
        * @lint ignoreUndefined(dialog)
        */
        __onEditPasswdAccount : function(e) {
            var username = this.__getSelectedUsername();
            var formData =  
            {
                'password1' : 
                {
                    'type'  : "passwordfield",
                    'label' : "Password", 
                    'value' : "",
                    'validation' : {
                        "required" : true
                    } 
                },
                'password2' :
                {
                    'type'  : "passwordfield",
                    'label' : "Repeat password",
                    'value' : "",
                    'validation' : {
                        "required" : true
                    }
                }
            };
            dialog.Dialog.form("<h3>Reset password for: " + username + "</h3>",formData, function( result )
            {
                result['username'] = username;
                helenos.util.RpcActionsProvider.saveNewPasword(result);
            }, this);
            
        },
        
        /** 
        * @lint ignoreUndefined(dialog)
        */
        __onDeleteAccount : function(e) {
            var selectionModel = this.__accountsTable.getSelectionModel();
            var selectedRow = selectionModel.getSelectedRanges()[0].minIndex;
            
            var username = this.__accountsTable.getTableModel().getValue(0, selectedRow);
            
            var _this = this;
            dialog.Dialog.confirm(this.tr('are.you.sure'), function(ret) {
                if (ret == true) {
                    helenos.util.RpcActionsProvider.deleteAccount(username);
                    this._reloadAccountsTable();
                }
            }, _this);
            
        },
        
        _reloadAccountsTable : function() {
            this.__accountsTable.resetSelection();
            this.__accountsTable.getTableModel().reloadData();
        },
        
        __getSelectedUsername : function() {
            var selectedRow = this.__accountsTable.getSelectionModel().getSelectedRanges()[0].minIndex;
            return this.__accountsTable.getTableModel().getValue(0, selectedRow);
        },
        
        __onTableSelectionChanged : function(e) {
            var isSelected = !this.__accountsTable.getSelectionModel().isSelectionEmpty();
            this.__deleteButton.setEnabled(isSelected);
            this.__editButton.setEnabled(isSelected);
        }
    }
});
