/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.ui.HeaderButton",
{
    extend : qx.ui.form.Button,

    construct : function(icon, toolTipText)
    {
        this.base(arguments, null, icon);
        this.set({
            padding : 2, toolTipText : toolTipText
        });
    }
});
