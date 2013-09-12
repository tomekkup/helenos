/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.ui.form.validation.Manager",
{
    extend : qx.ui.form.validation.Manager,

  construct : function()
  {
    this.base(arguments);
  },

  members :
  {
    __validateRequired : function(formItem) {
        this.debug(formItem);
        if (!formItem.isVisible()) {
            this.debug('not visible');
            return true;
        } else {
            this.debug('visible');
            return this.base(arguments, formItem);
        }
    }
  }
});
