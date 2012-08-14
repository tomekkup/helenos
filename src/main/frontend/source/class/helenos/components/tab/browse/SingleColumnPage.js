/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/*
#asset(qx/icon/${qx.icontheme}/16/places/folder-open.png)
*/
qx.Class.define("helenos.components.tab.browse.SingleColumnPage",
{
    extend : helenos.components.tab.browse.AbstractBrowsePage,
 
    construct : function(ksName, cfName)
    {
        this.base(arguments, ksName, cfName);
        this.set({
            icon : 'icon/16/apps/utilities-keyring.png'
        });
    },

    members :
    {
        __keyTF : null,
        __nameTF : null,
        __sNameTF : null,
        
        _performSearch  : function(e) {
            var key = this.__keyTF.getValue();
            var name = this.__nameTF.getValue();
            var sName = (this.__sNameTF == null ? null : this.__sNameTF.getValue());
            var result = helenos.util.RpcActionsProvider.querySingleColumn(this._cfDef, key, name, sName );
            
            this._resultView.removeAll();
            if (this._rajCB.getValue()) {
                this._resultView.add(this._getTreeFromJson(key, result), {flex: 1});
            } else {
                this._resultView.add(new qx.ui.form.TextArea(result), {flex: 1});
            }
        },
        
        _getCriteriaComponents : function() {
            var ret = new Array();
            this.__keyTF = new helenos.ui.TextField(this._cfDef.keyValidationClass);
            this.__nameTF = new helenos.ui.TextField(this._cfDef.comparatorType.className);
            
            //var searchButton = this._getSearchButton();
            
            ret.push(new qx.ui.basic.Label('Key:'));
            ret.push(this.__keyTF);
            ret.push(new qx.ui.core.Spacer(5));
                        
            if (this._cfDef.columnType == 'Super') {
                this.__sNameTF = new helenos.ui.TextField(this._cfDef.subComparatorType.className);
                ret.push(new qx.ui.core.Spacer(5));
                ret.push(new qx.ui.basic.Label('Super column name:'));
                ret.push(this.__sNameTF);
            }
            
            ret.push(new qx.ui.basic.Label('Column name:'));
            ret.push(this.__nameTF);
            //filterGB.add(searchButton);
            //filterGB.add(this._rajCB);
            
            //this.add(filterGB);
            return ret;
        }
    }
});
