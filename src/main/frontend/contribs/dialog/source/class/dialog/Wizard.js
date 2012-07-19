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
 * A wizard-type widget that constructs the wizard pages on-the-fly, using 
 * functionality from dialog.Form. 
 */
qx.Class.define("dialog.Wizard",
{
  extend : dialog.Form,
  
  /*
  *****************************************************************************
     PROPERTIES
  *****************************************************************************
  */     
  properties :
  {
    /**
     * An array of maps that sets the properties of this widget
     */
    pageData : 
    {
      check : "Array",
      apply : "_applyPageData"
    },  
    
    /**
     * The number of the page in the wizard
     */
    page : 
    {
      check : "Integer",
      apply : "_applyPage",
      init : 0
    },
    
    /**
     * Whether to allow the user to go to the previous
     * wizard page
     */
    allowBack :
    {
      check : "Boolean",
      init : false,
      event : "changeAllowBack"
    },

    /**
     * Whether to allow the user to go to the next
     * wizard page
     */
    allowNext :
    {
      check : "Boolean",
      init : false,
      event : "changeAllowNext"
    },    
    
    /**
     * Whether to allow the user to finish the wizard. Automatically
     * set to 'true' on the last page of the wizard. The "Finish" button
     * is enabled if this property is 'true' AND all the form entries pass
     * the validation tests. 
     */
    allowFinish :
    {
      check : "Boolean",
      init : false,
      event : "changeAllowFinish"
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
    _backButton : null,
    _nextButton : null,
    _finishButton : null,
    
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
      var groupboxContainer = new qx.ui.groupbox.GroupBox();
      groupboxContainer.setPadding(0);
      groupboxContainer.setLayout( new qx.ui.layout.VBox(0) );
      this.add( groupboxContainer );
      
      /*
       * hbox with message and image (@todo)
       */
      var hbox = new qx.ui.container.Composite();
      hbox.setLayout( new qx.ui.layout.HBox(10) );
      groupboxContainer.add( hbox );
      
      /*
       * message label in title area  
       */
      this._message = new qx.ui.basic.Label();
      this._message.setRich(true);
      this._message.setMinWidth(100);
      this._message.setAllowGrowX(true);
      hbox.add( this._message, {flex:1} );    
      
      /*
       * horizontal line
       */
      var line = new qx.ui.core.Widget;
      line.setHeight( 2 );
      line.setBackgroundColor("gray");
      groupboxContainer.add( line );
      
      /* 
       * Form container  
       */
      var formContainer = this._formContainer = new qx.ui.container.Composite();
      formContainer.setPadding(16);
      formContainer.setLayout( new qx.ui.layout.Grow() );
      formContainer.setMinWidth(300);
      formContainer.setMinHeight(200);
      groupboxContainer.add( formContainer );

      /*
       * horizontal line
       */
      var line = new qx.ui.core.Widget;
      line.setHeight( 2 );
      line.setMarginBottom( 5 );
      line.setBackgroundColor("gray");
      groupboxContainer.add( line );      
      
      /*
       * buttons pane with horizontal line on top
       */
      var buttonPane = new qx.ui.container.Composite();
      var bpLayout = new qx.ui.layout.HBox(5)
      bpLayout.setAlignX("right");
      buttonPane.setLayout( bpLayout );
      groupboxContainer.add(buttonPane);
      
      /* 
       *  'Back' button 
       */
      this._backButton = new qx.ui.form.Button( "< " + this.tr("Back") );
      this._backButton.addListener("execute",this.goBack,this);
      this.bind("allowBack",this._backButton,"enabled");
      this._backButton.setEnabled(false);
      buttonPane.add( this._backButton );   

      /* 
       *  'Next' button 
       */
      this._nextButton = new qx.ui.form.Button( this.tr("Next") + " >" );
      this._nextButton.addListener("execute",this.goForward,this);
      this._nextButton.setEnabled(false);
      buttonPane.add( this._nextButton );   
      
      /* 
       * Cancel Button 
       */
      var cancelButton = this._createCancelButton();
      buttonPane.add( cancelButton );        
      
      /* 
       *  Finish button
       */
      this._finishButton = new qx.ui.form.Button( this.tr("Finish")  );
      this._finishButton.addListener("execute",this.finish,this);
      this._finishButton.setEnabled(false);
      buttonPane.add( this._finishButton );
      
      
    },

    /**
     * Add bindings to the validation manager to control the state of 
     * the wizard buttons. 
     * @param form {qx.ui.form.Form} The form to bind
     */
    _onFormReady : function( form )
    {
      var _this = this;
      
      /*
       * bind the enabled state of the "next" button to the validity of the
       * current form, as long as the last page hasn't been reached.
       */
      form.getValidationManager().bind( "valid", this._nextButton, "enabled", {
        converter : function( value ) {
          return value && _this.getAllowNext() ? true:false;
        }
      });

      /*
       * bind the enabled state of the "finish" button to the validity of the
       * current form and the state of the "allowFinish" property
       */
      form.getValidationManager().bind( "valid", this._finishButton, "enabled", {
        converter : function( value ){
          return value  && _this.getAllowFinish() ? true:false;
        }
      });
    },    
    
    /*
    ---------------------------------------------------------------------------
       APPLY METHODS
    ---------------------------------------------------------------------------
    */     
    
    /**
     * Apply the page data. This initializes the response
     * data model
     * @param pageData {Array} The new page data
     * @param old {Array} The old page data
     * @return
     */
    _applyPageData : function ( pageData, old )
    {
      /*
       * disable the buttons
       */
      this._backButton.setEnabled(false);
      this._nextButton.setEnabled(false);
      this._finishButton.setEnabled(false);
      
      if ( pageData )
      {
        /*
         * initialize response data model
         */
        var modelData = {};
        pageData.forEach( function( pData ){
          var formData = pData.formData;
          for ( var key in formData )
          {
            modelData[key] = formData[key].value || null;
          }        
        } );
        var model = qx.data.marshal.Json.createModel( modelData );
        this.setModel( model );
      }
      else
      {
        this.setFormData(null);
        this.setModel(null);
      }
    },

   /**
    * Go to a given wizard page. This recreates the 
    * form.
    * @param page {Integer} The new page
    * @param old {Integer} The old page
    */
   _applyPage : function ( page, old )
   {
      var pageData = this.getPageData()[ page ];
      this.setFormData(null);
      
      /*
       * delete properties that are not allowed to be set
       */
      delete pageData.pageData;
      delete pageData.page;
      
      /*
       * set button status
       */
      var length = this.getPageData().length; 
      this.setAllowNext( page < length -1 );
      this.setAllowBack( page > 0 );
      if ( ! this.getAllowFinish() )
      {
        this.setAllowFinish( page == length -1 );
      }
      
      
      /*
       * create form
       */
      this.set( pageData );
     

   },    
    
    /*
    ---------------------------------------------------------------------------
       API METHODS
    ---------------------------------------------------------------------------
    */
    
    /**
     * Starts the wizard
     */
    start : function()
    {
      this.show();
      this.setPage( 0 );
    },

    /**
     * Goes to the previous wizard button
     */
    goBack : function()
    {
      var page = this.getPage(); 
      if ( page == 0 )
      {
        this.error("Cannot go back!");
      }
      this.setPage( --page );
    },

    /**
     * Goes to the next wizard page
     */
    goForward : function()
    {
      var page = this.getPage(); 
      if ( page > this.getPageData().length -2  )
      {
        this.error("Cannot go forward!");
      }
      this.setPage( ++page );
    },    
    
    /** 
     * Finishes the wizard. Calls callback with the result data map
     */
    finish : function()
    {
      this.hide();
      if( this.getCallback() )
      {
        this.getCallback()( qx.util.Serializer.toNativeObject( this.getModel() ) );
      }
      this.resetCallback();
    }
  }    
});