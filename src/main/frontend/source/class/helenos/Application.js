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
#asset(ZeroClipboard*.*)
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
        /** 
        * @lint ignoreUndefined(silverbluetheme)
        */
        main : function()
        {
            // Call super class
            this.base(arguments);
            
            // set default locale
            qx.locale.Manager.getInstance().setLocale("en");
            
            // apply some required mixin to TreeVirtual to enable context menu
            qx.Class.include(qx.ui.treevirtual.TreeVirtual, qx.ui.table.MTableContextMenu);

            // Enable logging in debug variant
            if (qx.core.Environment.get("qx.debug"))
            {
                // support native logging capabilities, e.g. Firebug for Firefox
                qx.log.appender.Native;
                // support additional cross-browser console. Press F7 to toggle visibility
                qx.log.appender.Console;
            }
            
            // add main component
            this.getRoot().add(new helenos.components.TopComposite(), {
                edge : 0
            });
        }
    }
});
