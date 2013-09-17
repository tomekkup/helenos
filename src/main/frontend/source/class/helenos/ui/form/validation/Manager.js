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
        var needsValidation = formItem.getUserData('__VALIDATE__');
        if (needsValidation === null || needsValidation === false) {
            return true;
        } else {
            return this.base(arguments, formItem);
        }
    }
  }
});
