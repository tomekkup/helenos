/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/*
#asset(qx/icon/16/Tango/status/dialog-information.png)
#asset(qx/icon/Tango/16/places/user-trash.png)
#asset(qx/icon/Tango/16/apps/office-spreadsheet.png)
 */
qx.Class.define("helenos.components.menu.ColumnFamilyContextMenu",
{
    extend : qx.ui.menu.Menu,

    /*
  *****************************************************************************
     MEMBERS
  *****************************************************************************
  */
 
    construct : function()
    {
        this.base(arguments);
        
        var propsButton = new qx.ui.menu.Button("Properties", "qx/icon/Tango/16/status/dialog-information.png");
        this.add(propsButton);
        
        var viewDataButton = new qx.ui.menu.Button("View data", "qx/icon/Tango/16/apps/office-spreadsheet.png");
        this.add(viewDataButton);
        
        var removeButton = new qx.ui.menu.Button("Remove column family", "qx/icon/Tango/16/places/user-trash.png");
        this.add(removeButton);
    }
});
