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
        
        shutdownApp : function() {
            var handler = qx.core.Init.getApplication().getRoot().fadeOut(750);
            handler.addListener('end', function(e) {
                qx.core.ObjectRegistry.shutdown();
                window.location.reload();
            }, this);
        },
        
        registerTabbedPane : function(pane) {
            this.__tabbedPane = pane;
        },
        
        registerSchemaPane : function(pane) {
            this.__schemaPane = pane;
        },
	
        showConnectionEditTab : function() {
            qx.core.Assert.assertNotNull(this.__tabbedPane,'tabbed pane not registered yet');
            
            var page = new helenos.components.tab.ConnectionsEditorPage();
            this._addPageToTab(page);
        },
        
        showAccountsEditTab : function() {
            qx.core.Assert.assertNotNull(this.__tabbedPane,'tabbed pane not registered yet');
            
            var page = new helenos.components.tab.AccountsEditorPage();
            this._addPageToTab(page);
        },
        
        showKeyspaceInfoTab : function(keyspaceName) {
            qx.core.Assert.assertNotNull(this.__tabbedPane,'tabbed pane not registered yet');
            
            var ksPage = new helenos.components.tab.KeyspaceInfoPage(keyspaceName);
            this._addPageToTab(ksPage);
        },
        
        showBrowseByPredicateTab : function(keyspaceName, columnFamily) {
            qx.core.Assert.assertNotNull(this.__tabbedPane,'tabbed pane not registered yet');
            var dataPage = new helenos.components.tab.browse.PredicatePage(keyspaceName, columnFamily);
            this._addPageToTab(dataPage);
        },
        
        showColumnFamilyInfoTab : function(keyspaceName, columnFamilyName) {
            qx.core.Assert.assertNotNull(this.__tabbedPane,'tabbed pane not registered yet');
            
            var cfPage = new helenos.components.tab.ColumnFamilyInfoPage(keyspaceName, columnFamilyName);
            this._addPageToTab(cfPage);
        },
        
        showBrowseByCqlQueryTab : function(keyspaceName, columnFamilyName) {
            qx.core.Assert.assertNotNull(this.__tabbedPane,'tabbed pane not registered yet');
            var cqlPage = new helenos.components.tab.browse.CqlPage(keyspaceName, columnFamilyName);
            
            this._addPageToTab(cqlPage);
        },
        
        refreshSchemaTree : function() {
            qx.core.Assert.assertNotNull(this.__schemaPane,'cluster pane not registered yet');
            this.__schemaPane.refreshSchemaTree();
        },
        
        _addPageToTab : function(page) {
            this.__tabbedPane.add(page);
            this.__tabbedPane.setSelection([page]);
        }
    }
});
