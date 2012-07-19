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
qx.Class.define("helenos.components.menu.KeyspaceContextMenu",
{
    extend : qx.ui.menu.Menu,
 
    construct : function(ksName)
    {
        this.base(arguments);
        
        var propsButton = new qx.ui.menu.Button("Properties", "qx/icon/Oxygen/16/status/dialog-information.png");
        propsButton.setUserData('KSNAME', ksName);
        propsButton.addListener("execute", this.__showProperties);
        this.add(propsButton);
        
        this.add(new qx.ui.menu.Separator());
        
        var addCFButton = new qx.ui.menu.Button("Add column family", "qx/icon/Oxygen/16/actions/list-add.png");
        addCFButton.setUserData('KSNAME', ksName);
        addCFButton.addListener("execute", this.__addColumnFamily);
        this.add(addCFButton);
        
        var dropButton = new qx.ui.menu.Button("Remove", "qx/icon/Oxygen/16/actions/edit-delete.png");
        dropButton.setUserData('KSNAME', ksName);
        dropButton.addListener("execute", this.__dropKeyspace);
        this.add(dropButton);
    },
    
    members : {
        __showProperties : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            helenos.util.GuiObserver.showKeyspaceInfoTab(ksName);
        },
        
        __dropKeyspace : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            dialog.Dialog.confirm(this.tr('loss.data.alert'), function(ret) {
                if (ret == true) {
                    helenos.util.RpcActionsProvider.dropKeyspace(ksName);
                    helenos.util.GuiObserver.refreshSchemaTree();
                }
            }, this);
        },
        
        __addColumnFamily : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var formData = {
                'name' : {
                    'type'  : "TextField",
                    'label' : "Name", 
                    'value' : "",
                    "validation" : {
                        "required" : true
                    }
                },
                'column' : {
                    'type'  : "SelectBox", 
                    'label' : "Column",
                    'value' : 1,
                    'options' : helenos.util.CassandraTypes.columnTypes,
                    "validation" : {
                        "required" : true
                    }
                },
                'comparator' : {
                    'type'  : "SelectBox", 
                    'label' : "Comparator",
                    'value' : 1,
                    'options' : helenos.util.CassandraTypes.comparatorTypes,
                    "validation" : {
                        "required" : true
                    }
                },
                'subComparator' : {
                    'type'  : "SelectBox", 
                    'label' : "Subcomparator",
                    'value' : 1,
                    'options' : helenos.util.CassandraTypes.comparatorTypes,
                    "validation" : {
                        "required" : true
                    }
                }
                ,
                'keyValidationclass' : {
                    'type'  : "SelectBox", 
                    'label' : "Key validation class",
                    'value' : 1,
                    'options' : helenos.util.CassandraTypes.validationClasses,
                    "validation" : {
                        "required" : true
                    }
                },
                'defaultValidationclass' : {
                    'type'  : "SelectBox", 
                    'label' : "Default validation class",
                    'value' : 1,
                    'options' : helenos.util.CassandraTypes.validationClasses,
                    "validation" : {
                        "required" : true
                    }
                },
                'comment' :
                {
                    'type'  : "TextArea",
                    'label' : "Comment",
                    'lines' : 4,
                    'value' : ""
                }
            };
            dialog.Dialog.form('<b>Create new column family</b>', formData, function(result) {
                
                });
        }
    }
});
