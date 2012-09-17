/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */

/**
 * This class demonstrates how to define simulated interaction tests for your 
 * application. See the manual for details:
 * {@link http://manual.qooxdoo.org/2.0.1/pages/development/simulator.html}
 * 
 * @lint ignoreUndefined(simulator)
 */
qx.Class.define("helenos.simulation.DemoSimulation", {

  extend : simulator.unit.TestCase,
  
  members :
  {
    /*
    ---------------------------------------------------------------------------
      TESTS
    ---------------------------------------------------------------------------
    */
    
    /** Check if a widget is present (part of the DOM) */
    testButtonPresent : function()
    {
      this.assertNotNull(this.getSimulation().getWidgetOrNull("qxh=qx.ui.form.Button"), "Button widget not present!");
    },
    
    /** Click a button and check if an alert box pops up */
    testButtonClick : function()
    {
      this.getQxSelenium().qxClick("qxh=qx.ui.form.Button");
      this.assertEquals("true", String(this.getQxSelenium().isAlertPresent()));
    }
  }
  
});