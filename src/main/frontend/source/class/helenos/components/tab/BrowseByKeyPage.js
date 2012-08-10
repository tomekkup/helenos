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
qx.Class.define("helenos.components.tab.BrowseByKeyPage",
{
    extend : helenos.components.tab.AbstractBrowsePage,
 
    construct : function(ksName, cfName)
    {
        this.base(arguments);
        this.set({
            layout : new qx.ui.layout.VBox(3, 'top'),
            icon : 'icon/16/apps/utilities-keyring.png',
            label: (ksName + ' : ' + cfName)
        });
        
        var cfDef = helenos.util.RpcActionsProvider.describeColumnFamily(ksName, cfName);
        
        this.__addFilterPane(cfDef);
        this.add(this._resultView, {flex: 1});
    },

    members :
    {
        __keyTF : null,
        __nameTF : null,
        __sNameTF : null,
        __cfDef : null,
        
        __performSearch : function(e) {
            var key = this.__keyTF.getValue();
            var name = this.__nameTF.getValue();
            var sName = (this.__sNameTF == null ? null : this.__sNameTF.getValue());
            var result = helenos.util.RpcActionsProvider.querySingleColumn(this.__cfDef, key, name, sName );
            
            this._resultView.removeAll();
            if (this._rajCB.getValue()) {
                this._resultView.add(this._getTreeFromJson(key, result), {flex: 1});
            } else {
                this._resultView.add(new qx.ui.form.TextArea(result), {flex: 1});
            }
        },
        
        _getTreeFromJson : function(name, data) {
            var tree = new qx.ui.tree.Tree();
            var rootNode = new qx.ui.tree.TreeFolder(name);
            
            tree.setRoot(rootNode);
            rootNode.setOpen(true);
          
            if (data == undefined || data == '') {
                tree.getRoot().add(new qx.ui.tree.TreeFile('empty value'));
                return tree;
            }
            
            var jsonVal = qx.lang.Json.parse(data);
            var currentNode = tree.getRoot();
            this._renderTreeItemFromJson(currentNode, jsonVal);
            return tree;
        },
        
        _renderTreeItemFromJson : function(node, data) {
            if (data == null) {
                return;
            }
            if (Array.isArray(data)) {
                for (var i = 0; i < data.length; i++) {
                    this._renderTreeItemFromJson(node, data[i]);
                }
            } else if(typeof data === 'object') {
                for (var key in data) {
                    if(Array.isArray(data[key])) {
                        var subNode = new qx.ui.tree.TreeFolder(key);
                        subNode.setIcon('helenos/a.png');
                        node.add(subNode);
                    } else
                    if (typeof data[key] === 'number') {
                        var subNode = new qx.ui.tree.TreeFile(key + ' : ' + data[key]);
                        subNode.setIcon('helenos/n.png');
                        node.add(subNode);
                    } else
                    if (typeof data[key] === 'string') {
                        var subNode = new qx.ui.tree.TreeFile(key + ' : ' + data[key]);
                        subNode.setIcon('helenos/s.png');
                        node.add(subNode);
                    }
                    else {
                        var subNode = new qx.ui.tree.TreeFolder(key);
                        subNode.setIcon('icon/16/places/folder-open.png');
                        
                        subNode.setOpen(true);
                        node.add(subNode);
                        this._renderTreeItemFromJson(subNode, data[key]);
                    }
                }
            } else {
               node.add(new qx.ui.tree.TreeFile(data));
            }
        },
        
        __addFilterPane : function(cfDef) {
            this.__cfDef = cfDef;
            this.__keyTF = new helenos.ui.TextField(this.__cfDef.keyValidationClass);
            this.__nameTF = new helenos.ui.TextField(this.__cfDef.comparatorType.className);
            
            var searchButton = new qx.ui.form.Button('Search', 'icon/16/actions/system-search.png');
            searchButton.addListener("execute", this.__performSearch, this);
            
            var filterGB = new qx.ui.groupbox.GroupBox('Filter');
            filterGB.setLayout(new qx.ui.layout.HBox(8));
                        
            filterGB.add(new qx.ui.basic.Label('Key:'));
            filterGB.add(this.__keyTF);
            filterGB.add(new qx.ui.core.Spacer(5));
                        
            if (this.__cfDef.columnType == 'Super') {
                this.__sNameTF = new helenos.ui.TextField(this.__cfDef.subComparatorType.className);
                filterGB.add(new qx.ui.core.Spacer(5));
                filterGB.add(new qx.ui.basic.Label('Super column name:'));
                filterGB.add(this.__sNameTF);
            }
            
            filterGB.add(new qx.ui.basic.Label('Column name:'));
            filterGB.add(this.__nameTF);
            filterGB.add(searchButton);
            filterGB.add(this._rajCB);
            
            this.add(filterGB);
        }
    }
});
