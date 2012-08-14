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
            var tableModel = new qx.ui.table.model.Simple();
            tableModel.setColumns(['Column','Value'],['name','value']);
            tableModel.setDataAsMapArray(result);
            return new qx.ui.table.Table(tableModel);
        },
        
        _getCriteriaComponents : function() {
            var ret = new Array();
            this.__keyTF = new helenos.ui.TextField(this._cfDef.keyValidationClass);
            this.__nameStartTF = new helenos.ui.TextField(this._cfDef.comparatorType.className);
            this.__nameEndTF = new helenos.ui.TextField(this._cfDef.comparatorType.className);
            this.__reversedCB = new qx.ui.form.CheckBox('Reversed');
            
            ret.push(new qx.ui.basic.Label('Key:'));
            ret.push(this.__keyTF);
            ret.push(new qx.ui.core.Spacer(5));
                        
            if (this._cfDef.columnType == 'Super') {
                this.__sNameTF = new helenos.ui.TextField(this._cfDef.subComparatorType.className);
                ret.push(new qx.ui.core.Spacer(5));
                ret.push(new qx.ui.basic.Label('Super column name:'));
                ret.push(this.__sNameTF);
            }
            
            ret.push(new qx.ui.basic.Label('Range from:'));
            ret.push(this.__nameStartTF);
            ret.push(new qx.ui.basic.Label('to:'));
            ret.push(this.__nameEndTF);
            ret.push(new qx.ui.core.Spacer(5));
            ret.push(this.__reversedCB);
            return ret;
        }
    }
});
