/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/*
#asset(helenos/keyspace.png)
#asset(qx/icon/${qx.icontheme}/64/status/dialog-information.png)
*/
qx.Class.define("helenos.components.tab.ColumnFamilyInfoPage",
{
    extend : helenos.components.tab.AbstractCloseablePage,
 
    construct : function(ksName, cfName)
    {
        this.base(arguments);
        this.set({
            label: cfName,
            icon: 'helenos/standardcf.png',
            layout: new qx.ui.layout.VBox(3, 'top')
        });
        
        var cfDef = helenos.util.RpcActionsProvider.describeColumnFamily(ksName, cfName);
        
        this.__addNameAtom(cfDef);
        this.__addInfoPane(cfDef);
        this.__addMetadataTable(cfDef);
        this.__addCommentArea(cfDef);
    },

    members :
    {
        __addNameAtom : function(cfDef) {
            var atom = new helenos.ui.RichAtom('Column family: <b>' + cfDef.name + '</b> [' + cfDef.id + ']', "icon/64/status/dialog-information.png");
            atom.setFont(new qx.bom.Font(22, ["OpenSansRegular", "Helvetica Neue", "Helvetica", "Arial", "sans-serif"]));
            this.add(atom);
        },
        
        __addInfoPane : function(cfDef) {
            var composite = new qx.ui.container.Composite(new qx.ui.layout.HBox());
            
            composite.add(this.__getTypesInfoPane(cfDef), {flex : 1});
            composite.add(this.__getCacheInfoPane(cfDef), {flex : 1});
            composite.add(this.__getMemInfoPane(cfDef), {flex : 1});
            
            this.add(composite);
        },
        
        __addCommentArea : function(cfDef) {
            var gb = new qx.ui.groupbox.GroupBox('Comment');
            gb.setLayout(new qx.ui.layout.VBox());
            var ta = new qx.ui.form.TextField(cfDef.comment).set({readOnly : true})
            
            gb.add(ta, {flex : 1});
            this.add(gb);
        },
        
        __getCacheInfoPane : function(cfDef) {
            var gb = new qx.ui.groupbox.GroupBox('Cache');
            gb.setLayout(new qx.ui.layout.VBox());
            
            
            gb.add(new helenos.ui.RichAtom('Row cache size: <b>' + cfDef.rowCacheSize + '</b>'));
            gb.add(new helenos.ui.RichAtom('Row cache save period: <b>' + cfDef.rowCacheSavePeriodInSeconds.className + ' seconds</b>'));
            gb.add(new helenos.ui.RichAtom('Row cache provider: <b>' + cfDef.rowCacheProvider + '</b>'));
            gb.add(new helenos.ui.RichAtom('Row cache KTS: <b>' + cfDef.rowCacheKeysToSave + '</b>'));
            gb.add(new helenos.ui.RichAtom('Key cache size: <b>' + cfDef.keyCacheSize + '</b>'));
            gb.add(new helenos.ui.RichAtom('Key cache save period: <b>' + cfDef.keyCacheSavePeriodInSeconds + ' seconds</b>'));
            gb.add(new helenos.ui.RichAtom('Read repair change: <b>' + cfDef.readRepairChance + '</b>'));
            
            gb.add(new helenos.ui.RichAtom('GC grace: <b>' + cfDef.gcGraceSeconds + ' seconds</b>'));
            
            return gb;
        },
        
        __getMemInfoPane : function(cfDef) {
            var gb = new qx.ui.groupbox.GroupBox('Memtable & threshold');
            gb.setLayout(new qx.ui.layout.VBox());
            
            gb.add(new helenos.ui.RichAtom('Max compaction threshold: <b>' + cfDef.maxCompactionThreshold + '</b>'));
            gb.add(new helenos.ui.RichAtom('Min compaction threshold: <b>' + cfDef.minCompactionThreshold + '</b>'));
            gb.add(new helenos.ui.RichAtom('Memtable operations: <b>' + cfDef.memtableOperationsInMillions + ' mln</b>'));
            gb.add(new helenos.ui.RichAtom('Memtable throughput: <b>' + cfDef.memtableThroughputInMb + ' Mb</b>'));
            gb.add(new helenos.ui.RichAtom('Memtable flush: <b>' + cfDef.memtableFlushAfterMins + '</b>'));
            
            return gb;
        },
        
        __getTypesInfoPane : function(cfDef) {
            var gb = new qx.ui.groupbox.GroupBox('Types');
            gb.setLayout(new qx.ui.layout.VBox());
            
            gb.add(new helenos.ui.RichAtom('Column: <b>' + cfDef.columnType + '</b>'));
            gb.add(new helenos.ui.RichAtom('Comparator: <b>' + cfDef.comparatorType.className.replace('org.apache.cassandra.db.marshal.','') + '</b>'));
            if (cfDef.subComparatorType != null) {
                gb.add(new helenos.ui.RichAtom('Sub comparator: <b>' + cfDef.subComparatorType.className.replace('org.apache.cassandra.db.marshal.','') + '</b>'));
            }
            
            gb.add(new helenos.ui.RichAtom('Key validation: <b>' + cfDef.keyValidationClass.replace('org.apache.cassandra.db.marshal.','') + '</b>'));
            gb.add(new helenos.ui.RichAtom('Default validation: <b>' + cfDef.defaultValidationClass.replace('org.apache.cassandra.db.marshal.','') + '</b>'));
            return gb;
        },
        
        __addMetadataTable : function(cfDef) {
            var tableModel = new qx.ui.table.model.Simple();
            tableModel.setColumns(['Name','Validation class', 'Index name', 'Index type'],['name','validationClass','indexName', 'indexType']);
            
            tableModel.setDataAsMapArray(cfDef.columnMetadata);
            var table = new qx.ui.table.Table(tableModel);
            
            var gb = new qx.ui.groupbox.GroupBox('Column metadata');
            gb.setLayout(new qx.ui.layout.VBox());
            
            var sp = new qx.ui.core.scroll.ScrollPane();
            sp.add(table);
            
            gb.add(sp, {flex : 1});
            this.add(gb, {flex : 1});
        }
    }
});
