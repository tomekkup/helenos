/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.ui.GroupBoxV',
{
    extend : qx.ui.groupbox.GroupBox,

    construct : function(label)
    {
        this.base(arguments, label);
        this.setLayout(new qx.ui.layout.VBox(7).set({alignX : 'left'}));
    }
});
