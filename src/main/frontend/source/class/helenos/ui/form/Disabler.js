/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.ui.form.Disabler",
{
    extend : qx.core.Object,

  construct : function()
  {
    this.base(arguments);

    this.__items = [];
  },

  members :
  {
    __items : null,
    
    disable : function() {
        for (var i = 0; i < this.__items.length; i++) {
            this.__items[i].setEnabled(false);
        } 
    },
    
    enable : function() {
        for (var i = 0; i < this.__items.length; i++) {
            this.__items[i].setEnabled(true);
        }
    },
    
    add : function(item) {
        this.__items.push(item);
    }
  }
});
