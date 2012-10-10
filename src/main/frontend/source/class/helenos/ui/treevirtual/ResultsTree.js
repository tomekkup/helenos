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

    construct : function(data)
    {
        this.base(arguments, ["Name", "Value", "Clock", "TTL"]);
        this.set({alwaysShowOpenCloseSymbol : true, useTreeLines : true});
        
        var resizeBehavior = this.getTableColumnModel().getBehavior();
        resizeBehavior.set(1, { width:"1*", minWidth:200  });
        resizeBehavior.setWidth(3, 60);
        
        this.setData(data);
        
        ZeroClipboard.setMoviePath("resource/ZeroClipboard10.swf");
        this.setContextMenuHandler(0, this._contextMenuHandler);
        this.setContextMenuHandler(1, this._contextMenuHandler);
        this.setContextMenuHandler(2, this._contextMenuHandler);
        this.setContextMenuHandler(3, this._contextMenuHandler);
    },
    
    members : {
        
        _contextMenuHandler : function(col, row, table, dataModel, contextMenu) {
            var copyBtn = new qx.ui.menu.Button('Copy to clipboard');
            var clip = new ZeroClipboard.Client();
            
            clip.addEventListener('complete', function (client, text) {
                client.destroy();
            },this);
            if (!table.getSelectionModel().isSelectionEmpty()) {
                var text = dataModel.getValue(col, row);
                clip.setText(text);
            }
            
            copyBtn.addListener("appear", function(e) {
                clip.glue(e.getTarget().getContentElement().getDomElement());
            }, this);
            
            contextMenu.add(copyBtn);
            return true;
        },
        
        setData : function(data) {
            var dataModel = this.getDataModel();
            
            var i;
            for(i = 0; i < data.length; i++) {
                var row = data[i];
                var branch = dataModel.addBranch(null, row.key, true);
                var j;
                for (j = 0; j < row.columns.length; j++) {
                    var col = row.columns[j];
                    var leaf = dataModel.addLeaf(branch, col.name);
                    dataModel.setColumnData(leaf, 1, col.value);
                    dataModel.setColumnData(leaf, 2, col.clock);
                    dataModel.setColumnData(leaf, 3, col.ttl);
                }
            }
            dataModel.setData();
        }
    }
});
