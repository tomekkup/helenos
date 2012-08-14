/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.components.Header",
{
    extend : qx.ui.container.Composite,

    construct : function()
    {
        this.base(arguments);
        this.set({
            layout : new qx.ui.layout.HBox(5).set({
                alignY : "middle"
            }),
            padding : 8,
            height : 40,
            appearance : 'app-header-black'
        });
        
        //TODO change components to appearance definitions
        var label = new qx.ui.basic.Label().set({
            value: "Helenos",
            rich : true,
            font : new qx.bom.Font(22, ["OpenSansRegular", "Helvetica Neue", "Helvetica", "Arial", "sans-serif"])
        });
        this.add(label );
    }
});
