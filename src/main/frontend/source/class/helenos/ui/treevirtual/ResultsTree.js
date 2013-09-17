/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.ui.treevirtual.ResultsTree",
{
    extend : qx.ui.treevirtual.TreeVirtual,
    include : [helenos.ui.table.MZeroClipboardCtxHandler],
    
    construct : function()
    {
        this.base(arguments, ["Name", "Value", "Clock", "TTL"]);
        this.set({
            alwaysShowOpenCloseSymbol : true, 
            useTreeLines : true,
            statusBarVisible : false
        });
        
        var resizeBehavior = this.getTableColumnModel().getBehavior();
        resizeBehavior.set(1, {
            width:"1*", 
            minWidth:200
        });
        resizeBehavior.setWidth(2, 195);
        resizeBehavior.setWidth(3, 50);
        
        this.setContextMenuHandler(0, this.contextMenuHandler);
        this.setContextMenuHandler(1, this.contextMenuHandler);
        this.setContextMenuHandler(2, this.contextMenuHandler);
        this.setContextMenuHandler(3, this.contextMenuHandler);
        
        var dateRenderer = new qx.ui.table.cellrenderer.Date();
        dateRenderer.setDateFormat(qx.util.format.DateFormat.getDateTimeInstance());
        this.getTableColumnModel().setDataCellRenderer(2, dateRenderer);
    },
    
    members : {
        
        setData : function(data) {
            if (data == null) {
                return
            }
            var dataModel = this.getDataModel();
            dataModel.clearData();
            var i;
            for(i = 0; i < data.length; i++) {
                var row = data[i];
                var branch = dataModel.addBranch(null, row.key, true, false, 'helenos/key.png');
                var j;
                for (j = 0; j < row.columns.length; j++) {
                    var col = row.columns[j];
                    if (col.name != 'KEY') {
                        var leaf = dataModel.addLeaf(branch, col.name, 'helenos/isctf.png');
                        dataModel.setColumnData(leaf, 1, col.value);
                        dataModel.setColumnData(leaf, 2, new Date(col.clock));
                        dataModel.setColumnData(leaf, 3, col.ttl);
                    }
                }
            }
            dataModel.setData();
        }
    }
});
