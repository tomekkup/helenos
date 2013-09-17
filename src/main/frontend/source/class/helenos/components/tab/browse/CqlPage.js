/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.components.tab.browse.CqlPage",
{
    extend : helenos.components.tab.browse.AbstractPage,
 
    construct : function(ksName, cfName)
    {
        this.base(arguments, ksName, cfName);
    },

    members :
    {
        _getSplitPaneOrientation : function() {
            return 'vertical';
        },
        
        _getIconPath : function() {
            return 'helenos/query-16.png';
        },
        
        _performSearch  : function(e) {
            var consistencyLevel = this._consistencyLevelSB.getSelection()[0].getLabel();
            
            this._queryObj = new helenos.model.CqlQuery();
            this._queryObj.prepareQuery(this._cfDef, consistencyLevel);
            this._queryObj.setQuery(this._queryArea.getValue());
            
            var jsonQuery = qx.util.Serializer.toNativeObject(this._queryObj, null, null);
            
            return helenos.util.RpcActionsProvider.queryCql(jsonQuery);
        },
        
        _getCriteriaPane : function() {
            this._queryArea = new qx.ui.form.TextArea('SELECT * FROM ' + this._cfDef.name + ' LIMIT 100;');
            this._queryArea.set({
                padding : 4,
                wrap : true,
                height : 120,
                appearance : 'cql-textarea',
                required : true
            });
            this._setValidateAttr(this._queryArea, true);
            this._addToValidator(this._queryArea);
                        
            var queryGB = new helenos.ui.GroupBoxV('Query');
            queryGB.add(this._queryArea, {
                flex : 1
            });
            
            var buttonPane = new qx.ui.container.Composite(new qx.ui.layout.HBox(4));
            
            var leftPane = new qx.ui.container.Composite(new qx.ui.layout.HBox());
            leftPane.add(this._getSearchButton());
            var rightPane = new qx.ui.container.Composite(new qx.ui.layout.HBox(5, 'right').set({alignY : 'center'}));
            this._initConsistencyLevelSB();
            rightPane.add(new helenos.ui.BoldLabel(this.tr('consistency.level') + ':'));
            rightPane.add(this._consistencyLevelSB);
            
            buttonPane.add(leftPane, {width: '50%'});
            buttonPane.add(rightPane, {width: '50%'});
            //buttonPane.add(this._getSearchButton());
            
            var container = new qx.ui.container.Composite(new qx.ui.layout.VBox(3, 'top'));
            container.setAppearance('criteria-pane');
            container.add(queryGB, {flex : 1});
            container.add(buttonPane);
            
            return container;
        }
    }
});
