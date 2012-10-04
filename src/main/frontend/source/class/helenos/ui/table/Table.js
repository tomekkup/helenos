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

    construct : function(tableModel)
    {
        this.base(arguments, tableModel);
        this.setToolTipText('Double click to edit and copy values');
    }
});
