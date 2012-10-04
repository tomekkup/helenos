/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.ui.CompositeH',
{
    extend : qx.ui.container.Composite,

    construct : function(objects)
    {
        this.base(arguments);
        this.setLayout(new qx.ui.layout.HBox(6).set({alignY : 'middle'}));
        for (var i = 0; i < objects.length; i++) {
            this.add(objects[i]);
        }
    }
});
