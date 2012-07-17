/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */

/*
#asset(qx/icon/Tango/16/actions/view-refresh.png)
#asset(qx/icon/Tango/16/devices/computer.png)
#asset(helenos/keyspace.png)
#asset(helenos/supercf.png)
#asset(helenos/standardcf.png)
*/

qx.Class.define("helenos.util.GuiObserver",
{
    statics :
    {
        __tabbedPane : null,
        
        registerTabbedPane : function(pane) {
            this.__tabbedPane = pane;
        },
		
        showKeyspaceInfoTab : function(keyspaceName) {
            qx.core.Assert.assertNotNull(this.__tabbedPane,'tabbed pane not registered yet');
            
            var ksPage = new helenos.components.tab.KeyspaceInfoPage(keyspaceName);
            this.__tabbedPane.add(ksPage);
            this.__tabbedPane.setSelection([ksPage]);
        },
        
        showColumnFamilyInfoTab : function(keyspaceName, columnFamilyName) {
            qx.core.Assert.assertNotNull(this.__tabbedPane,'tabbed pane not registered yet');
            
            var cfPage = new helenos.components.tab.ColumnFamilyInfoPage(keyspaceName, columnFamilyName);
            this.__tabbedPane.add(cfPage);
            this.__tabbedPane.setSelection([cfPage]);
        }
    }
});
