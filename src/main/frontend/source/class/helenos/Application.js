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
#asset(favicon.ico)
#asset(ZeroClipboard*.*)
************************************************************************ */

/**
 * This is the main application class of your custom application "helenos"
 */
qx.Class.define("helenos.Application",
{
    extend : qx.application.Standalone,

    members :
    {
        _loginBox : null,
        
        shake : {duration: 1000, keyFrames : {
            0 : {translate: "0px"},
            10 : {translate: "-10px"},
            20 : {translate: "10px"},
            30 : {translate: "-10px"},
            40 : {translate: "10px"},
            50 : {translate: "-10px"},
            60 : {translate: "10px"},
            70 : {translate: "-10px"},
            80 : {translate: "10px"},
            90 : {translate: "-10px"},
            100 : {translate: "0px"}
        }},
        /** 
        * @lint ignoreUndefined(silverbluetheme)
        */
        main : function()
        {
            this.base(arguments);
            // set default locale
            qx.locale.Manager.getInstance().setLocale("en");
            
            ZeroClipboard.setMoviePath("resource/ZeroClipboard10.swf");
            // apply MTableContextMenu mixin to TreeVirtual and Table to enable context menu
            qx.Class.include(qx.ui.treevirtual.TreeVirtual, qx.ui.table.MTableContextMenu);
            qx.Class.include(helenos.ui.table.Table, qx.ui.table.MTableContextMenu);

            // Enable logging in debug variant
            if (qx.core.Environment.get("qx.debug"))
            {
                // support native logging capabilities, e.g. Firebug for Firefox
                qx.log.appender.Native;
                // support additional cross-browser console. Press F7 to toggle visibility
                qx.log.appender.Console;
            }
            // register root
            helenos.util.GuiObserver.registerRoot(this.getRoot());
            // ask for credentials
            this.initLoginBox();
        },
        
        initLoginBox : function() {
            this._loginBox = new dialog.Login({
                "text" : 'Please sign in',
                checkCredentials : this.checkCredentials
            });
            
            this._loginBox.addListener('loginSuccess', function() {
                this.getRoot().add(new helenos.components.TopComposite(), {edge : 0});
            }, this);
            this._loginBox.addListener('loginFailure', function() {
                qx.bom.element.Animation.animate(this._loginBox.getContainerElement().getDomElement(), this.shake, 1000);
            }, this);
            this._loginBox.show();
        },
        
        checkCredentials : function(username, password, callback ) {
            var req = new qx.io.remote.Request(helenos.util.UriHelper.getRemoteUri('/j_spring_security_check'), 'POST', 'application/json');
            req.set({
                parseJson : true, 
                prohibitCaching : true
            });
            req.setParameter('j_username', username, true);
            req.setParameter('j_password', password, true);
            req.addListener("completed", function(e) {
                helenos.util.CredentialsProvider.registerLoggedUser(username, e.getContent());
                callback(e.getContent() == null, username);
            }, this);
            
            req.send();
        }
    }
});
