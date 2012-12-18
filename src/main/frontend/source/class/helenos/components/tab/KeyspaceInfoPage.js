/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/*
#asset(qx/icon/${qx.icontheme}/64/status/dialog-information.png)
*/
qx.Class.define("helenos.components.tab.KeyspaceInfoPage",
{
    extend : helenos.components.tab.AbstractCloseablePage,

    /*
  *****************************************************************************
     MEMBERS
  *****************************************************************************
  */
 
    construct : function(ksName)
    {
        this.base(arguments);
        this.set({
            label: ksName,
            icon: 'helenos/keyspace.png',
            layout: new qx.ui.layout.VBox(3, 'top')
        });
        
        
        var ksDef = helenos.util.RpcActionsProvider.describeKeyspace(ksName);
        
        this.__addNameAtom(ksDef);
        this.__addBasicInfoGroup(ksDef);
        this.__addCFTable(ksDef);
    },

    members :
    {
        __addNameAtom : function(ksDef) {
            var atom = new helenos.ui.RichAtom('Keyspace: <b>' + ksDef.name + '</b>', "icon/64/status/dialog-information.png");
            atom.setFont(new qx.bom.Font(22, ["OpenSansRegular", "Helvetica Neue", "Helvetica", "Arial", "sans-serif"]));
            this.add(atom);
        },
        
        __addBasicInfoGroup : function(ksDef) {
            var gb = new helenos.ui.GroupBoxV('Basic');
            
            var a1 = new helenos.ui.RichAtom('Strategy class: <b>' + ksDef.strategyClass + '</b>');
            var a2 = new helenos.ui.RichAtom('Replication factor: <b>' + ksDef.replicationFactor + '</b>');
            
            gb.add(a1);
            gb.add(a2);
            this.add(gb);
        },
        
        __addCFTable : function(ksDef) {
            var tableModel = new qx.ui.table.model.Simple();
            tableModel.setColumns(['Id','Name','Type', 'Comparator', 'Sub comparator', 'Key validation','Default validation','Row cache', 'Key cache', 'GC grace sec'],['id','name','columnType','comparatorType_typeName', 'subComparatorType_typeName', 'keyValidationClass_normalized','defaultValidationClass_normalized','rowCacheSize', 'keyCacheSize', 'gcGraceSeconds']);
            
            tableModel.setDataAsMapArray(ksDef.cfDefs.map(this.__convertCF));
            var table = new helenos.ui.table.Table(tableModel);
            table.setContextMenuHandlers([0,1]);
            table.setColumnsWidth([10,10,10,10,10,10,10,10,10,10]);
            var gb = new qx.ui.groupbox.GroupBox('Column families');
            gb.setLayout(new qx.ui.layout.VBox());
            
            var sp = new qx.ui.core.scroll.ScrollPane();
            sp.add(table);
            
            gb.add(sp, {flex : 1});
            
            this.add(gb, {flex : 1});
        },
        
        //TODO move 'replace' code to some helper obj
        __convertCF : function(obj ) {
            obj['comparatorType_typeName'] = obj.comparatorType.typeName;
            obj['subComparatorType_typeName'] = obj.subComparatorType ? obj.subComparatorType.typeName : '';
            obj['keyValidationClass_normalized'] = obj.keyValidationClass.replace('org.apache.cassandra.db.marshal.','');
            obj['defaultValidationClass_normalized'] = obj.defaultValidationClass.replace('org.apache.cassandra.db.marshal.','');
            
            return obj;
        }
        
    }
});
