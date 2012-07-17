/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/*
#asset(qx/icon/${qx.icontheme}/16/status/dialog-information.png)
#asset(qx/icon/${qx.icontheme}/16/places/user-trash.png)
#asset(qx/icon/${qx.icontheme}/16/apps/office-spreadsheet.png)
 */
qx.Class.define("helenos.components.menu.ColumnFamilyContextMenu",
{
    extend : qx.ui.menu.Menu,
 
    construct : function(ksName, cfName)
    {
        this.base(arguments);
        
        var propsButton = new qx.ui.menu.Button("Properties", "qx/icon/Oxygen/16/status/dialog-information.png");
        propsButton.setUserData('KSNAME', ksName);
        propsButton.setUserData('CFNAME', cfName);
        propsButton.addListener("execute", this.__showProperties);
        this.add(propsButton);
        
        var viewDataButton = new qx.ui.menu.Button("View data", "qx/icon/Oxygen/16/apps/office-spreadsheet.png");
        viewDataButton.setUserData('KSNAME', ksName);
        viewDataButton.setUserData('CFNAME', cfName);
        this.add(viewDataButton);
        
        var removeButton = new qx.ui.menu.Button("Remove column family", "qx/icon/Oxygen/16/places/user-trash.png");
        removeButton.setUserData('KSNAME', ksName);
        removeButton.setUserData('CFNAME', cfName);
        this.add(removeButton);
    },
    
    members : {
        __showProperties : function(e) {
            var ksName = e.getTarget().getUserData('KSNAME');
            var cfName = e.getTarget().getUserData('CFNAME');
            
            helenos.util.GuiObserver.showColumnFamilyInfoTab(ksName, cfName);
        }
    }
});
