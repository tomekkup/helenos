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
            dialog.Dialog.error('<b>RPC ERROR:</b><br/>' +
                'Origin: ' + details.origin + '<br/>' +
                'Code: ' + details.code + '<br/>' +
                'Message:<br/><p><i>' + details.message + '</i></p>'
                );
        },
        
        callSync : function(methodName) {
            var ret = null;
            
            if (this.getHandleExceptions()){
                try {
                    ret = this._callInternal(arguments, 0);
                } catch (exc) {
                    this.showDetails(exc.rpcdetails);
                }
            } else {
                ret = this._callInternal(arguments, 0);
            }
            
            return ret;
        }
    }
});
