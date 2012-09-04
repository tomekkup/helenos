/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.ui.CompositeV',
{
    extend : qx.ui.container.Composite,

    construct : function(objects)
    {
        this.base(arguments);
        this.setLayout(new qx.ui.layout.VBox(2));
        for (var i = 0; i < objects.length; i++) {
            this.add(objects[i]);
        }
    }
});
