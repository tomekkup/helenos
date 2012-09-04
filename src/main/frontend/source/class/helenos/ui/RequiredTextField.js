/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.ui.RequiredTextField",
{
    extend : helenos.ui.TextField,

    construct : function(clazz)
    {
        this.base(arguments, clazz);
        this.setRequired(true );
    }
});
