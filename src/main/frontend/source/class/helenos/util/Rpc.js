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
  
    construct : function(serviceName)
    {
        this.base(arguments);
        this.setUrl(this.__getRemoteUri(serviceName));
        this.setTimeout(30000);
    },
    
    statics : {
        serverPath : null
    },
        
    members : {
        __getRemoteUri : function(serviceName) {
            if (helenos.util.Rpc.serverPath == undefined)  {
                var pathIdx = window.location.href.indexOf('/helenos/index.html');
                helenos.util.Rpc.serverPath = window.location.href.substr(0, pathIdx);
            }
            
            return helenos.util.Rpc.serverPath + serviceName;
        },
        
        showDetails : function(details) {
            dialog.Dialog.alert('RPC ERROR: ' +
                'origin: ' + details.origin +
                '; code: ' + details.code +
                '; message: ' + details.message
                );
        },
        
        callSync : function(methodName) {
            var ret = null;
            
            try {
                ret = this._callInternal(arguments, 0);
            } catch (exc) {
                this.showDetails(exc.rpcdetails);
            }
            
            /*if (ret.error) {
                alert('RPC CALL ERROR: ' +
                        'code: ' + ret.error.code +
                        'message: ' + ret.error.message
                     );
                return null;
            }*/
            
            return ret;
        }
    }
});
