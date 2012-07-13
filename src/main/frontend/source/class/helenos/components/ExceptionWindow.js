/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */

/*
#asset(qx/icon/Tango/32/status/dialog-warning.png)
*/
qx.Class.define('helenos.components.ExceptionWindow',
{
    extend: qx.ui.window.Window,

    construct: function() {
        this.base(arguments);
        this.set({ allowMinimize: false, allowMaximize: false, resizable: false, padding: 1, width: 500,
            caption: 'Error occured', modal: true});
        this.setLayout(new qx.ui.layout.VBox(10, 'top'));

        this._label = new qx.ui.basic.Atom('<b>Remote server returned an error!', 'qx/icon/Tango/32/status/dialog-warning.png');
        this._textArea = new qx.ui.form.TextArea();
        this._textArea.setMinHeight(200);

        this.add(this._label);
        this.add(this._textArea);
        this.addListenerOnce('resize', this.center, this);
    },

    members:
	{
        _label: null,
        _textArea: null,
        _body: '',

        showException: function(e) {
            this._body = '';
            //alert(qx.dev.Debug.debugObjectToString(e));
	        
            //if (e.message != undefined) {
            //    this._body += 'message: ' + e.message + '<br/>';
            //}
            //if (e.lineNumber != undefined) {
            //    this._body += 'lineNumber: ' + e.lineNumber + '<br/>';
            //}
            if (e.rpcdetails != undefined) {
                //if (e.rpcdetails.origin != undefined) {
                //    this._body += 'origin: ' + e.rpcdetails.origin + '<br/>';
                //}
                //if (e.rpcdetails.code != undefined) {
                //    this._body += 'code: ' + e.rpcdetails.code + '<br/>';
                //}
                if (e.rpcdetails.message != undefined) {
                    this._body += 'Detale:' + e.rpcdetails.message + '\n';
                }
                if (e.rpcdetails.stackTrace != undefined) {
                    this._body += '\nStos: ' + e.rpcdetails.stackTrace + '<br/>';
                }
            }
            this._textArea.setValue(this._body);
            this.open();
        }
    }
});
