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
 * Dialog that offers the user a choice of alternative buttons to select from.
 */
qx.Class.define("dialog.Select",
{
  extend : dialog.Dialog,

  /*
  *****************************************************************************
     PROPERTIES
  *****************************************************************************
  */     
  properties :
  {
    /**
     * An array of maps [ { label: "Foo", icon : "icon/22/...", value : "foo" },...]
     */
    options : 
    {
      check : "Array",
      nullable : false,
      event : "changeOptions"
    }
  },
  
  /*
  *****************************************************************************
     MEMBERS
  *****************************************************************************
  */     
  members :
  {
    
    /*
    ---------------------------------------------------------------------------
       PRIVATE MEMBERS
    ---------------------------------------------------------------------------
    */  

    
    /*
    ---------------------------------------------------------------------------
       WIDGET LAYOUT
    ---------------------------------------------------------------------------
    */     
    
    /**
     * Create the main content of the widget
     */
    _createWidgetContent : function()
    {      

      /*
       * groupbox
       */
      var groupboxContainer = new qx.ui.groupbox.GroupBox().set({
        contentPadding: [16, 16, 16, 16]
      });
      groupboxContainer.setLayout( new qx.ui.layout.VBox(10) );
      this.add( groupboxContainer );

      var hbox = new qx.ui.container.Composite;
      hbox.setLayout( new qx.ui.layout.HBox(10) );
      groupboxContainer.add( hbox );
      
      /*
       * Add message label
       */
      this._message = new qx.ui.basic.Label();
      this._message.setRich(true);
      this._message.setWidth(200);
      this._message.setAllowStretchX(true);
      hbox.add( this._message, {flex:1} );    
      
      /*
       * buttons pane
       */
      var buttonPane = new qx.ui.container.Composite;
      var bpLayout = new qx.ui.layout.HBox(5)
      bpLayout.setAlignX("center");
      buttonPane.setLayout( bpLayout );
      
      /* 
       * Add buttons on the fly
       */
      this.addListener("changeOptions",function(event){
        buttonPane.removeAll();
        var options = event.getData();
        options.forEach(function(option){
          var button =  new qx.ui.form.Button( option.label, option.icon );
          button.setAllowStretchX(true);
          var value = "" + option.value;
          button.addListener("execute", function(){
            this._handleSelection(value);
          }, this );
          buttonPane.add(button);
        },this);
        
        /* 
         * Cancel Button 
         */
        var cancelButton = this._createCancelButton();
        buttonPane.add( cancelButton );        
      },this);
      
      groupboxContainer.add(buttonPane);
        
    },
    
    /*
    ---------------------------------------------------------------------------
       EVENT HANDLERS
    ---------------------------------------------------------------------------
    */     
    
    /**
     * Handle click on a button. Calls callback with
     * the value set in the options map.
     * @param value {var} The passed value
     */
    _handleSelection : function( value )
    {
      this.hide();
      if( this.getCallback() )
      {
        this.getCallback().call(this.getContext(),value);
      }
    }
  }
});