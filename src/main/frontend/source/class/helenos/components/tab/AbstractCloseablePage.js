/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.components.tab.AbstractCloseablePage",
{
    extend : qx.ui.tabview.Page,
 
    construct : function()
    {
        this.base(arguments);
        this.setShowCloseButton(true);
    }
});
