/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */

/*
#asset(qx/icon/${qx.icontheme}/16/actions/view-refresh.png)
#asset(qx/icon/${qx.icontheme}/16/devices/computer.png)
*/
qx.Class.define("helenos.components.SchemaPane",
{
    extend : qx.ui.container.Composite,

    /*
  *****************************************************************************
     MEMBERS
  *****************************************************************************
  */
 
    construct : function()
    {
        this.base(arguments);
        this.set({
            layout : new qx.ui.layout.VBox(),
            width : 240
        });

        this.__createButtons();
        this.__createSchemaTree();
        
        this.add(this.__btnToolbar);
        this.add(this.__schemaTree, {
            flex : 1
        });
    },

    members :
    {
        __schemaTree : null,
        __btnToolbar : null,
        
        __createButtons : function() {
            this.__btnToolbar = new qx.ui.toolbar.ToolBar();
            
            var refreshButton = new qx.ui.toolbar.Button("Refresh", "icon/16/actions/view-refresh.png");
            refreshButton.addListener('execute', this.refreshSchemaTree, this);
            
            var addKeyspaceButton = new qx.ui.toolbar.Button("Add keyspace", "icon/16/actions/list-add.png");
            addKeyspaceButton.addListener('execute', this.__addKeyspace, this);
            
            this.__btnToolbar.add(refreshButton);
            this.__btnToolbar.add(addKeyspaceButton);
        },
        
        __createSchemaTree : function() {
            this.__schemaTree = new qx.ui.tree.Tree();
            
            this.refreshSchemaTree();
        },
        
        /** 
        * @lint ignoreUndefined(dialog)
        */
        __addKeyspace : function() {
            var formData = {
                'keyspaceName' : {
                    'type'  : 'TextField',
                    'label' : 'Name', 
                    'value' : '',
                    'validation' : {
                        'required' : true
                    }
                },
                'strategyClass' : {
                    'type'  : 'SelectBox', 
                    'label' : 'Strategy class',
                    'value' : 1,
                    'options' : helenos.util.CassandraTypes.strategyClasses,
                    'validation' : {
                        'required' : true
                    }
                },
                'replicationFactor' : {
                    'type'  : 'TextField', 
                    'label' : 'Replication factor',
                    'value' : '1',
                    'validation' : {
                        'required' : true
                    }
                }
            };
            (new dialog.Form({
                "message"    : '<h3>Create new keyspace</h3>',
                "formData"    : formData,
                "allowCancel" : true,
                "callback"    : function(context, result) {
                    if (result != null) {
                        helenos.util.RpcActionsProvider.createKeyspace(result);
                        helenos.util.GuiObserver.refreshSchemaTree();
                    }
                },
                "context"     : this
            })).set({width : 450}).show();
        },
        
        refreshSchemaTree : function() {
            this.__schemaTree.resetSelection();
            this.__schemaTree.resetRoot();
          
            var clusterName = helenos.util.RpcActionsProvider.describeClusterName();
            this.__setRootItem(clusterName);
          
            var ksDefs = helenos.util.RpcActionsProvider.describeKeyspaces();
            this.__renderKeyspaces(ksDefs);
        },
        
        __renderKeyspaces : function(ksDefs) {
            if (ksDefs == undefined) {
                // AHDC
                return;
            }
            for (var i = 0; i < ksDefs.length; i++) {
                var ks = ksDefs[i];
                
                var ksItem = new qx.ui.tree.TreeFolder(ks.name);
                ksItem.set({
                    open: ks.name != 'system',
                    icon : 'helenos/keyspace.png',
                    contextMenu: new helenos.components.menu.KeyspaceContextMenu(ks.name)
                });
                this.__schemaTree.getRoot().add(ksItem);
                
                for (var j = 0; j < ks.cfDefs.length; j++) {
                    var cf = ks.cfDefs[j];
                    var cfItem = new qx.ui.tree.TreeFile(cf.name);
                    cfItem.set({
                        icon : cf.columnType == 'Super' ? 'helenos/supercf.png' : 'helenos/standardcf.png',
                        toolTip : this.__createColumnFamilyToolTip(cf),
                        contextMenu: new helenos.components.menu.ColumnFamilyContextMenu(ks.name, cf.name, cf.columnType)
                    });
                    cfItem.setIcon(cf.columnType == 'Super' ? 'helenos/supercf.png' : 'helenos/standardcf.png');
                    cfItem.setToolTip(this.__createColumnFamilyToolTip(cf));
                    ksItem.add(cfItem);
                }
            }
        },
        
        __createColumnFamilyToolTip : function(cfDef) {
            var ttt = 'Comparator: <b>' + cfDef.comparatorType.typeName + '</b><br/>';
            if (cfDef.subComparatorType != null) {
                ttt += 'Subcomparator: <b>' + cfDef.subComparatorType.typeName + '</b><br/>';
            }
            ttt += 'Key validator: <b>' + cfDef.keyValidationClass + '</b><br/>';
            ttt += 'Default validator: <b>' + cfDef.defaultValidationClass + '</b><br/>';
            var tooltip = new qx.ui.tooltip.ToolTip(ttt);
            tooltip.set({
                        width : 350,
                        rich : true,
                        showTimeout : 100
                        });
            return tooltip;
        },
        
        __setRootItem : function(clusterName) {
            var rootItem = new qx.ui.tree.TreeFolder(clusterName);
            rootItem.setOpen(true);
            rootItem.setIcon("icon/16/devices/computer.png");
            this.__schemaTree.setRoot(rootItem);
        }
    }
});
