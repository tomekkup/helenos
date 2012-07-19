/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.ui.RichAtom",
{
    extend : qx.ui.basic.Atom,

    construct : function(label, icon)
    {
        this.base(arguments, label, icon);
        this.set({
            rich: true
        });
    }
});
