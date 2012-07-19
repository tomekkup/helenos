/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.util.GuiObserver",
{
    statics :
    {
        __tabbedPane : null,
        __schemaPane : null,
        
        registerTabbedPane : function(pane) {
            this.__tabbedPane = pane;
        },
        
        registerSchemaPane : function(pane) {
            this.__schemaPane = pane;
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
        },
        
        refreshSchemaTree : function() {
            qx.core.Assert.assertNotNull(this.__schemaPane,'cluster pane not registered yet');
            this.__schemaPane.refreshSchemaTree();
        }
    }
});
