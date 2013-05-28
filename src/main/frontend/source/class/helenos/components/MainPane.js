/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.components.MainPane",
{
    extend : qx.ui.splitpane.Pane,

    /*
  *****************************************************************************
     MEMBERS
  *****************************************************************************
  */
 
    construct : function()
    {
        this.base(arguments);
        this.setOrientation("horizontal");
 
        this.__createLeftPane();
        this.__createRightPane();
        this.add(this.__schemaPane, 0);
        this.add(this.__rightPane, 1);
    },

    members :
    {
        __schemaPane : null,
        __rightPane : null,
    
        __createLeftPane : function() {
            this.__schemaPane = new helenos.components.SchemaPane();
            helenos.util.GuiObserver.registerSchemaPane(this.__schemaPane);
        },
    
        __createRightPane : function() {
            this.__rightPane = new helenos.components.RightContentPane();
            helenos.util.GuiObserver.registerTabbedPane(this.__rightPane);
        }
    }
});
