/* ************************************************************************

   Copyright:

   License:

   Authors:

************************************************************************ */

/* ************************************************************************

#asset(helenos/*)

************************************************************************ */

/**
 * This is the main application class of your custom application "helenos"
 */
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
    
    pane.add(this.__treePane, 0);
    pane.add(this.__contentPane, 1);
  },

  members :
  {
    __treePane : null,
    __contentPane : null
  }
});
