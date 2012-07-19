/* ************************************************************************

   qooxdoo dialog library
  
   http://qooxdoo.org/contrib/project#dialog
  
   Copyright:
     2007-2010 Christian Boulanger
  
   License:
     LGPL: http://www.gnu.org/licenses/lgpl.html
     EPL: http://www.eclipse.org/org/documents/epl-v10.php
     See the LICENSE file in the project's top-level directory for details.
  
   Authors:
   *  Christian Boulanger (cboulanger)
  
************************************************************************ */

/**
 * Form renderer renderer for {@link qx.ui.form.Form}. This is a 
 * single row renderer adapted for the dialog.Form widget. Main
 * difference is that the form allows text-only labels without a corresponding
 * form element which can serve, for example, for explanatory text.
 */
qx.Class.define("dialog.FormRenderer", 
{
  extend : qx.ui.form.renderer.Single,
  implement : qx.ui.form.renderer.IFormRenderer,

  members :
  {
    _row : 0,
    _buttonRow : null,
    
    /**
     * Add a group of form items with the corresponding names. The names are
     * displayed as label.
     * The title is optional and is used as grouping for the given form 
     * items. Additionally, this renderer supports label-only fields.
     * 
     * @param items {qx.ui.core.Widget[]} An array of form items to render.
     * @param names {String[]} An array of names for the form items.
     * @param title {String?} A title of the group you are adding.
     */
    addItems : function(items, names, title) {
      /*
       * add the header
       */
      if (title != null) {
        this._add(
          this._createHeader(title), {row: this._row, column: 0, colSpan: 2}
        );
        this._row++;
      }
      
      /*
       * add the items
       */ 
      for (var i = 0; i < items.length; i++) 
      {
        /*
         * current item
         */
        var item = items[i];

        /*
         * radio group
         */
        if ( item instanceof qx.ui.form.RadioGroup )
        {
          /*
           * create horizontal radio group for a small
           * number of radio buttons 
           */
          if ( item.getUserData("orientation") == "horizontal" )
          {
            var widget = this._createHBoxForRadioGroup( item );
          }
          else
          {
            var widget = this._createWidgetForRadioGroup( item );
          }       
        }
        
        /*
         * other form widgets
         */
        else
        {
          var widget = item;
        }
        
        /*
         * Excluded form elements, used for full-width
         * labels. this should be implemented differently,
         * though
         */
        if ( names[i] && item.getUserData("excluded") )
        {
          var label = new qx.ui.basic.Label( names[i] );
          label.setRich(true);
          this._add( label,  {row: this._row, column: 0, colSpan : 2 } );
        }
        
        /*
         * if the label is null, use the full width for the widget
         * doesn't work yet
         */
        else if ( ! names[i]  )
        {
          this._add( widget, {row: this._row, column: 0, colSpan : 2 } );
        }
        
        /*
         * normal case: label in col 0, form element in col 2
         */
        else
        {
          var label = this._createLabel( names[i], item);
          label.setRich(true);          
          this._add( label, {row: this._row, column: 0} );
          this._add( widget, {row: this._row, column: 1} );
        }
        
        /*
         * increment row
         */
         this._row++;
        
      }
    },

    /**
     * Takes the items of the given RadioGroup and adds the to a Composite. 
     * 
     * @param group {qx.ui.form.RadioGroup} The RadioGroup which needs to be 
     *   added.
     * @return {qx.ui.container.Composite} A composite containing the items of 
     *   the RadioGroup.
     */
    _createWidgetForRadioGroup : function(group) {
      var widget = new qx.ui.container.Composite(new qx.ui.layout.VBox(5));
      var items = group.getItems();
      for (var i = 0; i < items.length; i++) {
        widget.add(items[i]);
      }
      return widget;
    },
    
    /**
     * Takes the items of the given RadioGroup and adds the to a Composite. 
     * The composite has a HBox layout so the RadioButtons will be alligned
     * horizontally. This is only useful for a small number of RadioButtons
     * such as 2 or 3 buttons with labels. 
     * 
     * @param group {qx.ui.form.RadioGroup} The RadioGroup which needs to be 
     *   added.
     * @return {qx.ui.container.Composite} A composite containing the items of 
     *   the RadioGroup.
     */
    _createHBoxForRadioGroup : function(group) {
      var widget = new qx.ui.container.Composite(new qx.ui.layout.HBox(5));
      var items = group.getItems();
      for (var i = 0; i < items.length; i++) {
        widget.add(items[i]);
      }
      return widget;
    }
    
    
  }
});