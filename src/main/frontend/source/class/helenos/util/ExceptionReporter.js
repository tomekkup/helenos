/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.util.ExceptionReporter',
{
    statics :
    {
        __ewindow : null,
		
        report : function(e) {
            if (this.__ewindow == undefined) {
                this.__ewindow = new helenos.components.ExceptionWindow();
            }
            this.__ewindow.showException(e);
        }
    }
});