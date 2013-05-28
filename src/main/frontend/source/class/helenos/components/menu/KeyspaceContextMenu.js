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
#asset(qx/icon/${qx.icontheme}/16/actions/list-add.png)
#asset(qx/icon/${qx.icontheme}/16/actions/edit-delete.png)
 */
qx.Class.define('helenos.components.menu.KeyspaceContextMenu',
{
    extend : qx.ui.menu.Menu,
 
    construct : function(ksName)
    {
        this.base(arguments);
        this.__initMenuItems(ksName);
    },
    
    members : {
        
        __initMenuItems : function(ksName) {
            var propsButton = new qx.ui.menu.Button('Properties', 'icon/16/status/dialog-information.png');
            propsButton.setUserData('KSNAME', ksName);
            propsButton.addListener('execute', this.__showProperties);
            
            var addCFButton = new helenos.ui.menu.RoleAwareButton('Add column family', 'icon/16/actions/list-add.png', helenos.model.Roles.ADMIN);
            addCFButton.setUserData('KSNAME', ksName);
            addCFButton.addListener('execute', this.__addColumnFamily);
            
            var dropButton = new helenos.ui.menu.RoleAwareButton('Drop', 'icon/16/actions/edit-delete.png', helenos.model.Roles.ADMIN);
            dropButton.setUserData('KSNAME', ksName);
            dropButton.addListener('execute', this.__dropKeyspace);
            
            this.add(propsButton);
            this.add(new qx.ui.menu.Separator());
            this.add(addCFButton);
            this.add(dropButton);
        },
        
        __showProperties : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            helenos.util.GuiObserver.showKeyspaceInfoTab(ksName);
        },
        
        /** 
        * @lint ignoreUndefined(dialog)
        */
        __dropKeyspace : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            dialog.Dialog.confirm(this.tr('loss.data.alert'), function(ret) {
                if (ret == true) {
                    helenos.util.RpcActionsProvider.dropKeyspace(ksName);
                    helenos.util.GuiObserver.refreshSchemaTree();
                }
            }, this);
        },
        
        /** 
        * @lint ignoreUndefined(dialog)
        */
        __addColumnFamily : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var formData = {
                'name' : {
                    'type'  : 'TextField',
                    'label' : 'Name', 
                    'value' : '',
                    'validation' : {
                        'required' : true
                    }
                },
                'columnType' : {
                    'type'  : 'SelectBox', 
                    'label' : 'Column',
                    'value' : 1,
                    'options' : helenos.util.Constants.columnTypes,
                    'validation' : {
                        'required' : true
                    }
                },
                'comparatorType' : {
                    'type'  : 'SelectBox', 
                    'label' : 'Comparator',
                    'value' : 1,
                    'options' : helenos.util.Constants.comparatorTypes,
                    'validation' : {
                        'required' : true
                    }
                },
                'subComparatorType' : {
                    'type'  : 'SelectBox', 
                    'label' : 'Subcomparator',
                    'value' : 1,
                    'options' : helenos.util.Constants.comparatorTypes
                }
                ,
                'keyValidationclass' : {
                    'type'  : 'SelectBox', 
                    'label' : 'Key validation class',
                    'value' : 5,
                    'options' : helenos.util.Constants.validationClasses,
                    'validation' : {
                        'required' : true
                    }
                },
                'defaultValidationclass' : {
                    'type'  : 'SelectBox', 
                    'label' : 'Default validation class',
                    'value' : 4,
                    'options' : helenos.util.Constants.validationClasses,
                    'validation' : {
                        'required' : true
                    }
                },
                'gcGraceSeconds' : {
                    'type'  : 'TextField',
                    'label' : 'GC grace seconds', 
                    'value' : '86400',
                    'validation' : {
                        'required' : true
                        //,'validator' : qx.util.Validate.number()
                    }
                },
                'comment' :
                {
                    'type'  : 'TextArea',
                    'label' : 'Comment',
                    'lines' : 4,
                    'value' : ''
                }
            };
            dialog.Dialog.form('<h4>Create new column family</h4>', formData, function(result) {
                if (result != null) {
                    result['keyspaceName'] = ksName;
                    helenos.util.RpcActionsProvider.createColumnFamily(result);
                    helenos.util.GuiObserver.refreshSchemaTree();
                }
            }, this);
        }
    }
});
