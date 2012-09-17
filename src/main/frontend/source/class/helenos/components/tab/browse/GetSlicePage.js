/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.components.tab.browse.GetSlicePage",
{
    extend : helenos.components.tab.browse.AbstractBrowsePage,
 
    construct : function(ksName, cfName)
    {
        this.base(arguments, ksName, cfName);
        this.set({
            icon : 'icon/16/apps/office-spreadsheet.png'
        });
    },

    members :
    {
        __keyTF : null,
        __nameStartTF : null,
        __nameEndTF : null,
        __sNameTF : null,
        __reversedCB : null,
        
        _performSearch  : function(e) {
            var key = this.__keyTF.getValue();
            var nameStart = this.__nameStartTF.getValue();
            var nameEnd = this.__nameEndTF.getValue();
            var sName = (this.__sNameTF == null ? null : this.__sNameTF.getValue());
            var reversed = this.__reversedCB.getValue();
            var result = helenos.util.RpcActionsProvider.querySlice(this._cfDef, key, nameStart, nameEnd, sName, reversed );
            
            this._resultView.removeAll();
            this._resultView.add(this._getResultComponent(result), {flex: 1});
        },
        
        _getResultComponent : function(result) {
            var tableModel = new helenos.ui.table.model.EditableSimple();
            tableModel.setColumns(['Column','Value'],['name','value']);
            tableModel.setDataAsMapArray(result);
            
            var tablePane = new qx.ui.core.scroll.ScrollPane();
            tablePane.set({allowGrowX : true, allowGrowY : true});
            tablePane.add(new qx.ui.table.Table(tableModel));
            return tablePane;
        },
        
        _getCriteriaComponents : function() {
            var ret = new Array();
            this.__keyTF = new helenos.ui.RequiredTextField(this._cfDef.keyValidationClass);
            this.__nameStartTF = new helenos.ui.RequiredTextField(this._cfDef.comparatorType.className);
            this.__nameEndTF = new helenos.ui.TextField(this._cfDef.comparatorType.className);
            this.__reversedCB = new qx.ui.form.CheckBox('Reversed');
            
            this._manager.add(this.__keyTF);
            this._manager.add(this.__nameStartTF);
            
            ret.push(new helenos.ui.CompositeV([new qx.ui.basic.Label('Key:'), this.__keyTF]));
                        
            if (this._cfDef.columnType == 'Super') {
                this.__sNameTF = new helenos.ui.TextField(this._cfDef.subComparatorType.className);
                ret.push(new helenos.ui.CompositeV([new qx.ui.basic.Label('Super column name:'), this.__sNameTF]));
            }
            
            ret.push(new helenos.ui.CompositeV([new qx.ui.basic.Label('Range from:'), this.__nameStartTF]));
            ret.push(new helenos.ui.CompositeV([new qx.ui.basic.Label('Range to:'), this.__nameEndTF]));
            ret.push(this.__reversedCB);
            return ret;
        }
    }
});
