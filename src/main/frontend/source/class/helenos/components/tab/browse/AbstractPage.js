/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/*
#asset(qx/icon/${qx.icontheme}/16/actions/system-search.png)
#asset(qx/icon/${qx.icontheme}/16/places/folder-open.png)
*/
qx.Class.define("helenos.components.tab.browse.AbstractPage",
{
    extend : helenos.components.tab.AbstractCloseablePage,
    
    construct : function(ksName, cfName)
    {
        this.base(arguments);
        this._cfDef = helenos.util.RpcActionsProvider.describeColumnFamily(ksName, cfName);
        
        this.set({
            layout : new qx.ui.layout.Grow(),
            label : (ksName + ' : ' + cfName),
            icon : this._getIconPath()
        });
        
        this._manager = new qx.ui.form.validation.Manager();
        this.__buildMainPane();
    },
 
    members : {
        
        _cfDef : null,
        _manager : null,
        _resultsTree : null,
        
        _getSplitPaneOrientation : function() {
            throw new Error('_getSplitPaneOrientation is abstract');
        },
        
        _buildCriteriaComponent : function() {
            throw new Error('_buildCriteriaComponent is abstract');
        },
        
        _getIconPath : function() {
            throw new Error('_getIconPath is abstract');
        },
        
        _performSearch : function() {
            throw new Error('_performSearch is abstract');
        },
        
        __buildMainPane : function() {
            var pane = new qx.ui.splitpane.Pane(this._getSplitPaneOrientation());
            pane.add(this._getCriteriaPane(),0);
            pane.add(this._getResultsPane(), 1);
            this.add(pane);
        },
        
        _getCriteriaPane : function() {
            throw new Error('_getCriteriaPane is abstract');
        },
        
        _getResultsPane : function() {
            this._resultsTree = new helenos.ui.treevirtual.ResultsTree();
            
            var tablePane = new qx.ui.core.scroll.ScrollPane().set({allowGrowX : true, allowGrowY : true});
            tablePane.add(this._resultsTree);
            
            var resultView = new qx.ui.container.Composite(new qx.ui.layout.VBox(1));
            resultView.add(tablePane, {flex : 1});
            return resultView;
        },

        _getSearchButton : function() {
            var button = new qx.ui.form.Button('Search', 'icon/16/actions/system-search.png');
            button.addListener("execute", this._performValidation, this);
            return button;
        },
        
        _performValidation : function(e) {
            this._manager.validate();
            if (this._manager.isValid()) {
                var data = this._performSearch();
                this._resultsTree.setData(data);
            }
        }
    }
});
