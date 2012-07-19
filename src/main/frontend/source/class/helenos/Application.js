/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/* ************************************************************************
#asset(helenos/*)
************************************************************************ */
/**
 * This is the main application class of your custom application "helenos"
 */
qx.Class.define("helenos.Application",
{
    extend : qx.application.Standalone,

    /*
  *****************************************************************************
     MEMBERS
  *****************************************************************************
  */

    members :
    {       
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
            
            qx.locale.Manager.getInstance().setLocale("en");
      
            this.getRoot().add(new helenos.components.TopComposite(), {
                edge : 0
            });
        }
    }
});
