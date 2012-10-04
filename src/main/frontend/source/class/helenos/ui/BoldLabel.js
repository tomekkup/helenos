/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.ui.BoldLabel",
{
    extend : qx.ui.basic.Label,

    construct : function(label)
    {
        this.base(arguments, '<b>' + label + '</b>');
        this.set({
            rich: true
        });
    }
});
