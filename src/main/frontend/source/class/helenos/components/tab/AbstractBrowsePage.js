/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.components.tab.AbstractBrowsePage",
{
    extend : helenos.components.tab.AbstractCloseablePage,
 
    members : {
        _rajCB : null,
        _resultView : null
    },
 
    construct : function()
    {
        this.base(arguments);
        
        this._rajCB = new qx.ui.form.CheckBox('Parse results to JSON');
        this._resultView = new qx.ui.container.Composite(new qx.ui.layout.VBox());
    }
});
