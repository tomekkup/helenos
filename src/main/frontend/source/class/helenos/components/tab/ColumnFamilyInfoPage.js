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
        
        var rpc = new helenos.util.Rpc('Cluster');
        var cfDef = rpc.callSync('describeColumnFamily', ksName, cfName);
        
    //this.__addNameAtom(ksDef);
    //this.__addBasicInfoGroup(ksDef);
    //this.__addCFTable(ksDef);
    },

    members :
    {
    
    }
});
