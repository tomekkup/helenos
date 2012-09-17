/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.ui.table.model.EditableSimple",
{
    extend : qx.ui.table.model.Simple,

    construct : function()
    {
        this.base(arguments);
    },
    
    members : {
        isColumnEditable : function(index) {
            return true;
        }
    }
});
