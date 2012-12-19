/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.ui.table.cellrenderer.Authorities",
{
    extend : qx.ui.table.cellrenderer.Default,
    
    members : {
        _getContentHtml : function(cellInfo)
        {
            var value = cellInfo.value;
            if ( value === null ){
                value = "";
            } else {
                var temp = "";
                for (var i = 0; i < value.length; i++) {
                    temp += value[i].authority + " ";
                }
                value = temp;
            }
            
            cellInfo.value = value;
            return qx.bom.String.escape(this._formatValue(cellInfo));
        } 
    }
});
