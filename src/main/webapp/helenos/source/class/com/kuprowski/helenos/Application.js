/* ************************************************************************

   Copyright:

   License:

   Authors:

************************************************************************ */

/* ************************************************************************

#asset(com/kuprowski/helenos/*)

************************************************************************ */

/**
 * This is the main application class of your custom application "helenos"
 */
qx.Class.define("com.kuprowski.helenos.Application",
{
  extend : qx.application.Standalone,



  /*
  *****************************************************************************
     MEMBERS
  *****************************************************************************
  */

  members :
  {
    /**
     * This method contains the initial application code and gets called 
     * during startup of the application
     * 
     * @lint ignoreDeprecated(alert)
     */
    main : function()
    {
      // Call super class
      this.base(arguments);

      // Enable logging in debug variant
      if (qx.core.Environment.get("qx.debug"))
      {
        // support native logging capabilities, e.g. Firebug for Firefox
        qx.log.appender.Native;
        // support additional cross-browser console. Press F7 to toggle visibility
        qx.log.appender.Console;
      }

      // Document is the application root
      var doc = this.getRoot();

      var pane = new com.kuprowski.helenos.components.MainPane;
      doc.add(pane, {left: 100, top: 50});
    }
  }
});
