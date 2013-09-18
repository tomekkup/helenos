/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.components.tab.browse.PredicatePage",
{
    extend : helenos.components.tab.browse.AbstractPage,
 
    construct : function(ksName, cfName)
    {
        this.base(arguments, ksName, cfName);
        this.__applyKeyModePredicate();
        this.__applyColModeRange();
        this.__disableNextBtn();
    },
    
    statics : {
        PREDICATE : 'predicate',
        NAME : 'name',
        RANGE : 'range',
        KEY_RANGE : 'key range'
    },
    
    members :
    {
        __keyModeRBG : null,
        __columnsByRBG : null,
        
        __keyMode : null,
        __keyTF : null,
        __keyFromTF : null,
        __keyToTF : null,
        __rowCountTF : null,
        
        __colMode : null,
        __nameStartTF : null,
        __nameEndTF : null,
        __sNameTF : null,
        __columnNamesTF : null,
        
        __keysPredicateCP : null,
        __keysRangeCP : null,
        __rangeColNamesCP : null,
        __rangeFromToCP : null,
        __reversedCB : null,
        __colsLimitTF : null,
        // for pagination
        __firstID : null,
        __lastID : null,
        __nextPageBtn : null,
        
        _getSplitPaneOrientation : function() {
            return 'horizontal';
        },
        
        _getIconPath : function() {
            return 'icon/16/apps/office-spreadsheet.png';
        },
        
        _performSearch  : function(e) {
            var consistencyLevel = this._consistencyLevelSB.getSelection()[0].getLabel();
            if (this.__isSuperColumnMode()) {
                if (this.__keyMode == this.self(arguments).KEY_RANGE) {
                    this._queryObj = new helenos.model.SubRangeQuery();
                } else {
                    this._queryObj = new helenos.model.SubPredicateQuery();
                }
                this._queryObj.setSName(this.__sNameTF.getValue());
            } else {
                if (this.__keyMode == this.self(arguments).KEY_RANGE) {
                    this._queryObj = new helenos.model.RangeQuery();
                } else {
                    this._queryObj = new helenos.model.PredicateQuery();
                }
            }
            this._queryObj.prepareQuery(this._cfDef, consistencyLevel);
            if (this.__keyMode == this.self(arguments).KEY_RANGE) {
                this._queryObj.setKeyFrom(this.__keyFromTF.getValue());
                this._queryObj.setKeyTo(this.__keyToTF.getValue());
                this._queryObj.setRowCount(this.__rowCountTF.getValue());
            } else {
                this._queryObj.setKey(this.__keyTF.getValue());
            }
            
            if (this.__colMode == this.self(arguments).RANGE) {
                this._queryObj.setNameStart(this.__nameStartTF.getValue());
                this._queryObj.setNameEnd(this.__nameEndTF.getValue());
                this._queryObj.setReversed(this.__reversedCB.getValue());
                this._queryObj.setLimit(this.__colsLimitTF.getValue());
            } else {
                this._queryObj.setColumnNames(this.__columnNamesTF.getValue().split(','));
            }
            
            var jsonQuery = qx.util.Serializer.toNativeObject(this._queryObj, null, null);
            var result = null;
            if (this.__keyMode == this.self(arguments).PREDICATE) {
                result = helenos.util.RpcActionsProvider.queryPredicate(this._cfDef, jsonQuery);
            } else  {
                result = helenos.util.RpcActionsProvider.queryKeyRange(this._cfDef, jsonQuery);
            }
            
            this._collectPaginationData(result);
            return result;
        },
        
        _collectPaginationData : function(result) {
            if (this.__keyMode == this.self(arguments).KEY_RANGE) {
                this.__firstID = result[0].key;
                this.__lastID = result[result.length-1].key;
                if (result.length < this._queryObj.getRowCount()) {
                    this.__disableNextBtn();
                } else {
                    this.__enableNextBtn();
                }
            } else  {
                if (this.__colMode == this.self(arguments).RANGE) {
                    var columnsRange = result[0].columns;
                    this.__firstID = columnsRange[0].name;
                    this.__lastID = columnsRange[columnsRange.length-1].name;
                    if (columnsRange.length < this._queryObj.getLimit()) {
                        this.__disableNextBtn();
                    } else {
                        this.__enableNextBtn();
                    }
                } else {
                    this.__disableNextBtn();
                }
            }
        },
        
        _getResultsPane : function() {
            var pane = this.base(arguments);
            pane.add(this.__buildButtonsBar());
            return pane;
        },
        
        __buildButtonsBar : function() {
            var buttonsBar = new qx.ui.toolbar.ToolBar();
            this.__nextPageBtn = new qx.ui.toolbar.Button('Next page', 'helenos/next_page.png');
            this.__nextPageBtn.addListener('execute', this.__onNextRange, this);
            
            buttonsBar.add(this.__nextPageBtn);
            return buttonsBar;
        },
        
        __onNextRange : function(e) {
            if (!this.__validateNextMode()) {
                return;
            }
            if (this.__keyMode == this.self(arguments).KEY_RANGE) {
                this.__keyFromTF.setValue(this.__lastID);
            } else {
                // paginate with columns
                this.__nameStartTF.setValue(this.__lastID);
                this.__nameEndTF.setValue(null);
            }
            
            this._performValidation();
        },
        
        __validateNextMode : function() {
          if (this.__colMode == this.self(arguments).RANGE && this.__keyMode == this.self(arguments).KEY_RANGE) {
              (new dialog.Alert({
                "message" : 'You can not paginate with key mode and column mode set to \'range\'',
                'image' : 'icon/48/status/dialog-error.png'
            })).set({width : 350}).show();
              return false;
          } else {
              return true;
          }
        },
        
        _getCriteriaPane : function() {
            var widgets = new Array();
            widgets.push(this.__buildKeysGB());
            widgets.push(this.__buildColumnsGB());
            widgets.push(this.__buildConsistencyLevelGB());
            
            var container = new qx.ui.container.Composite(new qx.ui.layout.VBox(5).set({alignX : 'left'}));
            container.setAppearance('criteria-pane');
            
            for (var i = 0; i < widgets.length; i++) {
                container.add(widgets[i]);
            }
            container.add(this._getSearchButton());
            container.add(this.__buildResetButton());
            
            var pane = new qx.ui.container.Scroll();
            pane.setWidth(180);
            pane.add(container);
            return pane;
        },
        
        __buildResetButton : function() {
            var button = new qx.ui.form.Button('Reset', 'icon/16/actions/edit-clear.png');
            button.addListener('execute', this.__resetSearchForm, this);
            return button;
        },
        
        __resetSearchForm : function(e) {
            this._resetter.reset();
            this._disabler.enable();
            this._disableNextBtn();
        },
        
        __disableNextBtn : function() {
            //if (this.__nextPrevBtnsEnabled == true) {
                this.__nextPageBtn.setEnabled(false);
            //    this._nextPrevBtnsEnabled = false;
            //}
        },
        
        __enableNextBtn : function() {
            //if (this.__nextPrevBtnsEnabled == false) {
                this.__nextPageBtn.setEnabled(true);
            //    this._nextPrevBtnsEnabled = true;
            //}
        },

        __buildConsistencyLevelGB : function() {
            this._initConsistencyLevelSB();
            
            var consLevelGB = new helenos.ui.GroupBoxV(this.tr('consistency.level'));
            consLevelGB.add(this._consistencyLevelSB);
            return consLevelGB;
        },
        
        __isSuperColumnMode : function() {
            return this._cfDef.columnType == 'Super';
        },

        __buildColumnsGB : function() {
            var columnsGB = new helenos.ui.GroupBoxV('Columns');
            
            if (this.__isSuperColumnMode() == true) {
                this.__nameStartTF = new helenos.ui.TextField(this._cfDef.subComparatorType.className);
                this.__nameEndTF = new helenos.ui.TextField(this._cfDef.subComparatorType.className);
            } else {
                this.__nameStartTF = new helenos.ui.TextField(this._cfDef.comparatorType.className);
                this.__nameEndTF = new helenos.ui.TextField(this._cfDef.comparatorType.className);
            }
            this._addToResetter(this.__nameStartTF);
            this._addToDisabler(this.__nameStartTF);
            
            this._addToResetter(this.__nameEndTF);
            this._addToDisabler(this.__nameEndTF);
            
            columnsGB.add(this.__buildRangeFromToBox());
            columnsGB.add(this.__buildRangeColNamesBox());
            
            if (this._cfDef.columnType == 'Super') {
                this.__sNameTF = new helenos.ui.TextField(this._cfDef.comparatorType.className);
                columnsGB.add(new qx.ui.basic.Label('Super column name:'));
                columnsGB.add(this.__sNameTF);
            }
            
            columnsGB.add(new qx.ui.basic.Label('Columns mode:'));
            columnsGB.add(this.__buildRangeModeBG());
            
            return columnsGB;
        },
        
        __buildKeysGB : function() {          
            var keysGB = new helenos.ui.GroupBoxV('Keys');
            keysGB.add(this.__buildKeysPredicateBox());
            keysGB.add(this.__buildKeysRangeBox());
            
            keysGB.add(new qx.ui.basic.Label('Key mode:'));
            keysGB.add(this.__buildKeyModeBG());
            
            return keysGB;
        },
        
         __buildKeysPredicateBox : function(){
           this.__keysPredicateCP = new qx.ui.container.Composite(new qx.ui.layout.VBox(5)).set({padding : 0});
           this.__keyTF = new helenos.ui.TextField(this._cfDef.keyValidationClass).set({required : true});

           this._addToResetter(this.__keyTF);
           this._addToValidator(this.__keyTF);
           this._addToDisabler(this.__keyTF);

           this.__keysPredicateCP.add(new qx.ui.basic.Label('Key:'));
           this.__keysPredicateCP.add(this.__keyTF);
           
           return this.__keysPredicateCP;
         },
         
         __buildKeysRangeBox : function() {
           this.__keyFromTF = new helenos.ui.TextField(this._cfDef.keyValidationClass);
           this.__keyToTF = new helenos.ui.TextField(this._cfDef.keyValidationClass);
           this.__rowCountTF = new qx.ui.form.TextField().set({filter : /[0-9]/, value : '10', required : true});
           this._addToResetter(this.__keyFromTF);
           this._addToDisabler(this.__keyFromTF);
           this._addToResetter(this.__keyToTF);
           this._addToDisabler(this.__keyToTF);
           this._addToResetter(this.__rowCountTF);
           this._addToDisabler(this.__rowCountTF);
           this._addToValidator(this.__rowCountTF);
           
           this.__keysRangeCP = new qx.ui.container.Composite(new qx.ui.layout.VBox(5)).set({padding : 0});
           this.__keysRangeCP.add(new qx.ui.basic.Label('From:'));
           this.__keysRangeCP.add(this.__keyFromTF);
           this.__keysRangeCP.add(new qx.ui.basic.Label('To:'));
           this.__keysRangeCP.add(this.__keyToTF);
           this.__keysRangeCP.add(new qx.ui.basic.Label('Max keys:'));
           this.__keysRangeCP.add(this.__rowCountTF);
           return this.__keysRangeCP;
         },
        
        __buildRangeModeBG : function() {
            var byNameBT = new qx.ui.form.RadioButton(this.self(arguments).NAME);
            var byRangeBT = new qx.ui.form.RadioButton(this.self(arguments).RANGE);
            
            this.__columnsByRBG = new qx.ui.form.RadioButtonGroup(new qx.ui.layout.HBox(8));
            this._addToResetter(this.__columnsByRBG);
            this._addToDisabler(this.__columnsByRBG);
            this.__columnsByRBG.add(byNameBT);
            this.__columnsByRBG.add(byRangeBT);
            this.__columnsByRBG.setSelection([byRangeBT]);
            this.__columnsByRBG.addListener('changeSelection', this.__onRangeModeToggled, this);
            return this.__columnsByRBG;
        },
        
        __buildKeyModeBG : function() {
            var predicateBT = new qx.ui.form.RadioButton(this.self(arguments).PREDICATE);
            var keyRangeBT = new qx.ui.form.RadioButton(this.self(arguments).KEY_RANGE);
            
            this.__keyModeRBG = new qx.ui.form.RadioButtonGroup(new qx.ui.layout.HBox(5));
            this._addToResetter(this.__keyModeRBG);
            this._addToDisabler(this.__keyModeRBG);
            this.__keyModeRBG.add(predicateBT);
            this.__keyModeRBG.add(keyRangeBT);
            this.__keyModeRBG.setSelection([predicateBT]);
            this.__keyModeRBG.addListener('changeSelection', this.__onKeyModeToggled, this);
            return this.__keyModeRBG;
        },
        
        __buildRangeFromToBox : function() {
            this.__rangeFromToCP = new qx.ui.container.Composite(new qx.ui.layout.VBox(5)).set({padding : 0});
            
            this.__colsLimitTF = new qx.ui.form.TextField().set({filter : /[0-9]/, value : '10', required : true});
            this._addToResetter(this.__colsLimitTF);
            this._addToDisabler(this.__colsLimitTF);
            this._addToValidator(this.__colsLimitTF);
            
            this.__rangeFromToCP.add(new qx.ui.basic.Label('From:'));
            this.__rangeFromToCP.add(this.__nameStartTF);
            this.__rangeFromToCP.add(new qx.ui.basic.Label('To:'));
            this.__rangeFromToCP.add(this.__nameEndTF);
            this.__rangeFromToCP.add(new qx.ui.basic.Label('Limit:'));
            this.__rangeFromToCP.add(this.__colsLimitTF);
            
            this.__reversedCB = new qx.ui.form.CheckBox('Reversed');
            this._addToResetter(this.__reversedCB);
            this._addToDisabler(this.__reversedCB);
            this.__rangeFromToCP.add(this.__reversedCB);
            
            return this.__rangeFromToCP;
        },
        
        __buildRangeColNamesBox : function() {
            this.__rangeColNamesCP = new qx.ui.container.Composite(new qx.ui.layout.VBox(5)).set({padding : 0});

            this.__columnNamesTF = new qx.ui.form.TextArea().set({placeholder : 'comma separated columns list', required : true});
            this._addToResetter(this.__columnNamesTF);
            this._addToDisabler(this.__columnNamesTF);
            this._addToValidator(this.__columnNamesTF);
            
            this.__rangeColNamesCP.add(new qx.ui.basic.Label('Column names:'));
            this.__rangeColNamesCP.add(this.__columnNamesTF);
            
            return this.__rangeColNamesCP;
        },
        
        __onRangeModeToggled : function(e) {
            if (e.getData()[0].getLabel() == this.self(arguments).NAME) {
                this.__applyColModeColNames();
            } else {
                this.__applyColModeRange();
            }
        },
        
        __onKeyModeToggled : function(e) {
            if (e.getData()[0].getLabel() == this.self(arguments).PREDICATE) {
                this.__applyKeyModePredicate();
            } else {
                this.__applyKeyModeKeyRange();
            }
        },
        
        __applyKeyModePredicate : function() {
            this.__keyMode = this.self(arguments).PREDICATE;
            
            this.__keysRangeCP.exclude();
            this.__keysPredicateCP.show();
            
            this._setValidateAttr(this.__keyTF, true);
            this._setValidateAttr(this.__rowCountTF, false);
        },
        
        __applyKeyModeKeyRange : function() {
            this.__keyMode = this.self(arguments).KEY_RANGE;
            
            this.__keysRangeCP.show();
            this.__keysPredicateCP.exclude();
            
            this._setValidateAttr(this.__keyTF, false);
            this._setValidateAttr(this.__rowCountTF, true);
        },
        
        __applyColModeColNames : function() {
            this.__colMode = this.self(arguments).NAME;
            
            this.__rangeFromToCP.exclude();
            this.__rangeColNamesCP.show();
            
            this._setValidateAttr(this.__columnNamesTF, true);
            this._setValidateAttr(this.__colsLimitTF, false);
        },
        
        __applyColModeRange : function() {
            this.__colMode = this.self(arguments).RANGE;
 
            this.__rangeFromToCP.show();
            this.__rangeColNamesCP.exclude();
            
            this._setValidateAttr(this.__columnNamesTF, false);
            this._setValidateAttr(this.__colsLimitTF, true);
        }
    }
});