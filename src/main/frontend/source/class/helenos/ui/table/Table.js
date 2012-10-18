/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.ui.table.Table",
{
    extend : qx.ui.table.Table,
    include : [helenos.ui.table.MZeroClipboardCtxHandler],

    construct : function(tableModel)
    {
        this.base(arguments, tableModel);
    },
    
    members : {
        _columnsWidth: null,

        setContextMenuHandlers : function(colIds) {
            var i;
            for (i=0; i < colIds.length; i++) {
                this.setContextMenuHandler(colIds[i], this.contextMenuHandler);
            }
        },
        
        setColumnsWidth : function(colsWidth) {
            this._columnsWidth = colsWidth;
            this.addListener('resize', function(e) {
                this._resizeTableColumns(e);
            }, this);
        },
        
        _resizeTableColumns : function(e) {
            for (var i = 0; i < this._columnsWidth.length; i++) {
                this.setColumnWidth(i, e.getData()['width'] * this._columnsWidth[i] / 100);
            }
        }
    }
});
