/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/*
#asset(qx/icon/${qx.icontheme}/16/actions/system-search.png)
*/
qx.Class.define("helenos.components.tab.BrowserPage",
{
    extend : helenos.components.tab.AbstractCloseablePage,
 
    construct : function(ksName, cfName)
    {
        this.base(arguments);
        this.set({
            layout : new qx.ui.layout.VBox(3, 'top'),
            icon : 'icon/16/apps/office-spreadsheet.png',
            label: (ksName + ' : ' + cfName)
        });
        
        var cfDef = helenos.util.RpcActionsProvider.describeColumnFamily(ksName, cfName);
        
        this.__addFilterPane(cfDef);
    },

    members :
    {
        __keyStart : null,
        __keyEnd : null,
        
        __performSearch : function(e) {
            
        },
        
        __addFilterPane : function(cfDef) {
            this.__keyStart = new qx.ui.form.TextField();
            this.__keyEnd = new qx.ui.form.TextField();
            var searchButton = new qx.ui.form.Button('Search', 'icon/16/actions/system-search.png');
            searchButton.addListener("execute", this.__performSearch, this);
            
            var filterGB = new qx.ui.groupbox.GroupBox('Filter');
            filterGB.setLayout(new qx.ui.layout.HBox(8));
            
            this.add(filterGB);
            filterGB.add(new qx.ui.basic.Label('Key:'));
            filterGB.add(this.__keyStart);
            filterGB.add(new qx.ui.basic.Label(' to '));
            filterGB.add(this.__keyEnd);
            filterGB.add(searchButton);
        }
    }
});
