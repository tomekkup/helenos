/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.util.Rpc',
{
    extend : qx.io.remote.Rpc ,
    
    properties : {
        handleExceptions : { init : true, check : 'Boolean' }
    },
  
    construct : function(serviceName)
    {
        this.base(arguments);
        this.setTimeout(30000);
        this.setUrl(helenos.util.UriHelper.getRemoteUri(serviceName));
    },
        
    members : {
        
        /** 
        * @lint ignoreUndefined(dialog)
        */
        showDetails : function(details) {
            (new dialog.Alert({
                "message" : '<h3><b>An error has occured !</b></h3>' +
                '<p>' + details.message + '</p>',
                'image' : 'icon/48/status/dialog-error.png'
            })).set({width : 450}).show();
            console.log(details);
        },
        
        callSync : function(methodName) {
            var ret = null;
            //qx.core.Init.getApplication().getRoot().setGlobalCursor('progress');
            if (this.getHandleExceptions()){
                try {
                    ret = this._callInternal(arguments, 0);
                } catch (exc) {
                    this.showDetails(exc.rpcdetails);
                }
            } else {
                ret = this._callInternal(arguments, 0);
            }
            //qx.core.Init.getApplication().getRoot().resetGlobalCursor();
            return ret;
        }
    }
});
