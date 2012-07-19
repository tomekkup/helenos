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
 * Confirmation popup singleton
 */
qx.Class.define("dialog.Confirm",
{
  extend : dialog.Dialog,
  
  /*
  *****************************************************************************
     STATIC METHODS
  *****************************************************************************
  */     
  statics:
  {
    /**
     * Returns singleton instance of this class. This method has to
     * be part of any subclass extending this widget.
     */
    getInstance : function()
    {
      return this.superclass.getInstance(this.classname);
    }
  },
  /*
  *****************************************************************************
     PROPERTIES
  *****************************************************************************
  */     
  properties :
  {
		/**
		 * Label used for the "yes button"
		 */
    yesButtonLabel :
    {
      check : "String",
      nullable : false,
      init : "Yes",
      event : "changeYesButtonLabel"
    },

		/**
		 * Icon used for the "yes button"
		 */
    yesButtonIcon :
    {
      check : "String",
      nullable : true,
      init : "icon/22/actions/dialog-ok.png",
      event : "changeYesButtonIcon"
    },    
    
		/**
		 * Label used for the "no button"
		 */
    noButtonLabel :
    {
      check : "String",
      nullable : false,
      init : "No",
      event : "changeNoButtonLabel"
    },

		/**
		 * Icon used for the "no button"
		 */
    noButtonIcon :
    {
      check : "String",
      nullable : true,
      init : "icon/22/actions/dialog-cancel.png",
      event : "changeNoButtonIcon"
    },
    
		/**
		 * This property controls the display of a cancel button
		 */
    allowCancel :
    {
      refine : true,
      init : false
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
    _yesButton : null,
    _noButton  : null,
    
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
       * add image 
       */
      this._image = new qx.ui.basic.Image();
      this._image.setVisibility("excluded");
      hbox.add( this._image );
      
      /*
       * Add message label
       */
      this._message = new qx.ui.basic.Label();
      this._message.setRich(true);
      this._message.setWidth(200);
      this._message.setAllowStretchX(true);
      hbox.add( this._message, {flex:1} );    
      
      /* 
       * Yes button 
       */
      var yesButton = this._yesButton =  new qx.ui.form.Button;
      yesButton.setAllowStretchX(true);
      yesButton.addListener("execute", this._handleYes, this );
      this.bind("yesButtonLabel", yesButton, "label");
      this.bind("yesButtonIcon",  yesButton, "icon");
      yesButton.setLabel( this.tr("yes") );
      
      /* 
       * No button 
       */
      var noButton = this._noButton = new qx.ui.form.Button;
      noButton.setAllowStretchX(true);
      noButton.addListener("execute", this._handleNo, this );
      this.bind("noButtonLabel",noButton, "label" );
      this.bind("noButtonIcon", noButton, "icon" );
      noButton.setLabel( this.tr("no") );
      
      /* 
       * Cancel Button 
       */
      var cancelButton = this._createCancelButton();
      
      /*
       * buttons pane
       */
      var buttonPane = new qx.ui.container.Composite;
      var bpLayout = new qx.ui.layout.HBox(5)
      bpLayout.setAlignX("center");
      buttonPane.setLayout( bpLayout );
      buttonPane.add( yesButton );
      buttonPane.add( noButton );
      buttonPane.add( cancelButton );
      groupboxContainer.add(buttonPane);
        
    },
    
    /*
    ---------------------------------------------------------------------------
       EVENT HANDLERS
    ---------------------------------------------------------------------------
    */     
    
    /**
     * Handle click on yes button. Calls callback with
     * a "true" value
     */
    _handleYes : function()
    {
      this.hide();
      if( this.getCallback() )
      {
        this.getCallback().call(this.getContext(),true);
      }
      this.resetCallback();
    },  

    /**
     * Handle click on no button. Calls callback with 
     * a "false" value
     */
    _handleNo : function()
    {
      this.hide();
      if( this.getCallback() )
      {
        this.getCallback().call(this.getContext(),false);
      }
    } 
  }
});