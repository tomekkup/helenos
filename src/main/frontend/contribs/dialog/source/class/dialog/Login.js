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
 * A dialog for authentication and login
 */
qx.Class.define("dialog.Login",
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
     * A html text that is displayed below the image (if present)
     * and above the login
     */
    text :
    {
      check : "String",
      nullable : true,
      apply : "_applyText"
    },
    
    /**
     * The name of the font in the theme that should be applied to 
     * the text
     */
    textFont :
    {
      check    : "String",
      nullable : true,
      init     : "bold",
      apply    : "_applyTextFont"
    }
    
  },
  
  /*
  *****************************************************************************
     EVENTS
  *****************************************************************************
  */    
  events : 
  {
    /**
     * Event dispatched when login was successful
     */
    "loginSuccess" : "qx.event.type.Event",
    
    /**
     * Data event dispatched when login failed, event data
     * contains a reponse message
     */
    "loginFailure"  : "qx.event.type.Data"    
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
    _text : null,
    _username : null,
    _password : null,
    
    /*
    ---------------------------------------------------------------------------
       APPLY AND PRIVATE METHODS
    ---------------------------------------------------------------------------
    */ 

		/**
		 * Apply function used by proterty {@link #text}
		 * @param value {String} New value
		 * @param old {String} Old value
		 */
    _applyText : function( value, old )
    {
      this._text.setValue( value );
      this._text.setVisibility( value ? "visible" : "excluded" );
    },    
    
		/**
		 * Apply function used by proterty {@link #textFont}
		 * @param value {String} New value
		 */
    _applyTextFont : function( value )
    {
      this._text.setFont( value );
    },
        
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
      var layout = new qx.ui.layout.VBox(10);
      layout.setAlignX("center");
      groupboxContainer.setLayout( layout );
      this.add( groupboxContainer );
      
      /*
       * add image 
       */
      this._image = new qx.ui.basic.Image();
      this._image.setVisibility("excluded");
      groupboxContainer.add( this._image );
      
      /*
       * add text
       */
      this._text = new qx.ui.basic.Label();
      this._text.setAllowStretchX(true);
      this._text.setVisibility("excluded");
      this.setTextFont("bold");
      
      groupboxContainer.add( this._text );
      
      /* 
       * grid layout with login fields  
       */
      var gridContainer = new qx.ui.container.Composite;
      var gridLayout = new qx.ui.layout.Grid(9, 5);
      gridLayout.setColumnAlign(0, "right", "top");
      gridLayout.setColumnAlign(2, "right", "top");
      gridLayout.setColumnMinWidth(0, 50);
      gridLayout.setColumnFlex(1, 2);
      gridContainer.setLayout(gridLayout);
      gridContainer.setAlignX("center");
      gridContainer.setMinWidth(200);
      gridContainer.setMaxWidth(300);
      groupboxContainer.add( gridContainer );
      
      /* 
       * Labels 
       */
      var labels = [this.tr("Name"), this.tr("Password") ];
      for (var i=0; i<labels.length; i++) {
        gridContainer.add(new qx.ui.basic.Label(labels[i]).set({
          allowShrinkX: false,
          paddingTop: 3
        }), {row: i, column : 0});
      }

      /* 
       * Text fields  
       */
      this._username = new qx.ui.form.TextField();
      this._password = new qx.ui.form.PasswordField();
      
      this._password.addListener("keypress",function(e){
        if ( e.getKeyIdentifier() == "Enter" ) 
        {   
          this._callCallback();
        }
      },this);

      gridContainer.add(this._username.set({
        allowShrinkX: false,
        paddingTop: 3
      }), {row: 0, column : 1});

      gridContainer.add(this._password .set({
        allowShrinkX: false,
        paddingTop: 3
      }), {row: 1, column : 1});

      /*
       * Add message label
       */
      this._message = new qx.ui.basic.Label();
      this._message.setRich(true);
      this._message.setAllowStretchX(true);
      this._message.setVisibility("excluded");
      groupboxContainer.add( this._message );    
      
      /* 
       * Login Button 
       */
      var loginButton = this._loginButton =  new qx.ui.form.Button(this.tr("Login"));
      loginButton.setAllowStretchX(false);
      loginButton.addListener("execute", this._callCallback, this);      
      
      /* 
       * Cancel Button 
       */
      var cancelButton = this._createCancelButton();
      
      /*
       * buttons pane
       */
      var buttonPane = new qx.ui.container.Composite;
      buttonPane.setLayout(new qx.ui.layout.HBox(5));
      buttonPane.add(loginButton);
      buttonPane.add(cancelButton);
      gridContainer.add(
         buttonPane,{ row : 3, column : 1}
      );

    },  
    
    /**
     * Calls the callback function with the current login data
     */
    _callCallback : function()
    {
      this.getCallback()(
        this._username.getValue(),
        this._password.getValue(),
        this._handleCheckLogin,
        this
      );
    },
    
    /**
     * Handle click on cancel button
     */
    _handleCancel : function()
    {
      this.hide();
    },
    
    /**
     * Handler function called with the result of the authentication
     * process.
     * @param result {Boolean} The state of login success
     * @param message {String|Null} Optional HTML message that might contain
     * error information, such as "Wrong password".
     */
    _handleCheckLogin : function( result, message )
    {
      /*
       * clear password field and message label
       */
      this._password.setValue("");
      this.setMessage(null);
       
      /*
       * check result
       */
      if ( result )
      {
        this.fireEvent("loginSuccess");
        this.hide();
      }
      else
      {
        this.fireDataEvent("loginFailure", message );
      }
    },
    
    /*
    ---------------------------------------------------------------------------
      API METHODS
    ---------------------------------------------------------------------------
    */     
   
    /**
    * @override
    */    
    hide : function()
    {
      this._password.setValue("");
      this.setMessage(null);
      this.base(arguments);
    }
  }  
});