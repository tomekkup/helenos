/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */

qx.Class.define("helenos.components.tab.ViewDataPage",
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
        
    },

    members :
    {
    
    }
});
